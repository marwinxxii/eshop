package ru.ifmo.eshop.storage;

public class Track extends Entity {

    public static final int TITLE_LENGTH=50;
    public static final int COMPOSER_LENGTH=50;
    public static final int DURATION_LENGTH=7;

    private int id;
    private Artist artist;
    private Item item;
    private String title;
    private int trackNumber;
    private String composer=null;
    private String duration=null;
    public boolean isVideo;

    public Track(int id, Artist artist, Item item, String title, int trackNumber) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is null or empty");
        }
        if (title.length() > TITLE_LENGTH) {
            throw new IllegalArgumentException("Title is too long");
        }
        if (trackNumber < 1) {
            throw new IllegalArgumentException("Wrong track number");
        }
        if (artist == null) {
            throw new IllegalArgumentException("Artist is null");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        this.id = id;
        this.artist = artist;
        this.item = item;
        this.title = title;
        this.trackNumber = trackNumber;
        this.isVideo = false;
    }

    public Artist getArtist() {
        return artist;
    }

    /*public void setArtist(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Artist is null");
        }
        this.artist = artist;
    }*/

    public Item getItem() {
        return item;
    }

    /*public void setItem(Item item) {
        if (item==null) {
            throw new IllegalArgumentException("item is null");
        }
        this.item = item;
    }*/

    public String getTitle() {
        return title;
    }

    /*public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("Title is too long");
        }
        this.title = title;
    }*/

    public int getTrackNumber() {
        return trackNumber;
    }

    /*public void setTrackNumber(int trackNumber) {
        if (trackNumber < 1) {
            throw new IllegalArgumentException("Wrong track num");
        }
        this.trackNumber = trackNumber;
    }*/

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        /*if (composer == null) {
            throw new IllegalArgumentException("Composer is null");
        }*/
        if (composer!=null && composer.length() > COMPOSER_LENGTH) {
            throw new IllegalArgumentException("Composer name is too long");
        }
        this.composer = composer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        /*if (duration == null) {
            throw new IllegalArgumentException("Duration is null");
        }*/
        if (duration!=null && duration.length() > DURATION_LENGTH) {
            throw new IllegalArgumentException("Duration too long");
        }
        this.duration = duration;
    }

    public int getId() {
        return id;
    }
}
