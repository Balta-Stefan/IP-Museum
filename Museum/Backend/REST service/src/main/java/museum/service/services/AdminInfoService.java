package museum.service.services;

import museum.service.models.entities.MuseumEntity;
import museum.service.models.entities.UserEntity;
import org.springframework.data.domain.Page;


public interface AdminInfoService
{
    int getNumberOfLoggedInUsers();
    long getNumberOfRegisteredUsers();
    int[] getLoginsPerHours();

    Page<UserEntity> getUsers(boolean onlyInactive, int pageNumber, int pageSize);
    boolean changeUserActivityStatus(int userID, boolean active);
    String changeUserPassword(int userID);

    Page<MuseumEntity> getMuseums(int pageNumber, int pageSize);
}
