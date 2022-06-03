package com.example.movietmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.movietmdb.adapter.MoviesAdapter;
import com.example.movietmdb.model.Movie;
import com.example.movietmdb.model.MovieResponse;
import com.example.movietmdb.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String lang ="en-US";


    public static final String API_KEY = "55957fcf3ba81b137f8fc01ac5a31fb5";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.icon, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.doctor, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.ffbeast, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.morbious, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.thelostcity, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        connectAndGetApiData();
    }
    // This method create an instance of Retrofit
// set the base url
    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.listMovie(
                API_KEY,lang,"1"
        );
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + movies.size());
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
