package com.example.popmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.popmovies.adapters.MovieAdapter;
import com.example.popmovies.models.Movie;
import com.example.popmovies.utils.JsonUtils;
import com.example.popmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.popmovies.models.Movie.*;

public class MainActivity extends AppCompatActivity {
	private MovieAdapter movieAdapter;

	private GridView moviesGrid;
	private List<Movie> movies = new ArrayList<>();
	private ProgressBar mLoadingIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		moviesGrid = (GridView) findViewById(R.id.moviesGridView); // init GridView
		mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
		// Create an object of CustomAdapter and set Adapter to GirdView
		movieAdapter = new MovieAdapter(getApplicationContext(), movies);
		moviesGrid.setAdapter(movieAdapter);
		moviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				launchDetailActivity(position);
			}
		});

		new FetchMoviesTask().execute(Category.MOST_POPULAR);
	}

	private void launchDetailActivity(int position) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
		startActivity(intent);
	}

	private void showMoviesDataView() {
		/* First, make sure the loading is invisible */
		mLoadingIndicator.setVisibility(View.INVISIBLE);
		/* Then, make sure the movies data is visible */
		moviesGrid.setVisibility(View.VISIBLE);
	}

	private void hideMoviesDataView() {
		/* First, make sure the loading is invisible */
		mLoadingIndicator.setVisibility(View.VISIBLE);
		/* Then, make sure the movies data is visible */
		moviesGrid.setVisibility(View.INVISIBLE);
	}

	private class FetchMoviesTask extends AsyncTask<Category, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			hideMoviesDataView();
		}

		@Override
		protected String doInBackground(Category... params) {
			URL weatherURL = NetworkUtils.buildUrl(params[0]);
			String response = null;
			try {
				response = NetworkUtils.getResponseFromHttpUrl(weatherURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String s) {
			if(s != null && !s.equals("")) {
				List<Movie> movies = JsonUtils.parseMoviesJson(s);
				movieAdapter.updatedData(movies);
				showMoviesDataView();
			} else {
				super.onPostExecute(s);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
		MenuInflater inflater = getMenuInflater();
		/* Use the inflater's inflate method to inflate our menu layout to this menu */
		inflater.inflate(R.menu.options, menu);
		/* Return true so that the menu is displayed in the Toolbar */
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.option_most_popular) {
			new FetchMoviesTask().execute(Category.MOST_POPULAR);
			return true;
		}
		if (id == R.id.option_top_rated) {
			new FetchMoviesTask().execute(Category.TOP_RATED);
			return true;
		}
		if (id == R.id.option_now_playing) {
			new FetchMoviesTask().execute(Category.NOW_PLAYING);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
