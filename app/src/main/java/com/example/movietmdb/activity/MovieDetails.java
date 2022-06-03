package com.example.movietmdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movietmdb.MainActivity;
import com.example.movietmdb.MovieApiService;
import com.example.movietmdb.R;
import com.example.movietmdb.model.Movie;
import com.example.movietmdb.model.MovieResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String lang ="en-US";
    public static  int position;


    public static final String API_KEY = "55957fcf3ba81b137f8fc01ac5a31fb5";

    private int id;
    private List<Movie> movies;
    public static final String IMAGE_URL_BASE_PATH="https://image.tmdb.org/t/p/w780/";


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
//        Call<Movie> call = movieApiService.getMovie(
//                "284052",API_KEY
//        );
//        call.enqueue(new Callback<Movie>() {
//            @Override
//            public void onResponse(Call<Movie> call, Response<Movie> response) {
//                List<Movie> movies = response.body();
//                movieTitle.setText(movies.get(0).getTitle());
//            }
//
//            @Override
//            public void onFailure(Call<Movie> call, Throwable t) {
//
//            }
//        });
        Call<MovieResponse> call = movieApiService.listMovie(
                API_KEY,lang,"1"
        );
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                Bundle bundle = getIntent().getExtras();
                int mPosition = 0;
                if (bundle != null) {
                    mPosition = bundle.getInt("movie");
                    Log.d(TAG, "Bundle mPosition: " + mPosition);
                }

//                int moviePosition = (int) (mPosition + 1);
//                Log.d(TAG, "Bundle moviePosition: " + moviePosition);


                Picasso.get()
                        .load(IMAGE_URL_BASE_PATH+movies.get(mPosition).getBackdropPath())
                        .into(movieImage);
                movieTitle.setText(movies.get(mPosition).getTitle());
                data.setText(movies.get(mPosition).getReleaseDate());
                movieDescription.setText(movies.get(mPosition).getOverview().toString());
                rating.setText(movies.get(mPosition).getVoteAverage().toString());


            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }
}