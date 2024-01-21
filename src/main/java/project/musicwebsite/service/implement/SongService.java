package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.musicwebsite.entity.Category;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.Song;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.TopSongByCategoryDTO;
import project.musicwebsite.repositories.*;
import project.musicwebsite.service.i.ISongService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService implements ISongService {

    private SongRepository songRepository;
    private SingerRepository singerRepository;
    private AlbumRepository albumRepository;
    private UserRepository userRepository;
    private PlaylistRepository playlistRepository;
    private CategoryRepository categoryRepository;
    private SongOfPlaylistRepository songOfPlaylistRepository;
    private ClickRepository clickRepository;


    @Autowired
    public SongService(SongRepository songRepository, SingerRepository singerRepository,
                       AlbumRepository albumRepository, UserRepository userRepository,
                       PlaylistRepository playlistRepository, CategoryRepository categoryRepository,
                       SongOfPlaylistRepository songOfPlaylistRepository, ClickRepository clickRepository) {
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.categoryRepository = categoryRepository;
        this.songOfPlaylistRepository = songOfPlaylistRepository;
        this.clickRepository = clickRepository;
    }

    @Override
    public Song save(Long creator, Song song) {
        Optional<Singer> singer = singerRepository.findById(creator);
        if (singer.isEmpty()) throw new NotFoundException("SINGER IS NOT EXISTED");
        if (singer.get().getRole() != 3 && singer.get().getRole() != 0)
            throw new BadRequestException("U cant created song");
        System.out.println("creator id :" + creator);

        Optional<Song> songOptional = songRepository.findByFileLyricOrFileSound(song.getFileLyric(), song.getFileSound());
        if (songOptional.isPresent()) throw new BadRequestException("SONG IS EXISTED");
        song.setCreator(singer.get());
        return songRepository.save(song);
    }

    @Override
    public List<Song> getAll() {
        List<Song> list = songRepository.findAll();
        return list;
    }

    @Override
    public List<Song> searchByName(String name) {
        String name1 = name.toLowerCase();
        List<Song> list = songRepository.searchByName(name1);
        return list;
    }

    public List<Song> searchByName(String name, Integer status) {
        name = name.toLowerCase();
        List<Song> list = songRepository.searchByName(name, status);
        return list;
    }

    @Override
    public Optional<Song> findById(Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.isEmpty()) throw new NotFoundException("Song not existed");
        return songOptional;
    }

    @Override
    @Transactional
    public Song update(Song song) {
        return songRepository.findById(song.getId())
                .map(song1 -> {
                    if (song.getFileLyric() != null && !song.getFileLyric().isBlank()) {
                        song1.setFileLyric(song.getFileLyric());
                    }
                    if (song.getFileSound() != null && !song.getFileSound().isBlank()) {
                        song1.setFileSound(song.getFileSound());
                    }
                    if (song.getAvatar() != null && !song.getAvatar().isBlank()) {
                        song1.setAvatar(song.getAvatar());
                    }
                    song1.setName(song.getName());
                    song1.setStatus(song.getStatus());
                    song1.setCategories(song.getCategories());
                    song1.setModifiedDate(new Date());
                    song1.setSingers(song.getSingers());
                    return songRepository.save(song1);
                }).orElseThrow(() -> new NotFoundException("Song not existed"));
    }

    @Override
    public List<TopSongByCategoryDTO> getTopSongByCategory(Long top) {
        List<Object[]> list = songRepository.getTopCategoryWithMostListens(3);
        List<TopSongByCategoryDTO> topSongByCategoryDTOS = new LinkedList<>();

        for (Object[] objects : list) {
            System.out.println("ok" + objects[1] + "   " + objects[0]);
            Long id = (Long) objects[1];
//            Long times = (Long) objects[0];
            TopSongByCategoryDTO topSongByCategoryDTO = new TopSongByCategoryDTO();
            System.out.println("oooo");
            Optional<Category> category = categoryRepository.findById(id);
            System.out.println("ok111");
            if (category.isEmpty()) continue;
            Long categoryId = category.get().getId();

            topSongByCategoryDTO.setCategory(category.get());
            List<Object[]> listSongIds = songRepository.getTopSongWithTheMostListensByCategoryId(
                    categoryId, 10);
            List<Song> songs = new LinkedList<>();

            for (Object[] objects1 : listSongIds) {
                Long idSong = (Long) objects1[1];
                Optional<Song> song = songRepository.findById(idSong);
                if (song.isEmpty() || song.get().getStatus()!=2) continue;
                songs.add(song.get());
            }

            topSongByCategoryDTO.setSongs(songs);
            topSongByCategoryDTOS.add(topSongByCategoryDTO);

        }
        return topSongByCategoryDTOS;
    }


    @Override
    @Transactional
    public void delete(Long id) {
        System.out.println("id need Delete" + id);
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("This song is not existed"));
//        song.get
        songRepository.delete(song);
    }


    @Override
    public List<Song> findSongByCreatorId(Long creatorId) {
        List<Song> songs = songRepository.findSongByCreatorId(creatorId);
        return songs;
    }

    @Override
    public Long getTotalSong() {
        return songRepository.count();
    }

    @Override
    public Long countSongBySingerId(Long singerId) {
        return null;
    }


    public List<Song> saveListSong(List<Song> songs) {
        return songRepository.saveAll(songs);
    }

    public Page<Song> getAllByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.songRepository.findAll(pageable);
    }

    public Page<Song> searchSongPage(String name, Integer pageNo, Integer pageSize) {
        String name1 = name.toLowerCase();
        return this.songRepository.searchAllByNameAsPage(name1, pageNo, pageSize);
    }

    @Override
    public Song save(Song song) {
        clickRepository.countAllBySong();

        return songRepository.save(song);
    }


    @Override
    public List<Song> getSongsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("This category is not existed"));
        return songRepository.findAllByCategoriesContaining(category);
    }

    @Override
    public List<Song> getSongsBySingerId(Long singerId) {
        Singer singer = singerRepository.findById(singerId)
                .orElseThrow(() -> new NotFoundException("This singer is not existed"));

        return songRepository.findAllBySingersContaining(
                singer
        );
    }

    @Override
    public List<Song> findAllSongByNameAndCategoryAndSinger(
            String name, Long categoryId, Long singerId) {
        name = name.toLowerCase();
        Category category;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("This category is not existed"));
        } else category = null;
        Singer singer;
        if (singerId != null) {
            singer = singerRepository.findById(singerId)
                    .orElseThrow(() -> new NotFoundException("This singer is not existed"));
        } else singer = null;

        return songRepository.searchAllByNameOrCategoriesContainingOrSingersContaining(
                name, category, singer
        );

    }


}
