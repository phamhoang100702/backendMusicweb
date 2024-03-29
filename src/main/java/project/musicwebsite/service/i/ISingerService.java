package project.musicwebsite.service.i;

import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.SingerDTO;
import project.musicwebsite.model.dto.TopTenSingerDTO;

import java.util.List;
import java.util.Optional;

public interface ISingerService  {
    Singer save(Singer BSinger);
    List<Singer> getAll();
    Optional<Singer> findById(Long id);
    Singer update(Singer BSinger);
    void delete(Long id);
    SingerDTO follow(Long userId, Long singerId);
    SingerDTO unFollow(Long userId,Long singerId);

    List<User> findAllFollower(Long id);

    Long getTotalSinger();

    List<Singer> searchByNameOrNickName(String name);

//    List<Singer> searchByNickName(String name);

    List<TopTenSingerDTO> getTopTenSingerByFollowers(Long top);

}
