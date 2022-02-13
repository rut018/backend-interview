package com.interview.moviesdbapi.services.impl;

import com.interview.moviesdbapi.entities.Movie;
import com.interview.moviesdbapi.json.MovieRest;
import com.interview.moviesdbapi.repositories.MovieRepository;
import com.interview.moviesdbapi.services.MovieService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger((MovieServiceImpl.class));
    private static final int MAX_NUM_REGISTROS = 5;

    @Autowired
    MovieRepository movieRepository;

    public static final ModelMapper modelMapper = new ModelMapper();

    @Override
    public MovieRest getMovieById(Long movieId) {

        return modelMapper.map(getMovieEntity(movieId), MovieRest.class);
    }

    @Override
    public List<MovieRest> getMovies() {
        final List<Movie> movieEntity = movieRepository.findAll(Sort.by("platform").and(Sort.by("title")));
        return movieEntity.stream()
                .map(service -> modelMapper.map(service,MovieRest.class))
                .collect(Collectors.toList());
    }

    @Override
    public String createMovie(MovieRest movieRest){
        final Movie movie = new Movie();
        movie.setTitle(movieRest.getTitle());
        movie.setGenre(movieRest.getGenre());
        movie.setYear(movieRest.getYear());
        movie.setPlatform(movieRest.getPlatform());
        try{
            movieRepository.save(movie);
        }catch(Exception e){
            LOGGER.error("INTERNAL_SERVER_ERROR",e);
        }
        return "MOVIE_CREATED";
    }

    @Override
    public String deleteMovie(Long movieId) {
        try{
            movieRepository.deleteById(movieId);
        }catch(Exception e){
            LOGGER.error("INTERNAL_SERVER_ERROR",e);
        }
        return "MOVIE_DELETED";
    }

    @Override
    public String updateMovie(Long movieId, MovieRest movieRest) {

        Movie movie = new Movie();

        movie = movieRepository.findById(movieId).orElseThrow();
        movie.setTitle(movieRest.getTitle());
        movie.setGenre(movieRest.getGenre());
        movie.setYear(movieRest.getYear());
        movie.setPlatform(movieRest.getPlatform());
        try{
            movieRepository.save(movie);
        }catch(Exception e){
            LOGGER.error("INTERNAL_SERVER_ERROR",e);
        }
        return "MOVIE_UPDATED";
    }

    @Override
    public List<MovieRest> getAllPageable(int page) {

        Page<Movie> pageResult =  movieRepository.findAll(PageRequest.of(page,MAX_NUM_REGISTROS, Sort.by("platform").and(Sort.by("title"))));
        return pageResult.stream()
                .map(service -> modelMapper.map(service,MovieRest.class))
                .collect(Collectors.toList());

    }

    private Movie getMovieEntity(Long movieId){

        return movieRepository.findById(movieId).orElseThrow() ;
    }


}
