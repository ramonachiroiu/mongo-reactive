package com.pps.mongo.repository;

import com.pps.mongo.entity.MovieInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<MovieInfo, String> {

    Mono<MovieInfo> save(MovieInfo name);
    Flux<MovieInfo> findAll();
    Mono<Void> deleteAll();

}
