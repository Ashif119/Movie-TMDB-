package com.example.movietmdb;

import com.example.movietmdb.model.Movie;
import com.example.movietmdb.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    //List for Movies
    //list of movie https://api.themoviedb.org/3/movie/now_playing?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&page=1
    @GET("/3/movie/now_playing")
    Call<MovieResponse> listMovie(
            @Query("api_key") String key,
            @Query("language") String lang,
            @Query("page") String page
    );
    @GET("/3/movie/{id}?")
    Call<Movie> getMovie(
            @Path("id") int id,
            @Query("api_key") String key
    );
}
