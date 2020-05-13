package com.example.popmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
	private Integer id;
	private String poster;
	private String title;
	private String overview;
	private String releaseDate;
	private String voteAverage;

	public Movie(Integer id, String poster, String title, String overview, String releaseDate, String voteAverage) {
		this.id = id;
		this.poster = poster;
		this.title = title;
		this.overview = overview;
		this.releaseDate = releaseDate;
		this.voteAverage = voteAverage;
	}

	public Movie(Parcel in) {
		this.id = in.readInt();
		this.poster = in.readString();
		this.title = in.readString();
		this.overview = in.readString();
		this.releaseDate = in.readString();
		this.voteAverage = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(poster);
		dest.writeString(title);
		dest.writeString(overview);
		dest.writeString(releaseDate);
		dest.writeString(voteAverage);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}

		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(String voteAverage) {
		this.voteAverage = voteAverage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public enum Category {
		TOP_RATED,
		MOST_POPULAR,
		NOW_PLAYING,
	};

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}
}
