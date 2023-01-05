package com.pps.mongo.controller;

import com.pps.mongo.entity.Movie;
import com.pps.mongo.entity.MovieInfo;
import com.pps.mongo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MovieController {

    @Autowired
    private MovieService service;


    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, value = "/movie-info")
    public ResponseEntity<Flux<MovieInfo>>  getMovie() {
        return new ResponseEntity<>(service.getMovieInfo(), HttpStatus.OK);
    }


    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, value = "/movies")
    public ResponseEntity<Flux<Movie>> getMovies() {
        return new ResponseEntity<>(service.getAllMovies(), HttpStatus.OK);
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, value = "add")
    public ResponseEntity<Mono<MovieInfo>> addMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(service.addMovie(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/all")
    public void deleteAll() {
        service.deleteAll();
    }

}
