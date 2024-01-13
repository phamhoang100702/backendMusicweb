package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.UserDTO;
import project.musicwebsite.model.mapper.UserMapper;
import project.musicwebsite.repositories.*;
import project.musicwebsite.service.i.IUserService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SingerRepository singerRepository;

//    @Autowired
//    PUserRepository pUserRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    ClickRepository clickRepository;
    @Autowired
    SongRepository songRepository;


    @Override
    public User save(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) throw new BadRequestException("Email existed");
        User user1 = userRepository.save(user);

        return user1;
    }

    public List<User> searchAllUserByName(String name){
        name = name.toLowerCase();
        return  userRepository.searchAllByName(name);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new NotFoundException("User dont exist");

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = userRepository.findByRole(1);

        return (list);
    }

    @Override
    public User update( User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        return userOptional.map(user1 -> {
            user1.setName(user.getName());
            user1.setModifiedDate(new Date());
            user1.setEmail(user.getEmail());
            String pw = passwordEncoder.encode(user.getPassword());
            user1.setPassword(pw);
            return userRepository.save(user1);
        }).orElseThrow(() -> new NotFoundException("User dont exist"));
    }

    @Override
    public void delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) throw new NotFoundException("User dont exist");

        userRepository.deleteById(id);
    }

    @Override
    public List<Singer> findFollowedSinger(Long id) {
        List<Long> list = userRepository.findFollowedSingerByUserId(id);
        if (list.isEmpty()) throw new NotFoundException("You have not follow anyone yet");
        List<Singer> singers = new ArrayList<>();
        for (Long item : list) {
            singers.add(singerRepository.findById(item).get());
        }
        return singers;
    }

    @Override
    public Long count() {
        return userRepository.count();
    }


    @Override
    public User switchToPremium(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isEmpty() || user.get().getRole()!=1) throw new NotFoundException("This user is not exist");
//        user.map(user1 -> {
//            Role role = roleRepository.findByName("PREMIUM").get();
//            user1.addRole(role);
//            return userRepository.save(user1);
//        });
//        userRepository.switchToPremium(id,new Date());
//        return user.get();
        return null;
    }

    @Override
    public List<User> saveListUser(ArrayList<User> users) {
        List<User> list = new ArrayList<>();
        for(User user :  users){
            User u1 = save(user);
            list.add(u1);
        }
        return list;
    }

    @Override
    public Boolean checkFollowedSinger(Long userId, Long singerId) {
        return null;
    }

    @Override
    public List<ChartDTO> getChartInforInTimePeriod(Long time) {
        LocalDate date = LocalDate.now().minusDays(time);
        List<ChartDTO> chartDTOS = new LinkedList<>();

        List<Object[]> list = userRepository.getChartInforInTimePeriod(java.sql.Date.valueOf(date));
        List<Object[]> list1 = singerRepository.getChartInforInTimePeriod(java.sql.Date.valueOf(date));
        List<Object[]> list2 = songRepository.getInfoInTimePeriod(java.sql.Date.valueOf(date));
        List<Object[]> list3 = clickRepository.getChartInforInTimePeriod(java.sql.Date.valueOf(date));
        Map<String,Integer> map = new TreeMap<>();


        for (Object[] objects : list) {
            ChartDTO chartDTO = new ChartDTO();
            Date date1 = (Date) objects[1];
            System.out.println(date1);
      }

        return chartDTOS;
    }
}
