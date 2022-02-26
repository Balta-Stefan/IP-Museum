package museum.service.services;

public interface AdminInfoService
{
    int getNumberOfLoggedInUsers();
    long getNumberOfRegisteredUsers();
    int[] getLoginsPerHours();
}
