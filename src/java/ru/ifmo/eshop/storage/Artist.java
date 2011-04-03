package ru.ifmo.eshop.storage;

/**
 * User: alex
 * Date: 20.03.11
 * Time: 21:37
 */
public class Artist {
	private int id;
	private String title;
	private Genre genre;
	private String country;
	private int beginYear;
	private int endYear;

	public Artist(){}

	public Artist(int id,String title,Genre genre) {
		if (id<0) throw new IllegalArgumentException("id is lesser than zero");
		if (title==null || title.isEmpty()) throw new IllegalArgumentException("Title have to be set");
		if (title.length()>30) throw new IllegalArgumentException("Title is too long");
		if (genre==null) throw new IllegalArgumentException("Genre have to be set");
		this.id = id;
		this.title = title;
		this.genre=genre;
		this.country="";
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		if (title==null || title.isEmpty()) throw new IllegalArgumentException("Title have to be set");
		if (title.length()>30) throw new IllegalArgumentException("Title is too long");
		this.title=title;
	}

	public Genre getGenre() {
		return genre;
	}
	
	public void setGenre(Genre genre) {
		if (genre==null) throw new IllegalArgumentException("Genre have to be set");
		this.genre=genre;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if (country==null || country.length()>20) throw new IllegalArgumentException("Country is null or too long");
		this.country=country;
	}

	public int getBeginYear() {
		return beginYear;
	}

	public void setBeginYear(int beginYear) {
		if (beginYear<=1000 || beginYear>2011) throw new IllegalArgumentException("Wrong year");
		this.beginYear=beginYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		if (endYear<=1000) throw new IllegalArgumentException("Wrong year");
		this.endYear=endYear;
	}
}
