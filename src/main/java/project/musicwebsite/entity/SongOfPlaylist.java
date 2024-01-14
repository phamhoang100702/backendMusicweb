package project.musicwebsite.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Table(name = "SongOfPlaylisttbl")
@Entity
@Data
@NoArgsConstructor
public class SongOfPlaylist {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = Song.class
    )
    @JoinColumn(
            name = "songId"
    )
    Song song;
    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = Playlist.class
    )
    @JoinColumn(
            name = "playlistId"
    )
    Playlist playlist;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongOfPlaylist that = (SongOfPlaylist) o;
        return Objects.equals(id, that.id) && Objects.equals(song, that.song) && Objects.equals(playlist, that.playlist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, song, playlist);
    }
}
