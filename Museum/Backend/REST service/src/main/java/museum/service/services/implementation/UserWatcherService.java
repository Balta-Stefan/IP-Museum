package museum.service.services.implementation;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class UserWatcherService
{
    private final AtomicInteger numOfLoggedInUsers = new AtomicInteger(0);

    public int login()
    {
        return numOfLoggedInUsers.incrementAndGet();
    }

    public int logout()
    {
        return numOfLoggedInUsers.decrementAndGet();
    }

    public int getNumOfLoggedInUsers()
    {
        return this.numOfLoggedInUsers.get();
    }
}
