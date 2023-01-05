package com.pps.mongo.service;

import com.pps.mongo.entity.Movie;
import com.pps.mongo.entity.MovieInfo;
import com.pps.mongo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieService {

    public static final String MOVIE_DETAILS_URI = "/movie-details";

    @Autowired
    private MovieRepository repository;

    WebClient client = WebClient.create("http://localhost:8082");

    public Flux<Movie> getAllMovies() {
        Flux<Movie> movieDetails = getMovieDetails();
        Flux<MovieInfo> movieInfos = getMovieInfo();
        return movieDetails.zipWith(movieInfos, Movie::new);
    }

    private Flux<Movie> getMovieDetails() {
        return client.get()
                .uri(MOVIE_DETAILS_URI)
                .retrieve()
                .bodyToFlux(Movie.class);
    }

    public Flux<MovieInfo> getMovieInfo() {
        return repository.findAll();
    }

    public Mono<MovieInfo> addMovie(Movie movie) {
        return repository.save(MovieInfo.builder().title(movie.getTitle()).genre(movie.getGenre()).build())
                .doOnNext(result ->
                        client.post()
                                .uri(MOVIE_DETAILS_URI)
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .body(Mono.just(movie.withId(result.getId())), Movie.class)
                                .retrieve()
                                .bodyToMono(Long.class)
                                .subscribe()
                );
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
