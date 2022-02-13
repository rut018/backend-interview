package com.interview.moviesdbapi.services;

import com.interview.moviesdbapi.json.MovieRest;


import java.util.List;

public interface MovieService {

    MovieRest getMovieById(Long MovieId);

     List<MovieRest> getMovies();

    String createMovie(MovieRest movieRest);

    String deleteMovie(Long movieId);

    String updateMovie(Long movieId, MovieRest movieRest);

    List<MovieRest> getAllPageable(int page);
}
