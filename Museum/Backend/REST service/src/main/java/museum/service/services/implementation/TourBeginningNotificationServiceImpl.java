package museum.service.services.implementation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import museum.service.models.DTOs.TourDTO;
import museum.service.services.TourBeginningNotificationService;
import museum.service.utilities.TourNotifierService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
public class TourBeginningNotificationServiceImpl implements TourBeginningNotificationService
{
    @Data
    private static class Notification
    {
        private final TourDTO tourDTO;
        private final LocalDateTime notifyAt;
    }

    public TourBeginningNotificationServiceImpl(TourNotifierService tourNotifierService)
    {
        this.tourNotifierService = tourNotifierService;
    }


    private final PriorityQueue<Notification> jobs = new PriorityQueue<>(Comparator.comparing(Notification::getNotifyAt));

    private final TourNotifierService tourNotifierService;


    @Scheduled(fixedDelay = 5000)
    private void checkJobs()
    {
        synchronized (jobs)
        {
            Notification job = jobs.peek();
            if(job != null)
            {
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime notificationTimeStamp = job.getNotifyAt();
                boolean isEqual = currentTime.isEqual(notificationTimeStamp);
                boolean isAfter = currentTime.isAfter(notificationTimeStamp);

                if(isEqual || isAfter)
                {
                    job = jobs.poll();

                    tourNotifierService.notifyUser(job.getTourDTO());
                }
            }
        }

    }

    @Override
    public void addTour(TourDTO tourDTO)
    {
        LocalDateTime startTimeStamp = tourDTO.getStartTimestamp();
        LocalDateTime hourBefore = startTimeStamp.minusHours(1);
        LocalDateTime fiveMinutesBefore = startTimeStamp.minusMinutes(5);

        Notification hourBeforeTask = new Notification(tourDTO, hourBefore);
        Notification fiveMinsBeforeTask = new Notification(tourDTO, fiveMinutesBefore);

        synchronized (jobs)
        {
            jobs.add(hourBeforeTask);
            jobs.add(fiveMinsBeforeTask);
        }
    }

}
