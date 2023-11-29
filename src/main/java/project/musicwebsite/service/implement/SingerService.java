package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.SingerDTO;
import project.musicwebsite.model.mapper.SingerMapper;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.ISingerService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SingerService implements ISingerService {
    @Autowired
    SingerRepository singerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Singer save(Singer BSinger) {
        Optional<User> user1 = userRepository.findByEmail(BSinger.getEmail());
        if (user1.isPresent()) throw new BadRequestException("EMAIL EXISTED");
        return singerRepository.save(BSinger);
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
    public Singer update(Long id, Singer BSinger) {
        return singerRepository.findById(id)
                .map(singer2 -> {
                    singer2.setName(BSinger.getName());
                    singer2.setBio(BSinger.getBio());
                    singer2.setSocialMediaLink(BSinger.getSocialMediaLink());
                    return singerRepository.save(singer2);
                }).orElseThrow(() -> new NotFoundException("SINGER NOT EXISTED"));
    }

    @Override
    public void delete(Long id) {
        Optional<Singer> singer = singerRepository.findById(id);
        if (singer.isEmpty()) throw new NotFoundException("SINGER NOT EXISTED");
        singerRepository.deleteById(id);
    }

    //demo
    @Override
    public SingerDTO follow(Long userId, Long singerId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new NotFoundException("User not existed");
        User user1 = user.get();


        singerRepository.findById(singerId)
                .map(singer1 ->
                {
                    if (singer1.existFollower(user1)) throw new BadRequestException("ALREADY FOLLOW");
                    singer1.addFollower(user1);
                    return singerRepository.save(singer1);
                }).orElseThrow(() -> new NotFoundException("SINGER NOT EXISTED"));

        Singer BSinger = singerRepository.findById(singerId).get();
        SingerMapper singerMapper = new SingerMapper();

        return singerMapper.singerMapper(BSinger);
    }

    @Override
    public SingerDTO unFollow(Long userId, Long singerId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new NotFoundException("USER NOT EXISTED");
        User user1 = user.get();
        singerRepository.findById(singerId)
                .map(singer -> {
                    if (!singer.existFollower(user1))
                        throw new BadRequestException("NOT FOLLOW");

                    singer.removeFollow(user1);
                    return singerRepository.save(singer);
                }).orElseThrow(() -> new NotFoundException("SINGER NOT EXISTED"));
        Singer BSinger1 = singerRepository.findById(singerId).get();
        SingerMapper singerMapper = new SingerMapper();

        return singerMapper.singerMapper(BSinger1);

    }

    @Override
    public List<User> findAllFollower(Long id) {
        List<Long> list = singerRepository.findFollowersBySingerId(id);
        if(list.isEmpty()) throw new NotFoundException("YOU DONT HAVE ANY FOLLOWERS");
        List<User> users = new LinkedList<>();
        for(Long item : list){
            Optional<User> user = userRepository.findById(item);
            users.add(user.get());
        }
        return users;
    }

    @Override
    public Long getTotalSinger() {
        return singerRepository.count();
    }
}
