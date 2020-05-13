package com.example.popmovies.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.popmovies.R;
import com.example.popmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
	private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
	Context context;
	private LayoutInflater inflater;
	/**
	 * This is our own custom constructor (it doesn't mirror a superclass constructor).
	 * The context is used to inflate the layout file, and the List is the data we want
	 * to populate into the lists
	 *
	 * @param applicationContext        The current context. Used to inflate the layout file.
	 * @param movies A List of Movie objects to display in a list
	 */
	public MovieAdapter(Context applicationContext, List<Movie> movies) {
		// Here, we initialize the ArrayAdapter's internal storage for the context and the list.
		// the second argument is used when the ArrayAdapter is populating a single TextView.
		// Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
		// going to use this second argument, so it can be any value. Here, we used 0.
		super(applicationContext, 0, movies);
		this.context = applicationContext;
		inflater = (LayoutInflater.from(applicationContext));
	}

	/**
	 * Provides a view for an AdapterView (ListView, GridView, etc.)
	 *
	 * @param position    The AdapterView position that is requesting a view
	 * @param convertView The recycled view to populate.
	 *                    (search online for "android view recycling" to learn more)
	 * @param parent      The parent ViewGroup that is used for inflation.
	 * @return The View for the position in the AdapterView.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Gets the AndroidFlavor object from the ArrayAdapter at the appropriate position
		Movie movie = getItem(position);

		// Adapters recycle views to AdapterViews.
		// If this is a new View object we're getting, then inflate the layout.
		// If not, this view already has the layout inflated from a previous call to getView,
		// and we modify the View widgets as usual.
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.movie_list_item, parent, false);
		}


		ImageView trailerView = (ImageView) convertView.findViewById(R.id.movieTrailer);
		Picasso.get().load(movie.getPoster().toString()).into(trailerView);

		return convertView;
	}

	public void updatedData(List<Movie> movies) {
		clear();
		if (movies != null){
			for (Movie movie : movies) {
				insert(movie, getCount());
			}
		}
		notifyDataSetChanged();
	}
}
