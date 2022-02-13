package com.interview.moviesdbapi.controllers;

import com.interview.moviesdbapi.entities.Movie;
import com.interview.moviesdbapi.json.MovieRest;
import com.interview.moviesdbapi.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/movie-db" + "/v1")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping(value = "/movie/{movieId}")
    public ResponseEntity<MovieRest> getMovieById(@PathVariable  Long movieId){
        return new ResponseEntity<>(movieService.getMovieById(movieId),HttpStatus.OK);

    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieRest>> getMovies(){
        return new ResponseEntity<>(movieService.getMovies(),HttpStatus.OK);
    }

    @GetMapping("/pageable/{page}")
    public ResponseEntity<List<MovieRest>> getMoviesPageable(@PathVariable int page){
        return new ResponseEntity<>(movieService.getAllPageable(page),HttpStatus.OK);
    }

    @PostMapping("/movie/create")
    public ResponseEntity<String> createMovie(@RequestBody MovieRest movieRest){
        return new ResponseEntity<>(movieService.createMovie(movieRest), HttpStatus.CREATED);
    }

    @PutMapping("/movie/update/{movieId}")
    public ResponseEntity<String> updateMovie(@PathVariable Long movieId, @RequestBody MovieRest movieRest){
        return new ResponseEntity<>(movieService.updateMovie(movieId, movieRest), HttpStatus.OK);
    }

    @DeleteMapping("/movie/delete/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId){
        return new ResponseEntity<>(movieService.deleteMovie(movieId), HttpStatus.OK);
    }

}










