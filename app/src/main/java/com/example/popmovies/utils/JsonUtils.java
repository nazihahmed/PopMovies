package com.example.popmovies.utils;

import android.util.Log;

import com.example.popmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

	private static final String TAG_RESULTS = "results";
	private static final String TAG_ID = "id";
	private static final String TAG_POSTER = "poster_path";
	private static final String TAG_TITLE = "title";
	private static final String TAG_VOTE = "vote_average";
	private static final String TAG_OVERVIEW = "overview";
	private static final String TAG_RELEASE_DATE = "release_date";

	public static List<Movie> parseMoviesJson(String json) {
		List<Movie> movies = new ArrayList<>();
		try {
			JSONObject jsonData = new JSONObject(json);
			JSONArray moviesJson = jsonData.getJSONArray(TAG_RESULTS);
			movies = convertJsonArrayToList(moviesJson);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return movies;
	}

	private static List<Movie> convertJsonArrayToList(JSONArray jsonArray) {
		List<Movie> list = new ArrayList<>();
		if (jsonArray != null) {
			try {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject movieJson = jsonArray.getJSONObject(i);
					int id = movieJson.getInt(TAG_ID);
					String poster = NetworkUtils.buildPosterUrl(movieJson.getString(TAG_POSTER));
					String title = movieJson.getString(TAG_TITLE);
					String voteAverage = movieJson.getString(TAG_VOTE);
					String overview = movieJson.getString(TAG_OVERVIEW);
					String releaseDate = movieJson.getString(TAG_RELEASE_DATE);

					Movie movie = new Movie(id, poster, title, overview, releaseDate, voteAverage);
					list.add(movie);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}

