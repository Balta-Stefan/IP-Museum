package museum.service.services.implementation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import museum.service.exceptions.NotFoundException;
import museum.service.models.DTOs.TourDTO;
import museum.service.models.entities.EventNotificationEntity;
import museum.service.models.entities.TourEntity;
import museum.service.repositories.NotificationsRepository;
import museum.service.repositories.ToursRepository;
import museum.service.services.TourBeginningNotificationService;
import museum.service.utilities.TourNotifierService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class TourBeginningNotificationServiceImpl implements TourBeginningNotificationService
{
    private final NotificationsRepository notificationsRepository;
    private final ToursRepository toursRepository;
    @Data
    private static class Notification
    {
        private final Integer tourID;
        private final LocalDateTime notifyAt;
    }

    public TourBeginningNotificationServiceImpl(NotificationsRepository notificationsRepository, ToursRepository toursRepository, TourNotifierService tourNotifierService)
    {
        this.notificationsRepository = notificationsRepository;
        this.toursRepository = toursRepository;
        this.tourNotifierService = tourNotifierService;
    }


    private final PriorityQueue<Notification> jobs = new PriorityQueue<>(Comparator.comparing(Notification::getNotifyAt));
    private final TourNotifierService tourNotifierService;

    @PostConstruct
    public void prepareUnsentNotifications()
    {
        synchronized (jobs)
        {
            for(EventNotificationEntity ev : notificationsRepository.findAllBySentIsFalse())
            {
                jobs.add(new Notification(ev.getId(), ev.getSendDateTime()));
            }
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void checkJobs()
    {
        synchronized (jobs)
        {
            while(true)
            {
                Notification job = jobs.peek();
                if (job != null)
                {
                    LocalDateTime currentTime = LocalDateTime.now();
                    LocalDateTime notificationTimeStamp = job.getNotifyAt();
                    boolean isEqual = currentTime.isEqual(notificationTimeStamp);
                    boolean isAfter = currentTime.isAfter(notificationTimeStamp);

                    if (isEqual || isAfter)
                    {
                        job = jobs.poll();
                        tourNotifierService.notifyUser(job.getTourID());

                        Optional<EventNotificationEntity> tmp = notificationsRepository.findById(job.getTourID());
                        if(tmp.isEmpty())
                        {
                            log.warn("Couldn't find tour from notification with the following tour ID: " + job.getTourID());
                        }
                        else
                        {
                            tmp.get().setSent(true);
                            notificationsRepository.saveAndFlush(tmp.get());
                        }
                    }
                    else
                        return;
                }
                else
                    return;
            }
        }

    }

    @Override
    @Transactional
    public void addTour(TourDTO tourDTO)
    {
        LocalDateTime startTimeStamp = tourDTO.getStartTimestamp();
        LocalDateTime hourBefore = startTimeStamp.minusHours(1);
        LocalDateTime fiveMinutesBefore = startTimeStamp.minusMinutes(5);

        Notification hourBeforeTask = new Notification(tourDTO.getTourID(), hourBefore);
        Notification fiveMinsBeforeTask = new Notification(tourDTO.getTourID(), fiveMinutesBefore);

        TourEntity tourEntity = toursRepository.findById(tourDTO.getTourID()).orElseThrow(NotFoundException::new);
        EventNotificationEntity notification1 = new EventNotificationEntity(null, tourEntity, false, hourBefore);
        EventNotificationEntity notification2 = new EventNotificationEntity(null, tourEntity, false, fiveMinutesBefore);

        notificationsRepository.saveAndFlush(notification1);
        notificationsRepository.saveAndFlush(notification2);

        synchronized (jobs)
        {
            jobs.add(hourBeforeTask);
            jobs.add(fiveMinsBeforeTask);
        }
    }

}
