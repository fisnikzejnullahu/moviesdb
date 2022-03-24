package com.moviesdb.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long> {

    @Query(value = "select g.name, g.id " +
            "from Genre g " +
            "inner join movie_genre mr on mr.genre_id = g.id " +
            "inner join movie m on m.id = mr.movie_id " +
            "inner join movie_actor ma on ma.movie_id = m.id " +
            "inner join actor a on a.id = ma.actor_id " +
            "where a.id = (:actorId)", nativeQuery = true)
    Set<Genre> getGenresByActorId(long actorId);
}
