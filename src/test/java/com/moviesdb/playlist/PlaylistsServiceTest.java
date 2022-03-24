package com.moviesdb.playlist;

import com.moviesdb.MoviesDbApplication;
import com.moviesdb.user.UserAccount;
import com.moviesdb.user.UsersRepository;
import com.moviesdb.user.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoviesDbApplication.class)
@Transactional
class PlaylistsServiceTest {

    private static int movieId;
    @Autowired
    private PlaylistsService playlistsService;
    @Autowired
    private PlaylistsRepository playlistsRepository;

    @Autowired
    private UsersRepository usersRepository;
    private UserAccount userAccount;

    @BeforeEach
    void init() {
        this.userAccount = usersRepository.findByUsername("user1").get();
        movieId = 2;
    }

    @Test
    void playlistCreateAndChangeName() {
        try (MockedStatic<UsersService> utilities = Mockito.mockStatic(UsersService.class)) {
            utilities.when(UsersService::getLoggedInUser).thenReturn(this.userAccount);

            int playlistId = createRandomPlaylist();

            String newName = "some-name-changed";
            playlistsService.update(playlistId, newName);

            Optional<Playlist> findPlaylist = playlistsRepository.findById(playlistId);
            assertThat(findPlaylist).isNotEmpty();

            Playlist newPlaylist = findPlaylist.get();
            assertEquals(newName, newPlaylist.getName());
        }
    }

    @Test
    void shouldAddAndRemoveMovieInMultiplePlaylists() {
        try (MockedStatic<UsersService> utilities = Mockito.mockStatic(UsersService.class)) {
            utilities.when(UsersService::getLoggedInUser).thenReturn(this.userAccount);

            int playlist1Id = createRandomPlaylist();
            int playlist2Id = createRandomPlaylist();
            String playlistIdsCommaSeparated = playlist1Id + "," + playlist2Id;

            playlistsService.addMovieInPlaylists(playlistIdsCommaSeparated, movieId);

            for (String playlistId : playlistIdsCommaSeparated.split(",")) {
                int id = Integer.parseInt(playlistId);
                Playlist playlist = playlistsRepository.findById(id).get();
                assertThat(playlist.getMovies().stream().filter(movie -> movie.getId() == movieId).findAny()).isNotEmpty();

                playlistsService.removeMovieFromPlaylist(id, movieId);

                playlist = playlistsRepository.findById(id).get();
                assertThat(playlist.getMovies().stream().filter(movie -> movie.getId() == movieId).findAny()).isEmpty();
            }
        }
    }

    int createRandomPlaylist() {
        Playlist playlist = new Playlist("some-name" + System.nanoTime());
        playlist.setUser(this.userAccount);
        return playlistsRepository.saveAndFlush(playlist).getId();
    }
}