package com.example.movietmdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movietmdb.R;
import com.example.movietmdb.activity.MovieDetails;
import com.example.movietmdb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;

    public static final String IMAGE_URL_BASE_PATH="https://image.tmdb.org/t/p/w780/";
    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    //A view holder inner class where we get reference to the views in the layout using their ID
    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie,parent,false);

//        final MovieViewHolder viewHolder= new MovieViewHolder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position =viewHolder.getAdapterPosition();
//                Intent intent = new Intent(context, MovieDetails.class);
//                context.startActivity(intent);
//            }
//        });
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MovieViewHolder holder, int position) {


        String image_url = IMAGE_URL_BASE_PATH+movies.get(position).getPosterPath();
        Picasso.get()
                .load(image_url)
                .into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetails.class);
                v.getContext().startActivity(intent);



            }
        });

        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
//        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());


    }

    @Override
    public int getItemCount() {
        if (movies!=null)
            return this.movies.size();
        else
        return 0;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        CardView moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;
        public MovieViewHolder(View v) {
            super(v);
            moviesLayout =v.findViewById(R.id.movies_layout);
            movieImage =v.findViewById(R.id.movie_image);
            movieTitle =v.findViewById(R.id.title);
            data =v.findViewById(R.id.date);
            movieDescription =v.findViewById(R.id.description);
            rating =v.findViewById(R.id.rating);
        }
    }

}
