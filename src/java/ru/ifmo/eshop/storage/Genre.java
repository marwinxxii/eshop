package ru.ifmo.eshop.storage;

/**
 * User: alex
 * Date: 03.03.11
 * Time: 23:01
 */
public class Genre {
    public static final int TITLE_LENGTH=40;
    public static final int DESCRIPTION_LENGTH=300;

    private int id;
    private String title;
    private String description;

    public Genre() {}

    public Genre(int id, String title, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("id is lesser than zero");
        }
        if (title == null || title.isEmpty() || description == null
                || description.isEmpty()) {
            throw new IllegalArgumentException("Title and desc have to be set");
        }
        if (title.length() > TITLE_LENGTH) {
            throw new IllegalArgumentException("Title is too long");
        }
        if (description.length() > DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Description is too long");
        }
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    /*public void setId(int id) {
        if (id<=0) throw new IllegalArgumentException("id is lesser than zero");
        this.id = id;
    }*/

    public String getTitle() {
        return title;
    }

    /*public void setTitle(String title) {
    this.title = title;
    }*/
    
    public String getDescription() {
        return description;
    }

    /*public void setDescription(String description) {
    this.description = description;
    }*/

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Genre)) {
            return false;
        }
        Genre g = (Genre) obj;
        if (this.id != g.id) {
            return false;
        }
        //if mandatory field is null, then this is illegal state
        return title.equals(g.title) && description.equals(g.description);
    }
}
