package com.moviesdb.actor;

import com.moviesdb.genre.Genre;
import com.moviesdb.movie.model.MovieActor;
import com.moviesdb.genre.GenresRepository;
import com.moviesdb.movie.repository.MovieActorRepository;
import com.moviesdb.movie.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ActorsService {

    private final ActorsRepository actorsRepository;
    private final MoviesRepository moviesRepository;
    private final MovieActorRepository movieActorRepository;
    private final GenresRepository genresRepository;

    @Value("${default.actors.response.page-size}")
    private int actorsResponsePageSize;

    @Value("${default.popular-movies.response.page-size}")
    private int popularMoviesResponsePageSize;

    public ActorsService(ActorsRepository actorsRepository, MoviesRepository moviesRepository, MovieActorRepository movieActorRepository, GenresRepository genresRepository) {
        this.actorsRepository = actorsRepository;
        this.moviesRepository = moviesRepository;
        this.movieActorRepository = movieActorRepository;
        this.genresRepository = genresRepository;
    }

    public ActorsViewModel getActors(int page, String sortBy, String sortOrder) {
        Sort by = Sort.by(sortBy);
        if (sortOrder.equals("desc")) {
            by = by.descending();
        }
        Pageable pageable = PageRequest.of(page - 1, actorsResponsePageSize, by);
        Page<Actor> actorPage = actorsRepository.findAll(pageable);

        System.out.println("actorPage = " + actorPage.getTotalPages());

        return new ActorsViewModel(actorPage.getContent(), actorPage.getTotalPages(), page);
    }

    public ActorDetailsModel getActor(long id) {
        return actorsRepository.findById(id).map(actor -> {
                List<MovieActor> mostPopularActorMovies = movieActorRepository.findByActorOrderByMovieRateDesc(actor, Pageable.ofSize(popularMoviesResponsePageSize));
                mostPopularActorMovies.forEach(movieActor -> {
                    System.out.println(movieActor.getMovie().getTitle());
                    System.out.println(movieActor.getMovie().getRate());
                });

                List<MovieActor> allMovies = movieActorRepository.findByActorOrderByMovieReleaseDateDesc(actor);
                actor.setMovies(allMovies);
                Set<Genre> genresPlayed = genresRepository.getGenresByActorId(id);
                return new ActorDetailsModel(actor, mostPopularActorMovies, genresPlayed);
            })
            .orElse(null);
    }
}
