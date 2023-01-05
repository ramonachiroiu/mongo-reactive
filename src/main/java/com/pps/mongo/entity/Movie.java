package com.pps.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @With
    private String id;
    private String title;
    private Genre genre;
    private String year;
    private String language;

    public Movie(Movie movieDetails, MovieInfo movieInfo) {
        id = movieInfo.getId();
        title = movieInfo.getTitle();
        genre = movieInfo.getGenre();
        language = movieDetails.getLanguage();
        year = movieDetails.getYear();
    }
}
