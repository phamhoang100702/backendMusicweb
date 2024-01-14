package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Playlist;
import project.musicwebsite.entity.Song;
import project.musicwebsite.entity.SongOfPlaylist;
import project.musicwebsite.entity.User;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.PlaylistRepository;
import project.musicwebsite.repositories.SongOfPlaylistRepository;
import project.musicwebsite.repositories.SongRepository;
import project.musicwebsite.repositories.UserRepository;
import project.musicwebsite.service.i.IPlaylistService;

import java.util.HashSet;
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
    @Autowired
    SongOfPlaylistRepository songOfPlaylistRepository;

    @Override
    public Playlist save(Long userId, Playlist playlist) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new NotFoundException("USER NOT EXISTED");
        playlist.setCreator(user.get());
        return playlistRepository.save(playlist);
    }

    public Playlist save( Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist findFavoritePlaylist(Long userId) {
        Optional<Playlist> playlist = playlistRepository.findFavoritePlaylistForUserByUserId(userId);
        if (playlist.isEmpty()) throw new NotFoundException("PLAYLIST NOT EXISTED");
        return playlist.get();
    }

    @Override
    public List<Playlist> getAllPlaylistByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new NotFoundException("USER NOT EXISTED");
        List<Playlist> list = playlistRepository.findPlaylistByUserId(userId);
        if (list.isEmpty()) throw new NoContentException("YOU DONT HAVE ANY PLAYLIST");
        return list;
    }

    @Override
    public List<Playlist> getAll() {
        List<Playlist> list = playlistRepository.findAll();
        return list;
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isEmpty()) throw new NotFoundException("Playlist not existed");
        return playlist;
    }

    @Override
    public Playlist update( Playlist playlist) {
        return playlistRepository.findById(playlist.getId())
                .map(playlist1 -> {
                    playlist1.setName(playlist.getName());
                    playlist1.setStatus(playlist.getStatus());
                    return playlistRepository.save(playlist1);
                }).orElseThrow(() -> new NotFoundException("Playlist not existed"));
    }

    @Override
    public void delete(Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isEmpty()) throw new NotFoundException("This playlist not existed");
        playlistRepository.deleteById(id);
    }

    @Override
    public Long getTotalPlayList() {
        return playlistRepository.count();
    }

    @Override
    public List<Song> getAllSongByPlaylist(Long id) {
        List<Long> list = songOfPlaylistRepository.findAllSongPlaylist(id);
        if (list.isEmpty()) throw new NoContentException("List empty");
        List<Song> songs = new LinkedList<>();

        for (Long id1 : list) {
            System.out.println(id1);
            Optional<Song> song = songRepository.findById(id1);
            if (song.isEmpty()) throw new NotFoundException("Song is not existed");
            songs.add(song.get());
        }
        return songs;
    }

    @Override
    public List<Playlist> searchAllPlaylistByNameForUser(String name) {
        name=name.toLowerCase();
        return playlistRepository.searchAllPlaylistByNameForUser(name);
    }

    @Override
    public List<Playlist> searchAllMainPlaylist(String name) {
        name=name.toLowerCase();
        return playlistRepository.searchAllMainPagePlaylistByName(name);
    }

    @Override
    public List<Playlist> getAllMainpagePlaylist() {
        return playlistRepository.getAllMainpagePlaylist();
    }

    @Override
    public Long count() {
        return playlistRepository.count();
    }

    @Override
    public Playlist saveSongsToPlaylist(Long playlistId,Long[] arr) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(()->new NotFoundException("This playlist is not existed"));
        List<SongOfPlaylist> songOfPlaylists = new LinkedList<>();
        for(Long id : arr){
            Optional<Song> song = songRepository.findById(id);
            if(song.isEmpty()) continue;;
            SongOfPlaylist songOfPlaylist = new SongOfPlaylist();
            songOfPlaylist.setPlaylist(playlist);
            songOfPlaylist.setSong(song.get());
            songOfPlaylists.add(songOfPlaylist);
        }
        songOfPlaylistRepository.saveAll(songOfPlaylists);
        return  playlist;
    }

    @Override
    public Playlist addSongToFavoritePlaylist(Long userId, Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(()->
                new NotFoundException("This song is not existed"));
        Playlist playlist = playlistRepository.findFavoritePlaylistForUserByUserId(userId)
                .orElseThrow(()->new NotFoundException("This playlist is not existed"));
        SongOfPlaylist songOfPlaylist = new SongOfPlaylist();
        songOfPlaylist.setPlaylist(playlist);
        songOfPlaylist.setSong(song);
        songOfPlaylistRepository.save(songOfPlaylist);
        return playlist;
    }

    @Override
    public Playlist removeSongFromFavoritePlaylist(Long userId, Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(()->
                new NotFoundException("This song is not existed"));
        Playlist playlist = playlistRepository.findFavoritePlaylistForUserByUserId(userId)
                .orElseThrow(()->new NotFoundException("This playlist is not existed"));
        songOfPlaylistRepository.deletePlaylistBySongIdAndPlaylistId(userId,playlist.getId());
        return playlist;
    }
}
