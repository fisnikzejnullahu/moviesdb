package com.moviesdb.movie.repository;

import com.moviesdb.actor.Actor;
import com.moviesdb.movie.model.MovieActor;
import com.moviesdb.movie.model.MovieActorId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieActorRepository extends JpaRepository<MovieActor, MovieActorId> {

    List<MovieActor> findByActorOrderByMovieRateDesc(Actor actor, Pageable pageable);
    List<MovieActor> findByActorOrderByMovieReleaseDateDesc(Actor actor);
}
