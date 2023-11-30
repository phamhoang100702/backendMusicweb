package project.musicwebsite.service.i;

import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User save(User user) ;
    Optional<User> findById(Long id);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);

    List<Singer> findFollowedSinger(Long id);

    Long getTotalSong();

}