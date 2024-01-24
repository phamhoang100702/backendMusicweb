package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Follower;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.SingerDTO;
import project.musicwebsite.model.dto.TopTenSingerDTO;
import project.musicwebsite.model.mapper.SingerMapper;
import project.musicwebsite.repositories.FollowerRepository;
import project.musicwebsite.repositories.PlaylistRepository;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.ISingerService;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SingerService implements ISingerService {
    @Autowired
    SingerRepository singerRepository;
    @Autowired
    FollowerRepository followerRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PlaylistRepository playlistRepository;

    @Override
    public Singer save(Singer BSinger) {
        Optional<User> user1 = userRepository.findByEmail(BSinger.getEmail());
        if (user1.isPresent()) throw new BadRequestException("EMAIL EXISTED");
        String password = passwordEncoder.encode(BSinger.getPassword());
        BSinger.setPassword(password);
        Singer singer =  singerRepository.save(BSinger);
        return  singer;
    }

    @Override
    public List<Singer> getAll() {
        List<Singer> list = singerRepository.findAll();
        if (list.isEmpty()) throw new NotFoundException("DONT HAVE ANY SINGER");
        return list;
    }

    @Override
    public Optional<Singer> findById(Long id) {
        Optional<Singer> singer = singerRepository.findById(id);
        if (singer.isEmpty()) throw new NotFoundException("SINGER DONT EXISTED");
        return singer;
    }

    @Override
    public Singer update(Singer singer) {
        System.out.println("Vao day ");
        return singerRepository.findById(singer.getId())
                .map(singer2 -> {
                    singer2.setName(singer.getName());
                    singer2.setBio(singer.getBio());
                    if(singer.getAvatar()!=null && !singer.getAvatar().isBlank())  {
                        singer2.setAvatar(singer.getAvatar());
                    }
                    if(singer.getSocialMediaLink()!=null && !singer.getSocialMediaLink().isBlank() ){
                        singer2.setSocialMediaLink(singer.getSocialMediaLink());
                    }
                    singer2.setStatus(singer.getStatus());
                    if(singer.getPassword()!=null && !singer.getPassword().isBlank()){
                        String password = passwordEncoder.encode(singer.getPassword());
                        singer2.setPassword(password);
                    }
                    if(singer.getNickName()!=null && !singer.getNickName().isBlank()){
                        singer2.setNickName(singer.getNickName());
                    }
                    return singerRepository.save(singer2);
                }).orElseThrow(() -> new NotFoundException("SINGER NOT EXISTED"));
    }

    @Override
    public void delete(Long id) {
        Optional<Singer> singer = singerRepository.findById(id);
        if (singer.isEmpty()) throw new NotFoundException("SINGER NOT EXISTED");
        followerRepository.deleteAllBySinger(singer.get());
        singerRepository.deleteAllSingerOfSongBySingerId(id);
        singerRepository.delete(singer.get());
    }

    //demo
    @Override
    public SingerDTO follow(Long userId, Long singerId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NotFoundException("This user is not existed")
        );
        Singer singer = singerRepository.findById(singerId).orElseThrow(
                ()->new NotFoundException("This user is not existed")
        );
        Follower follower = new Follower();
        follower.setUser(user);
        follower.setSinger(singer);
        followerRepository.save(follower);

        return SingerMapper.singerMapper(singer);
    }

    @Override
    public SingerDTO unFollow(Long userId, Long singerId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NotFoundException("This user is not existed")
        );
        Singer singer = singerRepository.findById(singerId).orElseThrow(
                ()->new NotFoundException("This user is not existed")
        );
        followerRepository.unFollow(userId,singerId);

        return SingerMapper.singerMapper(singer);

    }

    @Override
    public List<User> findAllFollower(Long id) {
        Singer singer = singerRepository.findById(id).orElseThrow(
                ()->new NotFoundException("This singer is not existed")
        );
        return followerRepository.findAllFollower(singer);
    }

    @Override
    public Long getTotalSinger() {
        return singerRepository.count();
    }


    public List<Singer> addPatch(List<Singer> singers){
        return singerRepository.saveAll(singers);
    }

    @Override
    public List<Singer> searchByNameOrNickName(String name) {
        name = name.toLowerCase();
        return singerRepository.searchByNameOrNickName(name,name);
    }
    public List<Singer> searchByNameOrNickName(String name,Boolean status){
        name = name.toLowerCase();
        return singerRepository.searchByNameOrNickName(name,name,status);
    }



//    public List<Singer> searchByNameOrNickName(String name,String nickName,Boolean status) {
//        System.out.println(name + " " + nickName +"   " + status);
//        return singerRepository.searchByNameOrNickNameAndStatus(name,nickName,status);
//    }


    @Override
    public List<TopTenSingerDTO> getTopTenSingerByFollowers(Long top) {
        List<Object[]>  list = singerRepository.searchTopTenSingerByFollower(top);
        List<TopTenSingerDTO> topTenSingerDTOS = new LinkedList<>();
        for (Object[] objects : list){
            TopTenSingerDTO singer = new TopTenSingerDTO();
            singer.setFollowers((Long) objects[0]);
            singer.setId((Long) objects[1]);
            singer.setName((String) objects[2]);
            singer.setNickName((String) objects[3]);
            singer.setAvatar((String) objects[4]);
            topTenSingerDTOS.add(singer);
        }
        return topTenSingerDTOS;
    }

}
