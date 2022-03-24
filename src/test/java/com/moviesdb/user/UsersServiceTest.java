package com.moviesdb.user;

import com.moviesdb.MoviesDbApplication;
import com.moviesdb.playlist.Playlist;
import com.moviesdb.playlist.PlaylistsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MoviesDbApplication.class)
@Transactional
class UsersServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlaylistsRepository playlistsRepository;

    @Autowired
    private UsersService usersService;

    @Test
    void userWithPlaylistShouldBeCreatedAndDeleted() {
        int createdUserId = usersService.create("test-username", "123");
        System.out.println("createdUserId = " + createdUserId);

        //test user found in database
        Optional<UserAccount> foundOptional = usersRepository.findById(createdUserId);
        assertThat(foundOptional).isNotEmpty();

        //Check that the default playlist was created
        UserAccount theUser = foundOptional.get();
        assertThat(theUser.getPlaylists().size()).isNotEqualTo(0);
        Playlist defaultPlaylist = theUser.getPlaylists().get(0);
        System.out.println(defaultPlaylist.getId());
        System.out.println(defaultPlaylist.getName());
        assertThat(playlistsRepository.findById(defaultPlaylist.getId())).isNotEmpty();

        //delete
        usersRepository.deleteById(createdUserId);
        assertThat(usersRepository.findById(createdUserId)).isEmpty();

        //check that defaultPlaylist was cascaded and deleted
        assertThat(playlistsRepository.findById(defaultPlaylist.getId())).isEmpty();
    }
}