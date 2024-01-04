package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.*;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.SongDTO;
import project.musicwebsite.model.mapper.SongMapper;
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

    @Autowired
    public SongService(SongRepository songRepository, SingerRepository singerRepository, AlbumRepository albumRepository, UserRepository userRepository, PlaylistRepository playlistRepository, CategoryRepository categoryRepository) {
        this.songRepository = songRepository;
        this.singerRepository = singerRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Song save(Long creator, Song song) {
        Optional<User> singer = userRepository.findById(creator);
        if (singer.isEmpty()) throw new NotFoundException("SINGER IS NOT EXISTED");
        if (singer.get().getRole() != 3 && singer.get().getRole() != 0)
            throw new BadRequestException("U cant created song");

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

        List<Song> list = songRepository.searchByName(name);
        return list;
    }
    public List<Song> searchByName(String name,Integer status) {
        List<Song> list = songRepository.searchByName(name,status);
        return list;
    }

    @Override
    public Optional<Song> findById(Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.isEmpty()) throw new NotFoundException("Song not existed");
        return songOptional;
    }

    @Override
    public Song update(Song song) {
        return songRepository.findById(song.getId())
                .map(song1 -> {
                    song1.setName(song.getName());
                    song1.setStatus(song.getStatus());
                    song1.setCategories(song.getCategories());
                    song1.setFileSound(song.getFileSound());
                    song1.setModifiedDate(new Date());
                    return songRepository.save(song1);
                }).orElseThrow(() -> new NotFoundException("Song not existed"));
    }

    @Override
    public Playlist saveSongToPlaylist(Long songId, Long playlistId) {
        Optional<Song> song = songRepository.findById(songId);
        if (song.isEmpty()) throw new NotFoundException("This song is not existed");
        return playlistRepository.findById(playlistId)
                .map(playlist -> {
                    playlist.addSong(song.get());
                    return playlistRepository.save(playlist);
                }).orElseThrow(() -> new NotFoundException("This playlist is not existed"));
    }

    @Override
    public Playlist removeSongFromPlaylist(Long songId, Long playlistId) {
        Optional<Song> song = songRepository.findById(songId);
        if (song.isEmpty()) throw new NotFoundException("This song is not existed");
        return playlistRepository.findById(playlistId)
                .map(playlist -> {
                    playlist.removeSong(song.get());
                    return playlistRepository.save(playlist);
                }).orElseThrow(() -> new NotFoundException("This playlist is not existed"));
    }


    @Override
    public void delete(Long id) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if (optionalSong.isEmpty()) throw new NotFoundException("SONG NOT EXISTED");
        songRepository.deleteById(id);
    }

    @Override
    public Album addSongToAlbum(Long albumId, Long songId) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (album.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        Optional<Song> song = songRepository.findById(songId);
        Singer singer = album.get().getSinger();
        if (!singer.equals(song.get().getCreator())) throw new BadRequestException("YOU CAN ONLY ADD YOUR OWN SONGS");
        song.map(song1 -> {
            song1.setAlbum(album.get());
            return songRepository.save(song1);
        });
        return album.get();
    }

    @Override
    public List<SongDTO> findSongBySingerId(Long singerId) {
        List<Long> ids = songRepository.findSongBySingerId(singerId);
        List<Song> songs = new LinkedList<>();
        for (Long id : ids) {
            songRepository.findById(id).map(song -> {
                        songs.add(song);
                        return song;
                    }
            ).orElseThrow(() -> new NotFoundException("SONG NOT EXISTED"));
        }
        if (songs.isEmpty()) throw new NoContentException("DONT HAVE ANY SONG");
        return SongMapper.convertList(songs);
    }

    @Override
    public Long getTotalSong() {
        return songRepository.count();
    }

    @Override
    public Song saveSingersToSong(Long songId, List<Singer> singers) {

        return songRepository.findById(songId).map(song -> {
            for (Singer singer : singers) {
                song.addSinger(singer);
            }
            return songRepository.save(song);
        }).orElseThrow(() -> new NotFoundException("This song is not existed"));
    }

    @Override
    public Song removeSingerFromSong(Long songId, List<Singer> singers) {
        return songRepository.findById(songId).map(song -> {
            for (Singer singer : singers) {
                song.removeSinger(singer);
            }
            return songRepository.save(song);
        }).orElseThrow(() -> new NotFoundException("This song is not existed"));
    }

    public List<Song> saveListSong(List<Song> songs) {
        return songRepository.saveAll(songs);
    }

    public Song saveOneSong(Song songs) {
        return songRepository.save(songs);
    }

    public Page<Song> getAllByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.songRepository.findAll(pageable);
    }

    public Page<Song> searchSongPage(String name, Integer pageNo, Integer pageSize) {
        return this.songRepository.searchAllByNameAsPage(name, pageNo, pageSize);
    }

    @Override
    public Song addCategoryToSong(Long songId, List<Category> categories) {
        return songRepository.findById(songId).map(song -> {
            for (Category category : categories) {
                song.addCategory(category);
            }
            return songRepository.save(song);
        }).orElseThrow(() -> new NotFoundException("This song is not existed"));
    }

    @Override
    public Song removeCategoryToSong(Long songId, List<Category> categories) {
        return songRepository.findById(songId).map(song -> {
            for (Category category : categories) {
                song.removeCategory(category);
            }
            return songRepository.save(song);
        }).orElseThrow(() -> new NotFoundException("This song is not existed"));
    }

    @Override
    public Song save(Song song) {
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
