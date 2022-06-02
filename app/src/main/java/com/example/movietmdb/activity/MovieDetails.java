package com.example.movietmdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movietmdb.MainActivity;
import com.example.movietmdb.MovieApiService;
import com.example.movietmdb.R;
import com.example.movietmdb.model.Movie;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org";

    private int id;
    private List<Movie> movies;
    public static final String IMAGE_URL_BASE_PATH="https://image.tmdb.org/t/p/w780/";


    public static final String API_KEY = "55957fcf3ba81b137f8fc01ac5a31fb5";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;
    private Movie movie;
    CardView moviesLayout;
    TextView movieTitle;
    TextView data;
    TextView movieDescription;
    TextView rating;
    ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        moviesLayout =findViewById(R.id.movies_layout);
        movieImage =findViewById(R.id.movie_image);
        movieTitle =findViewById(R.id.title);
        data =findViewById(R.id.date);
        movieDescription =findViewById(R.id.description);
        rating =findViewById(R.id.rating);

        getMovie();

    }

    private void getMovie() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<Movie> call = movieApiService.getMovie(
                id,API_KEY
        );
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                List<Movie> data= Collections.singletonList(response.body());
//                for (int i=0;i<data.size();i++);
                movieTitle.setText(movies.get(data.size()).getTitle());

//                String image_url = IMAGE_URL_BASE_PATH+movie.getPosterPath();
//                Picasso.get()
//                        .load(image_url).into(movieImage);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }
}