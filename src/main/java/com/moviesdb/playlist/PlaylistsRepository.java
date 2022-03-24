package com.moviesdb.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlaylistsRepository extends JpaRepository<Playlist, Integer> {
    List<Playlist> findAllByUserId(int userId);
    Optional<Playlist> findByIdAndUserId(int id, int userId);


    @Modifying
    @Query(value = "delete " +
            "from playlist_movie pm " +
            "using playlist p " +
            "where p.id = pm.playlist_id " +
            "and p.user_id = (:userId) " +
            "and p.id not in (:playlistIds) " +
            "and pm.movie_id = (:movieId)", nativeQuery = true)
    void removeMovieFromOtherPlaylistsExcept(Collection<Integer> playlistIds, long movieId, int userId);

    @Modifying
    @Query(value = "delete " +
            "from playlist_movie pm " +
            "using playlist p " +
            "where p.id = pm.playlist_id " +
            "and p.user_id = (:userId) " +
            "and pm.movie_id = (:movieId)", nativeQuery = true)
    void removeMovieFromAllUserPlaylists(long movieId, int userId);
}
