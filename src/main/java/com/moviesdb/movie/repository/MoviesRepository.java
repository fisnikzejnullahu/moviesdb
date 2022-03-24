package com.moviesdb.movie.repository;

import com.moviesdb.movie.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select m, avg(mr.rate) as rate " +
            "from Movie m " +
            "left join MovieRating mr on mr.id.movieId = m.id " +
            "group by m.id " +
            "order by rate desc nulls last ")
    List<Movie> findByPopularityWithPagination(Pageable pageable);

    Page<Movie> findDistinctByGenresNameInOrderByRateDesc(Collection<String> names, Pageable pageable);
}
