package com.moviesdb.user;

import com.moviesdb.playlist.Playlist;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @SequenceGenerator(name="user_account_id_seq",
            sequenceName="user_account_id_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="user_account_id_seq")
    private int id;

    //TODO csrf tokeni po shtihet vet automatikisht kur pe perdor th:action kshtuqe hin fshij csrf vet ku i ki
    @Column(unique = true)
    private String username;
    private String password;
    private boolean enabled;

    @Column(name = "register_date")
    private LocalDateTime registerAt;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Playlist> playlists;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_in_roles",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRole> roles;

    public UserAccount() {
    }

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.registerAt = LocalDateTime.now();
        this.enabled = true;
        this.playlists = new ArrayList<>();
        var playlist = new Playlist("Watch Later");
        playlist.setUser(this);
        this.playlists.add(playlist);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(LocalDateTime registerAt) {
        this.registerAt = registerAt;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean active) {
        this.enabled = active;
    }
}
