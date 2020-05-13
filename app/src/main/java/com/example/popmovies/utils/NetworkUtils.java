package com.example.popmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.popmovies.models.Movie.*;

/**
 * These utilities will be used to communicate with the movies servers.
 */
public final class NetworkUtils {

	private static final String TOP_RATED_MOVIES_URL =
		"https://api.themoviedb.org/3/movie/top_rated";

	private static final String POPULAR_MOVIES_URL =
		"https://api.themoviedb.org/3/movie/popular";

	private static final String NOW_PLAYING_MOVIES_URL =
		"https://api.themoviedb.org/3/movie/now_playing";

	private static final String MOVIE_POSTER_URL =
		"https://image.tmdb.org/t/p/w342";

	/** TODO: insert your api key here */
	private static final String API_KEY = "";

	final static String QUERY_API_KEY = "api_key";

	/**
	 * Builds the URL used to talk to the movies server using a location. This location is based
	 * on the query capabilities of the movies provider that we are using.
	 *
	 * @param category the category of the movies desired.
	 * @return The URL to use to query the movies server.
	 */
	public static URL buildUrl(Category category) {
		final String MOVIES_API_URL;
		switch (category) {
			case TOP_RATED:
				MOVIES_API_URL = TOP_RATED_MOVIES_URL;
				break;
			case MOST_POPULAR:
				MOVIES_API_URL = POPULAR_MOVIES_URL;
				break;
			default:
				MOVIES_API_URL = NOW_PLAYING_MOVIES_URL;
		}
		Uri moviesUri = Uri.parse(MOVIES_API_URL).buildUpon()
			.appendQueryParameter(QUERY_API_KEY, API_KEY)
			.build();
		URL moviesURL = null;
		try {
			moviesURL = new URL(moviesUri.toString());
			Log.i("URL", moviesURL.toString());
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		return moviesURL;
	}

	public static String buildPosterUrl(String posterPath) {
		Uri posterUri = Uri.parse(MOVIE_POSTER_URL).buildUpon()
			.appendQueryParameter(QUERY_API_KEY, API_KEY)
			.appendPath(posterPath.replace("/",""))
			.build();
		URL posterURL = null;
		try {
			posterURL = new URL(posterUri.toString());
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		return posterURL.toString();
	}

	/**
	 * This method returns the entire result from the HTTP response.
	 *
	 * @param url The URL to fetch the HTTP response from.
	 * @return The contents of the HTTP response.
	 * @throws IOException Related to network and stream reading
	 */
	public static String getResponseFromHttpUrl(URL url) throws IOException {
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			InputStream in = urlConnection.getInputStream();

			Scanner scanner = new Scanner(in);
			scanner.useDelimiter("\\A");

			boolean hasInput = scanner.hasNext();
			if (hasInput) {
				return scanner.next();
			} else {
				return null;
			}
		} finally {
			urlConnection.disconnect();
		}
	}
}
