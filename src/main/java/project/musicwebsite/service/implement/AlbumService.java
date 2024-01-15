package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.entity.Song;
import project.musicwebsite.exception.BadRequestException;
import project.musicwebsite.exception.NoContentException;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.model.dto.AlbumDTO;
import project.musicwebsite.model.dto.SongDTO;
import project.musicwebsite.model.mapper.AlbumMapper;
import project.musicwebsite.model.mapper.SongMapper;
import project.musicwebsite.repositories.AlbumRepository;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.repositories.SongRepository;
import project.musicwebsite.service.i.IAlbumService;

import java.util.*;

@Service
public class AlbumService implements IAlbumService {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    SingerRepository singerRepository;
    @Autowired
    SongRepository songRepository;

    @Override
    public Album save(Album album) {
        System.out.println(album.getThumbnail());
        Set<Song> songs = album.getSongs();
        Album album1 = albumRepository.save(album);
        for(Song song : songs){
            songRepository.findById(song.getId()).map((song1 -> {
                if(song1.getCreator()!=null && song1.getCreator().getId()==album.getSinger().getId()){
                    song1.setAlbum(album1);
                }
                return songRepository.save(song1);
            })).orElseThrow(()->new NotFoundException("This song is not existed"));
        }
        return album1;
    }

    @Override
    public Album update(Album album) {
        return albumRepository.findById(album.getId())
                .map(album2 -> {
                    album2.setName(album.getName());
                    album2.setThumbnail(album.getThumbnail());
                    album2.setModifiedDate(new Date());
                    return albumRepository.save(album2);
                }).orElseThrow(() -> new NotFoundException("ALBUM NOT EXISTED"));
    }

    @Override
    public Album findById(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        return album.get();
    }

    @Override
    public List<Album> getAll() {
        List<Album> list = albumRepository.findAll();
        if (list.isEmpty()) throw new NotFoundException("DONT HAVE ANY ALBUM");

        return list;
    }

    @Override
    public List<AlbumDTO> getBySinger(Long singerId) {

        Optional<Singer> singer = singerRepository.findById(singerId);
        if (singer.isEmpty()) throw new NotFoundException("SINGER NOT EXISTED");
        List<Album> list = albumRepository.findAlbumBySingerId(singer.get());
        if (list.isEmpty()) throw new NotFoundException("DONT HAVE ANY ALBUM");
        List<AlbumDTO> albumDTOS = new LinkedList<>();
        for (Album album : list) {
            albumDTOS.add(AlbumMapper.convert(album));
        }
        return albumDTOS;
    }

    @Override
    public void delete(Long id) {
        Optional<Album> album1 = albumRepository.findById(id);
        if (album1.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        List<Long> ids = songRepository.findSongsByAlbumId(id);
        for (Long id1 : ids) {
            songRepository.findById(id1).map(song -> {
                song.setAlbum(null);
                return songRepository.save(song);
            }).orElseThrow(() -> new NotFoundException("SONG NOT EXISTED"));
        }
        albumRepository.deleteById(id);
    }

    @Override
    public List<Album> getByName(String string) {
        List<Album> albums = albumRepository.searchAlbumByNames(string);
        if (albums.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        return albums;
    }

    @Override
    public List<SongDTO> getSongByAlbumId(Long albumId) {
        List<Long> ids = songRepository.findSongsByAlbumId(albumId);
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
    public Long count() {
        return albumRepository.count();
    }

    @Override
    public Long countBySingerId() {
        return null;
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
    public Album removeSongFromAlbum(Long albumId, Long songId) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (album.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        Optional<Song> song = songRepository.findById(songId);
        Singer singer = album.get().getSinger();
        if (!singer.equals(song.get().getCreator())) throw new BadRequestException("YOU CAN ONLY ADD YOUR OWN SONGS");
        song.map(song1 -> {
            song1.setAlbum(null);
            return songRepository.save(song1);
        });
        return album.get();
    }
}
