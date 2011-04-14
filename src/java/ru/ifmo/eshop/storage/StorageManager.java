package ru.ifmo.eshop.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * User: alex
 * Date: 20.03.11
 * Time: 20:03
 */
public class StorageManager {

    private final String url;
    private final String login;
    private final String password;
    private Connection connection;

    public StorageManager(String host, int port, String login, String password)
            throws ClassNotFoundException, SQLException {
        if (host == null || port <= 0 || login == null || password == null) {
            throw new IllegalArgumentException();
        }
        url = "jdbc:oracle:thin:@" + host + ":" + port + ":xe";
        this.login = login;
        this.password = password;
        Locale.setDefault(Locale.ENGLISH);
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection=DriverManager.getConnection(url, this.login, this.password);
    }

    @Override
    protected void finalize() throws Throwable {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
        super.finalize();
    }

    public String getUrl() {
        return url;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Item getItem(int id) throws SQLException {
        //TODO check for id needed?
        if (id <= 0) {
            throw new IllegalArgumentException("Id is lesser than zero");
        }
        PreparedStatement pStatement = connection.prepareStatement("select "
                + "items.id as item_id,"
                + "items.media_type,"
                + "items.format,"
                + "items.title as item_title,"
                + "items.cover,"
                + "items.release_date,"
                + "labels.id as label_id,"
                + "labels.title as label_title,"
                + "labels.country "
                + "from items join labels on "
                + "items.label_id=labels.id where items.id=?");
        pStatement.setInt(1, id);
        ResultSet result = pStatement.executeQuery();
        if (result.next()) {
            Item i = new Item(result.getInt(1), result.getString("media_type"),
                    result.getString("format"),
                    result.getString("item_title"),
                    result.getDate("release_date"));
            if (result.getString("cover") != null) {
                i.setCover(result.getString("cover"));
            }
            if (result.getInt("label_id") != 0) {
                Label l = new Label(result.getInt("label_id"),
                        result.getString("label_title"));
                if (result.getString("country") != null) {
                    l.setCountry(result.getString("country"));
                }
                i.setLabel(l);
            }
            pStatement.close();
            PreparedStatement ps = connection.prepareStatement("select "
                    + "artists.id as artist_id,"
                    + "artists.title as artist_title,"
                    + "artists.country,"
                    + "artists.begin_year,"
                    + "artists.end_year,"
                    + "genres.id as genre_id,"
                    + "genres.title as genre_title,"
                    + "genres.description "
                    + "from artists join genres on artists.genre_id=genres.id "
                    + "where artists.id in ("+
                    "select artist_id from tracks where item_id=?)");
            ps.setInt(1, id);
            ArrayList<Artist> artists = new ArrayList<Artist>();
            //execute because query should return more than one row
            //ResultSet result2 = ps.executeQuery();
            if (!ps.execute()) {
                i.setArtists(artists);
                return i;
            }
            ResultSet result2 = ps.getResultSet();
            while (result2.next()) {
                Genre g = new Genre(result2.getInt("genre_id"),
                        result2.getString("genre_title"),
                        result2.getString("description"));
                Artist a = new Artist(result2.getInt("artist_id"),
                        result2.getString("artist_title"), g);
                if (result2.getString("country") != null) {
                    a.setCountry(result2.getString("country"));
                }
                if (result2.getInt("begin_year") != 0) {
                    a.setBeginYear(result2.getInt("begin_year"));
                }
                if (result2.getInt("end_year") != 0) {
                    a.setEndYear(result2.getInt("end_year"));
                }
                artists.add(a);
            }
            pStatement.close();
            i.setArtists(artists);
            return i;
        } else {
            pStatement.close();
            return null;
        }
    }

    public List<Item> getLastItems() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from items where rownum<4 order by id desc");
        //execute because query should return more that one row
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Item> items = new ArrayList<Item>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            items.add(this.getItem(id));
        }
        pStatement.close();
        return items;
    }

    public Genre getGenre(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id is lesser than zero");
        }
        PreparedStatement pStatement = connection.prepareStatement(
                "select * from genres where id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Genre genre=null;
        if (result.next()) {
            genre = new Genre(result.getInt(1), result.getString("title"),
                    result.getString("description"));
        }
        pStatement.close();
        return genre;
    }

    public void addGenre(String title,String description) throws SQLException {
        if (title==null || title.isEmpty()
                || title.length()>Genre.TITLE_LENGTH) {
            throw new IllegalArgumentException("Wrong title");
        }
        if (description==null || description.isEmpty()
                || description.length()>Genre.DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Wrong description");
        }
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into genres values(null,?,?)");
        pStatement.setString(1, title);
        pStatement.setString(2, description);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateGenre(int id,String title,String description)
            throws SQLException {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        if (title==null || title.isEmpty() || title.length()>Genre.TITLE_LENGTH)
            throw new IllegalArgumentException("title is null or empty");
        if (description==null || description.isEmpty()
                || description.length()>Genre.DESCRIPTION_LENGTH)
            throw new IllegalArgumentException("desc is null or empty");
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into genres values(?,?,?)");
        pStatement.setInt(1, id);
        pStatement.setString(2, title);
        pStatement.setString(3, description);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteGenre(int id) throws SQLException {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from genres where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public List<Genre> getLastGenres() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from genres where rownum<=5 order by id desc");
        //execute because query should return more that one row
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Genre> genres = new ArrayList<Genre>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            genres.add(this.getGenre(id));
        }
        pStatement.close();
        return genres;
    }

    public Label getLabel(int id) throws SQLException {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
         PreparedStatement pStatement = connection.prepareStatement(
                "select * from labels where id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Label label=null;
        if (result.next()) {
            label=new Label(result.getInt(1),result.getString("title"),
                    result.getString("country"));
        }
        pStatement.close();
        return label;
    }

    public void addLabel(String title,String country) throws SQLException {
        if (title==null || title.isEmpty()
                || title.length()>Label.TITLE_LENGTH) {
            throw new IllegalArgumentException("Wrong title");
        }
        if (country!=null && country.length()>Label.COUNTRY_LENGTH) {
            throw new IllegalArgumentException("country is too long");
        } else {
            country="";
        }
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into labels values(null,?,?)");
        pStatement.setString(1, title);
        pStatement.setString(2, country);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateLabel(int id,String title,String country) 
            throws SQLException {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        if (title==null || title.isEmpty() || title.length()>Genre.TITLE_LENGTH)
            throw new IllegalArgumentException("title is null or empty");
        if (country!=null && country.length()>Label.COUNTRY_LENGTH) {
            throw new IllegalArgumentException("country is too long");
        } else {
            country="";
        }
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into labels values(?,?,?)");
        pStatement.setInt(1, id);
        pStatement.setString(2, title);
        pStatement.setString(3, country);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteLabel(int id) throws SQLException {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from labels where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    //public List<Label> getLastLabels()

    public Artist getArtist(int id) throws SQLException {
       if (id<=0) throw new IllegalArgumentException("id is <=0");
       PreparedStatement ps = connection.prepareStatement("select "
                    + "artists.id as artist_id,"
                    + "artists.title as artist_title,"
                    + "artists.country,"
                    + "artists.begin_year,"
                    + "artists.end_year,"
                    + "genres.id as genre_id,"
                    + "genres.title as genre_title,"
                    + "genres.description "
                    + "from artists join genres on artists.genre_id=genres.id "
                    + "where artists.id=?");
       ps.setInt(1, id);
       ResultSet result=ps.executeQuery();
       Artist artist=null;
       if (result.next()) {
           Genre genre=new Genre(result.getInt("genre_id"),
                   result.getString("genre_title"),
                   result.getString("description"));
           artist=new Artist(result.getInt("artist_id"),
                   result.getString("artist_title"),genre);
           if (result.getString("country") != null) {
                artist.setCountry(result.getString("country"));
            }
            if (result.getInt("begin_year") != 0) {
                artist.setBeginYear(result.getInt("begin_year"));
            }
            if (result.getInt("end_year") != 0) {
                artist.setEndYear(result.getInt("end_year"));
            }
       }
       ps.close();
       return artist;
    }

    //public void addArtist(Artist artis)

    //TODO think about deleting related objects
    public void deleteArtist(int id) throws SQLException {
        if (id<=0) throw new IllegalArgumentException("id is <=0");
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from artists where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }
}
