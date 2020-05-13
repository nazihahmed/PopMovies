package com.example.popmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
	public static final String EXTRA_MOVIE = "extra_movie";

	private ImageView mPosterImage;
	private TextView mMovieTitle;
	private TextView mReleaseYear;
	private TextView mMovieOverview;
	private TextView mMovieRating;
	private Button mFavoriteButton;
	private Movie movieDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		mMovieTitle = (TextView) findViewById(R.id.movie_title);
		mPosterImage = (ImageView) findViewById(R.id.movie_poster);
		mReleaseYear = (TextView) findViewById(R.id.release_year);
		mMovieOverview = (TextView) findViewById(R.id.movie_overview);
		mMovieRating = (TextView) findViewById(R.id.movie_rating);
		mFavoriteButton = (Button) findViewById(R.id.favorite_button);

		Intent intent = getIntent();
		if (intent == null) {
			closeOnError();
		}

		movieDetails = intent.getParcelableExtra(EXTRA_MOVIE);
		populateUI(movieDetails);

		mFavoriteButton.setOnClickListener( new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), R.string.marked_as_favorite, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void closeOnError() {
		finish();
		Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
	}

	private void populateUI(Movie movie) {
		Picasso.get()
			.load(movie.getPoster())
			.into(mPosterImage);

		mMovieTitle.setText(movie.getTitle());
		mReleaseYear.setText(movie.getReleaseDate().split("-")[0]);
		mMovieOverview.setText(movie.getOverview());
		mMovieRating.setText(movie.getVoteAverage() + "/10");
	}
}
