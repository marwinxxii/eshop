package ru.ifmo.eshop.storage;

import java.sql.Date;
import java.util.List;

public class Item extends Entity {

    public static final int MEDIATYPE_LENGTH=20;
    public static final int FORMAT_LENGTH=20;
    public static final int TITLE_LENGTH=50;
    public static final int COVER_LENGTH=50;

    private int id;
    private String mediaType;
    private String format;
    private Label label;
    private String title;
    private String cover;
    private Date releaseDate;
    private List<Artist> artists;
    private double price;

    public Item(int id, String mediaType, String format, String title, Date releaseDate) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (mediaType == null || format == null || title == null || releaseDate == null) {
            throw new IllegalArgumentException("Some of args are null");
        }
        if (mediaType.isEmpty() || format.isEmpty() || title.isEmpty()) {
            throw new IllegalArgumentException("Some string are empty");
        }
        if (mediaType.length() > MEDIATYPE_LENGTH) {
            throw new IllegalArgumentException("Media type is too long");
        }
        if (format.length() > FORMAT_LENGTH) {
            throw new IllegalArgumentException("Format is too long");
        }
        if (title.length() > TITLE_LENGTH) {
            throw new IllegalArgumentException("Title is too long");
        }
        if (releaseDate.getTime()>System.currentTimeMillis()) {
            throw new IllegalArgumentException("Wrong release date");
        }
        //TODO releaseDate may be in future?
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

    /*public void setMediaType(String mediaType) {
        if (mediaType == null || mediaType.length() > MEDIATYPE_LENGTH) {
            throw new IllegalArgumentException("Wrong media type");
        }
        this.mediaType = mediaType;
    }*/

    public String getFormat() {
        return format;
    }

    /*public void setFormat(String format) {
        if (format == null || format.length() > FORMAT_LENGTH) {
            throw new IllegalArgumentException();
        }
        this.format = format;
    }*/

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        /*if (label == null) {
            throw new IllegalArgumentException();
        }*/
        //TODO label==null?
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    /*public void setTitle(String title) {
        if (title == null || title.isEmpty() || title.length() > TITLE_LENGTH) {
            throw new IllegalArgumentException();
        }
        this.title = title;
    }*/

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        if (cover != null && cover.length() > COVER_LENGTH) {
            throw new IllegalArgumentException();
        }
        //TODO cover==null?
        this.cover = cover;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    /*public void setReleaseDate(Date releaseDate) {
        //TODO check relDate
        if (releaseDate == null) {
            throw new IllegalArgumentException();
        }
        this.releaseDate = releaseDate;
    }*/

    public int getId() {
        return id;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        //TODO empty list
        if (artists == null){// || artists.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.artists = artists;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price<=0.01) throw new IllegalArgumentException("incorrect price");
        this.price = price;
    }
}
