package ru.ifmo.eshop.storage;

/**
 *
 * @author alex
 * 16.05.2011
 */
public class Distributor extends Entity {
    
    public static final int TYPE_LENGTH=10;
    public static final int TITLE_LENGTH=50;
    public static final int COUNTRY_LENGTH=30;

    private int id;
    private String type;
    private String title;
    private String country;

    public Distributor(int id,String type,String title,String country) {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        if (type==null || title==null || country==null) {
            throw new IllegalArgumentException("Some of the parmeters is null");
        }
        if (title.isEmpty() || title.length()>TITLE_LENGTH) {
            throw new IllegalArgumentException("Wrong title");
        }
        if (type.isEmpty() || type.length()>TYPE_LENGTH) {
            throw new IllegalArgumentException("Wrong type");
        }
        if (country.isEmpty() || country.length()>COUNTRY_LENGTH) {
            throw new IllegalArgumentException("Wrong country");
        }
        this.id=id;
        this.type=type;
        this.title=title;
        this.country=country;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }
}
