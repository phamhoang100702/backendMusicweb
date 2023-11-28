package project.musicwebsite.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.musicwebsite.entity.Album;
import project.musicwebsite.entity.Singer;
import project.musicwebsite.exception.NotFoundException;
import project.musicwebsite.repositories.AlbumRepository;
import project.musicwebsite.repositories.SingerRepository;
import project.musicwebsite.service.i.IAlbumService;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService implements IAlbumService {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    SingerRepository singerRepository;

    @Override
    public Album save(Long id, Album album) {
        Optional<Singer> singer = singerRepository.findById(id);
        album.setSinger(singer.get());
        return albumRepository.save(album);
    }

    @Override
    public Album update(Long id, Album album) {
        return albumRepository.findById(id)
                .map(album2 -> {
                    album2.setName(album.getName());
                    album2.setPublish(album.getPublish());
                    album2.setThumbnail(album.getThumbnail());
                    return  albumRepository.save(album2);
                }).orElseThrow(()-> new NotFoundException("ALBUM NOT EXISTED"));
    }

    @Override
    public Optional<Album> findById(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        if(album.isEmpty()) throw new NotFoundException("ALBUM NOT EXISTED");
        return album;
    }

    @Override
    public List<Album> getAll() {
        List<Album> list = albumRepository.findAll();
        if(list.isEmpty()) throw new NotFoundException("DONT HAVE ANY ALBUM");
        return list;
    }

    @Override
    public List<Album> getBySinger(Long singerId) {
//        List<Album> list = albumRepository.findAlbumBySingerId(singerId);
//        if(list.isEmpty()) throw new NotFoundException("DONT HAVE ANY ALBUM");
//        return list;
        return  null;
    }

    @Override
    public void delete(Long id) {
        Optional<Album> album1 = albumRepository.findById(id);
        if(album1.isEmpty()) throw  new NotFoundException("ALBUM NOT EXISTED");
        albumRepository.deleteById(id);
    }
}
