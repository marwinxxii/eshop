package ru.ifmo.eshop.storage;

import java.sql.Date;
import java.util.List;

public class Item {

    private int id;
    private String mediaType;
    private String format;
    private Label label;
    private String title;
    private String cover;
    private Date releaseDate;
    private List<Artist> artists;

    public Item(int id, String mediaType, String format, String title, Date releaseDate) {
        if (id < 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (mediaType == null || format == null || title == null || releaseDate == null) {
            throw new IllegalArgumentException("Some of args are null");
        }
        if (mediaType.isEmpty() || format.isEmpty() || title.isEmpty()) {
            throw new IllegalArgumentException("Some string are empty");
        }
        if (mediaType.length() > 20) {
            throw new IllegalArgumentException("Media type is too long");
        }
        if (format.length() > 20) {
            throw new IllegalArgumentException("Format is too long");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("Title is too long");
        }
        //TODO check releaseDate
        this.id = id;
        this.mediaType = mediaType;
        this.format = format;
        this.title = title;
        this.releaseDate = releaseDate;
        this.cover = "";
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        if (mediaType == null || mediaType.length() > 20) {
            throw new IllegalArgumentException();
        }
        this.mediaType = mediaType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if (format == null || format.length() > 20) {
            throw new IllegalArgumentException();
        }
        this.format = format;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        if (label == null) {
            throw new IllegalArgumentException();
        }
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.length() > 50) {
            throw new IllegalArgumentException();
        }
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        if (cover == null || cover.length() > 50) {
            throw new IllegalArgumentException();
        }
        this.cover = cover;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        //TODO check relDate
        if (releaseDate == null) {
            throw new IllegalArgumentException();
        }
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        if (artists == null || artists.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.artists = artists;
    }
}
