package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SingerRepository singerRepository;

    @Override
    public User save(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()) throw new BadRequestException("Email existed");
        return  userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw  new NotFoundException("User dont exist");

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = userRepository.findByRole(1);
        if(list.isEmpty()) throw new NotFoundException("Do not have any users");
        return  list;
    }

    @Override
    public User update(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user1 -> {
            user1.setName(user.getName());
            user1.setModifiedDate(new Date());
//            user1.setPassword(user.getPassword());

            return userRepository.save(user1);
        }).orElseThrow(()-> new NotFoundException("User dont exist"));
    }

    @Override
    public void delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())throw new NotFoundException("User dont exist");

        userRepository.deleteById(id);
    }

    @Override
    public List<Singer> findFollowedSinger(Long id) {
        List<Long> list = userRepository.findFollowedSingerByUserId(id);
        if(list.isEmpty()) throw new NotFoundException("YOU HAVEN'T FOLLOWED ANYONE YET ");
        List<Singer> singers = new ArrayList<>();
        for(Long item : list){
            singers.add(singerRepository.findById(item).get());
        }
       return  singers;
    }
}
