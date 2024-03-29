package project.musicwebsite.service.i;

import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.UPremium;
import project.musicwebsite.entity.User;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    User save(User user) ;
    Optional<User> findById(Long id);
    List<User> getAll();
    User update( User user);
    void delete(Long id);
    List<Singer> findFollowedSinger(Long id);
    Long count();
    User switchToPremium(Long id);
    List<User> saveListUser(ArrayList<User> users);
    Boolean checkFollowedSinger(Long userId,Long singerId);

    List<ChartDTO> getChartInforInTimePeriod(Long times);

}
