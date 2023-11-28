package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.Song;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.SongDTO;
import project.musicwebsite.model.mapper.SongMapper;
import project.musicwebsite.repositories.AlbumRepository;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.repositories.SongRepository;
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

    @Override
    public Song save(Long singerId, Song song) {
        Optional<Singer> singer = singerRepository.findById(singerId);
        if(singer.isEmpty()) throw new NotFoundException("SINGER IS NOT EXISTED");
        Optional<Song> songOptional = songRepository.findByFileLyricOrFileSong(song.getFileLyric(),song.getFileSong());
        if(songOptional.isPresent()) throw new BadRequestException("SONG IS EXISTED");
        song.setSinger(singer.get());
        return songRepository.save(song);
    }

    @Override
    public List<Song> getAll() {
        List<Song> list = songRepository.findAll();
        if(list.isEmpty()) throw  new NotFoundException("Song not existed");
        return list;
    }

    @Override
    public List<Song> searchByName(String name) {
        List<Song> list = songRepository.searchByName("name");
        if(list.isEmpty()) throw new NotFoundException("Song not existed");
        return list;
    }

    @Override
    public Optional<Song> findById(Long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if(songOptional.isEmpty()) throw new NotFoundException("Song not existed");
        return songOptional;
    }

    @Override
    public Song update(Long id, Song song) {
        return songRepository.findById(id)
                .map(song1 -> {
                    song1.setName(song.getName());
                    song1.setStatus(song.getStatus());
                    song1.setCategory(song.getCategory());
                    return songRepository.save(song1);
                }).orElseThrow(()->new NotFoundException("Song not existed"));
    }

    @Override
    public Playlist saveSongToPlaylist(Long idSong, Long idPlaylist) {
        return null;
    }

    @Override
    public Playlist removeSongFromPlaylist(Long idSong, Long idPlaylist) {
        return null;
    }


    @Override
    public Album removeSongFromPlayList(Long idAlbum, Long idSong) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Song> optionalSong = songRepository.findById(id);
        if(optionalSong.isEmpty()) throw new NotFoundException("SONG NOT EXISTED");
        songRepository.deleteById(id);
    }

    @Override
    public Album addSongToAlbum(Long albumId, Long songId) {
        Optional<Album> album = albumRepository.findById(albumId);
        if(album.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        Optional<Song> song = songRepository.findById(songId);
        Singer singer = album.get().getSinger();
        if(!singer.equals(song.get().getSinger())) throw new BadRequestException("YOU CAN ONLY ADD YOUR OWN SONGS");
        song.map(song1->{
            song1.setAlbum(album.get());
            return songRepository.save(song1);
        });
        return album.get();
    }

    @Override
    public List<SongDTO> findSongBySingerId(Long singerId) {
        List<Long> ids = songRepository.findSongBySingerId(singerId);
        List<Song> songs = new LinkedList<>();
        for(Long id : ids){
            songRepository.findById(id).map(song->{
                        songs.add(song);
                        return song;
                    }
            ).orElseThrow(()->new NotFoundException("SONG NOT EXISTED"));
        }
        if(songs.isEmpty()) throw new NoContentException("DONT HAVE ANY SONG");
        return SongMapper.convertList(songs);

//        if(songs.isEmpty()) throw new NoContentException("YOU DONT HAVE ANY SONG");

    }
}
