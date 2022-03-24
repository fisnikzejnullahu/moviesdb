package com.moviesdb.movie;

import com.moviesdb.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String genres,
            Model model) {

        System.out.println("page = " + page);
        System.out.println("genres = " + genres);

        model.addAttribute("model", moviesService.getMovies(page, genres));
        return "movies/index";
    }

    @GetMapping("{id}")
    public String details(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("model", moviesService.getMovieDetails(id));
            return "movies/details";
        } catch (NotFoundException ex) {
            return "404";
        }
    }

    @PostMapping(value = "{id}/rate")
    public String addMovieRate(@PathVariable("id") long movieId,
                               short rate) {

        moviesService.addMovieRate(movieId, rate);
        return "redirect:/movies/" + movieId;
    }
}
