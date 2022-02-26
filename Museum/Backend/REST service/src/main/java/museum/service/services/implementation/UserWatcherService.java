package museum.service.services.implementation;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;


@Service
public class UserWatcherService
{
    private int numOfLoggedInUsers = 0;
    private final int[] userLoginsPerHour = new int[24]; // i-th element is i-th hour from 00:00

    private LocalDate currentDay = LocalDate.now();

    synchronized public int login()
    {
        LocalDateTime currentTime = LocalDateTime.now();
        // if a new day is reached, clear the userLoginsPerHour array
        if(currentTime.toLocalDate().equals(this.currentDay) == false)
        {
            Arrays.fill(this.userLoginsPerHour, 0);
            this.currentDay = LocalDate.now();
        }
        int hour = currentTime.getHour();
        this.userLoginsPerHour[hour] = this.userLoginsPerHour[hour] + 1;

        this.numOfLoggedInUsers++;

        return this.numOfLoggedInUsers;
    }

    synchronized public int logout()
    {
        this.numOfLoggedInUsers--;

        return this.numOfLoggedInUsers;
    }

    synchronized public int getNumOfLoggedInUsers()
    {
        return this.numOfLoggedInUsers;
    }

    synchronized  public int[] getUserLoginsPerHour()
    {
        return this.userLoginsPerHour.clone();
    }
}
