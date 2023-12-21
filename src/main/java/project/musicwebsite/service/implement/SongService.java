package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import project.musicwebsite.entity.*;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.SongDTO;
import project.musicwebsite.model.mapper.SongMapper;
import project.musicwebsite.model.request.SongRequest;
import project.musicwebsite.repositories.*;
import project.musicwebsite.service.i.ISongService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService implements ISongService {
    @Autowired
    SongRepository songRepository;
    @Autowired
    SingerRepository singerRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    PlaylistRepository playlistRepository;

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
        if (list.isEmpty()) throw new NotFoundException("Song not existed");
        return list;
    }

    @Override
    public List<Song> searchByName(String name) {

        List<Song> list = songRepository.searchByName(name);
        if (list.isEmpty()) throw new NotFoundException("Song not existed");
        return list;
    }

    @Override
    public Optional<Song> findById(Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.isEmpty()) throw new NotFoundException("Song not existed");
        return songOptional;
    }

    @Override
    public Song update(Long id, Song song) {
        return songRepository.findById(id)
                .map(song1 -> {
                    song1.setName(song.getName());
                    song1.setStatus(song.getStatus());
                    song1.setCategories(song.getCategories());
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
            for(Singer singer : singers){
                song.addSinger(singer);
            }
            return songRepository.save(song);
        }).orElseThrow(()->new NotFoundException("This song is not existed"));
    }

    @Override
    public Song removeSingerFromSong(Long songId, List<Singer> singers) {
        return songRepository.findById(songId).map(song -> {
            for(Singer singer : singers){
                song.removeSinger(singer);
            }
            return songRepository.save(song);
        }).orElseThrow(()->new NotFoundException("This song is not existed"));
    }

    public List<Song> saveListSong(List<Song> songs) {
        return songRepository.saveAll(songs);
    }

    public Song saveOneSong(Song songs) {
        return songRepository.save(songs);
    }

    public Page<Song>  getAllByPage(Integer pageNo,Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return this.songRepository.findAll(pageable);
    }

    public Page<Song>  searchSongPage(String name,Integer pageNo,Integer pageSize){

        return this.songRepository.searchAllByNameAsPage(name,pageNo,pageSize);
    }

    @Override
    public Song addCategoryToSong(Long songId, List<Category> categories) {
        return songRepository.findById(songId).map(song -> {
            for(Category category : categories){
                song.addCategory(category);
            }
            return  songRepository.save(song);
        }).orElseThrow(()->new NotFoundException("This song is not existed"));
    }

    @Override
    public Song removeCategoryToSong(Long songId, List<Category> categories) {
        return songRepository.findById(songId).map(song -> {
            for(Category category : categories){
                song.removeCategory(category);
            }
            return  songRepository.save(song);
        }).orElseThrow(()->new NotFoundException("This song is not existed"));
    }

    @Override
    public Song save(Song song) {
        return  songRepository.save(song);
    }
}
