package ru.ifmo.eshop.storage;

/**
 * User: alex
 * Date: 20.03.11
 * Time: 21:34
 */
public class Label {
    public static final int TITLE_LENGTH=50;
    public static final int COUNTRY_LENGTH=30;

    private int id;
    private String title;
    private String country;

    public Label() {
    }

    public Label(int id, String title, String country) {
        if (id < 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (title == null || title.equals("") || country == null || country.equals("")) {
            throw new IllegalArgumentException("Title and country have to be set");
        }
        if (title.length() > TITLE_LENGTH) {
            throw new IllegalArgumentException("Title is too long");
        }
        if (country.length() > COUNTRY_LENGTH) {
            throw new IllegalArgumentException("Country name is too long");
        }
        this.id = id;
        this.title = title;
        this.country = country;
    }

    public Label(int id, String title) {
        if (id < 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("Title have to be set");
        }
        if (title.length() >TITLE_LENGTH) {
            throw new IllegalArgumentException("Title is too long");
        }
        this.id = id;
        this.title = title;
        this.country = "";
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null) {
            throw new IllegalArgumentException("Country is null");
        }
        if (country.length() > COUNTRY_LENGTH) {
            throw new IllegalArgumentException("COuntry name is too long");
        }
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Label)) {
            return false;
        }
        Label l = (Label) obj;
        if (this.id != l.id) {
            return false;
        }
        return this.title.equals(l.title) && this.country.equals(l.country);
    }
}
