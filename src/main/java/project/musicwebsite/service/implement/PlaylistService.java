package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Song;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.PlaylistRepository;
import project.musicwebsite.repositories.SongRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IPlaylistService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService implements IPlaylistService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    SongRepository songRepository;

    @Override
    public Playlist save(Long userId,Playlist playlist) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new NotFoundException("USER NOT EXISTED");
        playlist.setCreator(user.get());
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist findFavoritePlaylist(Long userId) {
        Optional<Playlist> playlist = playlistRepository.findFirstByUserId(userId);
        if(playlist.isEmpty()) throw new NotFoundException("PLAYLIST NOT EXISTED");
        return playlist.get();
    }

    @Override
    public List<Playlist> getAllForUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new NotFoundException("USER NOT EXISTED");
        List<Playlist> list = playlistRepository.findPlaylistByUserId(userId);
        if(list.isEmpty()) throw new NoContentException("YOU DONT HAVE ANY PLAYLIST");
        return list;
    }

    @Override
    public List<Playlist> getAll() {
        List<Playlist> list = playlistRepository.findAll();
        if(list.isEmpty()) throw new NoContentException("Dont have any playlist");
        return list;
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if(playlist.isEmpty()) throw new NotFoundException("Playlist not existed");
        return playlist;
    }

    @Override
    public Playlist update(Long id, Playlist playlist) {
        return playlistRepository.findById(id)
                .map(playlist1 -> {
                    playlist1.setName(playlist.getName());
                    playlist1.setStatus(playlist.getStatus());
                    return playlistRepository.save(playlist1);
                }).orElseThrow(()->new NotFoundException("Playlist not existed"));
    }

    @Override
    public void delete(Long id){
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if(playlist.isEmpty()) throw new NotFoundException("This playlist not existed");
        playlistRepository.deleteById(id);
    }

    @Override
    public Long getTotalPlayList() {
        return playlistRepository.count();
    }

    @Override
    public List<Song> getAllSongForPlaylist(Long id) {
        List<Long> list = playlistRepository.findAllSongPlaylist(id);
        if(list.isEmpty()) throw new NoContentException("List empty");
        List<Song> songs = new LinkedList<>();
        for(Long id1 : list){
            Optional<Song> song = songRepository.findById(id);
            if(song.isEmpty()) throw new NotFoundException("Song is not existed");
            songs.add(song.get());
        }
        return songs;
    }
}
