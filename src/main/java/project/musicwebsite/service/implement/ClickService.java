package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Click;
import project.musicwebsite.entity.Song;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.ChartDTO;
import project.musicwebsite.model.dto.ClickDTO;
import project.musicwebsite.repositories.ClickRepository;
import project.musicwebsite.repositories.SongRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IClickService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ClickService implements IClickService {

    @Autowired
    ClickRepository clickRepository;

    @Autowired
    SongRepository songRepository;
    @Autowired
    UserRepository userRepository;



    @Override
    public Click save(Click click) {
        return clickRepository.save(click);
    }

    @Override
    public List<Song> getListensBySongId(Long songId) {
        return null;
    }

    @Override
    public List<Song> getAllSongByUserId() {
        return null;
    }


    @Override
    public List<ClickDTO> countListensInWeek() {
        LocalDate date = LocalDate.now().minusDays(7);
        List<ClickDTO> clickDTOS = new LinkedList<>();

        List<Object[]> list = clickRepository.countAllClickByDays(Date.valueOf(date));
        for (Object[] objects : list) {
            Song song = (Song) objects[1];
            Long id = (Long) objects[0];
            clickDTOS.add(new ClickDTO(id, song));
        }

        return clickDTOS;
    }

    @Override
    public List<ClickDTO> countListensInDay() {

        LocalDate date = LocalDate.now().minusDays(1);
        List<ClickDTO> clickDTOS = new LinkedList<>();

        List<Object[]> list = clickRepository.countAllClickByDays(Date.valueOf(date));
        for (Object[] objects : list) {
            Song song = (Song) objects[1];
            Long id = (Long) objects[0];
            clickDTOS.add(new ClickDTO(id, song));
        }

        return clickDTOS;
    }

    @Override
    public List<ClickDTO> countListensInMonth() {
        LocalDate date = LocalDate.now().minusDays(30);
        List<ClickDTO> clickDTOS = new LinkedList<>();

        List<Object[]> list = clickRepository.countAllClickByDays(Date.valueOf(date));
        for (Object[] objects : list) {
            Song song = (Song) objects[1];
            Long id = (Long) objects[0];
            clickDTOS.add(new ClickDTO(id, song));
        }

        return clickDTOS;
    }



    @Override
    public List<ClickDTO> countListens() {
        System.out.println("AALLLL");
        List<Object[]> list = clickRepository.countAllBySong();
        List<ClickDTO> clickDTOS = new LinkedList<>();
        for (Object[] objects : list) {
            Long id = (Long) objects[0];
            Song song = (Song) objects[1];
            clickDTOS.add(new ClickDTO(id, song));
        }
        return clickDTOS;
    }

    @Override
    public Long countListensBySong(Song song) {
        if (!songRepository.existsById(song.getId())) {
            throw new NotFoundException("This song is not existed");
        }
        return clickRepository.countAllBySong(song);
    }

    @Override
    public Long count() {
        return clickRepository.count();
    }


    public List<Song> getHistorySongByUserId(Long userId){

        List<Object[]> list =  clickRepository.getHistorySong(userId);
        List<Long> list1 = new LinkedList<>();
        for(Object[] objects : list){
            if(list1.contains((Long)objects[0])){
                continue;
            }
            list1.add((Long) objects[0]);
        }
        List<Song> songs = new LinkedList<>();
        int index = 0;
        for(Long id : list1){
            if(index==20) break;
            Optional<Song> song = songRepository.findById(id);
            if(song.isEmpty()) continue;
            songs.add(song.get());
            index++;
        }

        return  songs;
    }

    public List<Click> addPatch(List<Click> clicks){
        return  clickRepository.saveAll(clicks);
    }


}
