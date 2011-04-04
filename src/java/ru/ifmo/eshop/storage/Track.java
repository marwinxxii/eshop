package ru.ifmo.eshop.storage;

public class Track {

    private int id;
    private Artist artist;
    private int itemId;
    private String title;
    private int trackNumber;
    private String composer;
    private String duration;
    public boolean isVideo;

    public Track() {
    }

    public Track(int id, Artist artist, int itemId, String title, int trackNumber, boolean isvideo) {
        if (id < 0 || itemId < 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is null or empty");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("Title is too long");
        }
        if (trackNumber < 1) {
            throw new IllegalArgumentException("Wrong track number");
        }
        if (artist == null) {
            throw new IllegalArgumentException("Artist is null");
        }
        this.id = id;
        this.artist = artist;
        this.itemId = itemId;
        this.title = title;
        this.trackNumber = trackNumber;
        this.isVideo = isvideo;
        this.composer = "";
        this.duration = "";
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Artist is null");
        }
        this.artist = artist;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        if (id < 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("Title is too long");
        }
        this.title = title;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        if (trackNumber < 1) {
            throw new IllegalArgumentException("Wrong track num");
        }
        this.trackNumber = trackNumber;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        if (composer == null) {
            throw new IllegalArgumentException("Composer is null");
        }
        if (composer.length() > 50) {
            throw new IllegalArgumentException("Composer name is too long");
        }
        this.composer = composer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        if (duration == null) {
            throw new IllegalArgumentException("Duration is null");
        }
        if (duration.length() > 7) {
            throw new IllegalArgumentException("Duration too long");
        }
        this.duration = duration;
    }

    public int getId() {
        return id;
    }
}
