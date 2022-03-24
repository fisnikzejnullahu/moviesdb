package com.moviesdb.playlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/playlists")
public class PlaylistsController {

    private final PlaylistsService playlistsService;

    public PlaylistsController(PlaylistsService playlistsService) {
        this.playlistsService = playlistsService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("playlists", playlistsService.getPlaylistsOfLoggedInUser());
        return "playlists/index";
    }

    @PostMapping
    public String save(String playlistName) {
        playlistsService.save(playlistName);
        return "redirect:/playlists";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") int playlistId, Model model) {
        Optional<Playlist> playlistOptional = playlistsService.getPlaylist(playlistId);
        return playlistOptional.map(playlist -> {
            model.addAttribute("playlist", playlist);
            return "playlists/details";
        }).orElse("404");
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") int playlistId, String newPlaylistName) {
        Playlist playlist = playlistsService.update(playlistId, newPlaylistName);
        return (playlist != null)
                ? "redirect:/playlists/" + playlistId
                : "404";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int playlistId) {
        playlistsService.delete(playlistId);
        return "redirect:/playlists";
    }

    @PostMapping("/movies/add")
    public String addMovie(@RequestParam String playlistsId,
                           @RequestParam long movieId) {
        System.out.println("playlistId = " + playlistsId);
        System.out.println("movieId = " + movieId);

        playlistsService.addMovieInPlaylists(playlistsId, movieId);
        return "redirect:/movies/" + movieId;
    }

    @PostMapping("/{id}/movies/remove")
    public String removeMovie(@PathVariable("id") int playlistId, long movieId) {
        playlistsService.removeMovieFromPlaylist(playlistId, movieId);
        return "redirect:/playlists/" + playlistId;
    }
}
