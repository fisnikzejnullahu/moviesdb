package com.moviesdb.playlist;

import com.moviesdb.movie.repository.MoviesRepository;
import com.moviesdb.user.UserAccount;
import com.moviesdb.user.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.moviesdb.user.UsersService.getLoggedInUser;

@Service
@Transactional
public class PlaylistsService {

    private final PlaylistsRepository playlistsRepository;
    private final MoviesRepository moviesRepository;

    public PlaylistsService(PlaylistsRepository playlistsRepository, MoviesRepository moviesRepository) {
        this.playlistsRepository = playlistsRepository;
        this.moviesRepository = moviesRepository;
    }

    public List<Playlist> getPlaylistsOfLoggedInUser() {
        UserAccount user = getLoggedInUser();
        return playlistsRepository.findAllByUserId(user.getId());
    }

    public void save(String playlistName) {
        UserAccount user = getLoggedInUser();
        Playlist playlist = new Playlist(playlistName);
        playlist.setUser(user);
        playlistsRepository.saveAndFlush(playlist);
    }

    public Playlist update(int playlistId, String newPlaylistName) {
        return playlistsRepository
                .findByIdAndUserId(playlistId, UsersService.getLoggedInUser().getId())
                .map(playlist -> {
                    playlist.setName(newPlaylistName);
                    playlist.setLastUpdated(LocalDate.now());
                    return playlist;
                }).orElse(null);
    }

    public void delete(int playlistId) {
        playlistsRepository.findByIdAndUserId(playlistId, UsersService.getLoggedInUser().getId()).ifPresent(playlistsRepository::delete);
    }

    public void addMovieInPlaylists(String playlistsId, long movieId) {
        var loggedInUserId = UsersService.getLoggedInUser().getId();
        if (playlistsId.isEmpty()) {
            playlistsRepository.removeMovieFromAllUserPlaylists(movieId, loggedInUserId);
            return;
        }

        String[] playlistsIdArray = playlistsId.split(",");
        for (String playlistId : playlistsIdArray) {
            int id = Integer.parseInt(playlistId);

            playlistsRepository.findByIdAndUserId(id, loggedInUserId)
                    .ifPresent(playlist -> {
                        // add only if playlist does not already contain movie
                        if (playlist.getMovies().stream().filter(movie -> movie.getId() == movieId).findAny().isEmpty()) {
                            moviesRepository.findById(movieId).ifPresent(movie -> {
                                playlist.getMovies().add(movie);
                                playlist.setLastUpdated(LocalDate.now());
                            });
                        }
                    });
        }


        //remove movie from all other user playlists that did not come with parameter
        Integer[] playlistsIdIntArray = new Integer[playlistsIdArray.length];
        for (int i = 0; i < playlistsIdArray.length; i++) {
            playlistsIdIntArray[i] = Integer.parseInt(playlistsIdArray[i]);
        }

        playlistsRepository.removeMovieFromOtherPlaylistsExcept(List.of(playlistsIdIntArray), movieId, loggedInUserId);
    }

    public void removeMovieFromPlaylist(int playlistId, long movieId) {
        playlistsRepository.findByIdAndUserId(playlistId, UsersService.getLoggedInUser().getId()).ifPresent(playlist -> {
            playlist.getMovies().stream().filter(movie -> movie.getId() == movieId).findAny().ifPresent(movie -> {
                playlist.getMovies().remove(movie);
                playlist.setLastUpdated(LocalDate.now());
            });
        });
    }

    public Optional<Playlist> getPlaylist(int playlistId) {
        return playlistsRepository.findByIdAndUserId(playlistId, UsersService.getLoggedInUser().getId());
    }
}
