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

    public StorageManager(String host, int port, String login, String password) throws ClassNotFoundException, SQLException {
        if (host == null || port < 0 || login == null || password == null) {
            throw new IllegalArgumentException();
        }
        url = "jdbc:oracle:thin:@" + host + ":" + port + ":xe";
        this.login = login;
        this.password = password;
        Locale.setDefault(Locale.ENGLISH);
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection(url, this.login, this.password);
    }

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

    /*public Artist getArtist(int id) throws SQLException {
    if (id<0) throw new IllegalArgumentException("Id is lesser than zero");
    PreparedStatement pStatement=connection.prepareStatement("select * from artists where id=?");
    pStatement.setInt(1, id);
    ResultSet result=pStatement.executeQuery();
    if (result.next()) {
    Artist a=new Artist(result.getInt(1),result.getString("title"),result.getInt("genre_id"));
    if (result.getInt("begin_year")!=0) {
    a.setBeginYear(result.getInt("begin_year"));
    }
    if (result.getInt("end_year")!=0) {
    a.setBeginYear(result.getInt("end_year"));
    }
    if (result.getString("country")!=null) {
    a.setCountry(result.getString("country"));
    }
    pStatement.close();
    return a;
    } else {
    pStatement.close();
    return null;
    }
    }*/
    public Item getItem(int id) throws SQLException {
        if (id < 0) {
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
                + "from items join labels on items.label_id=labels.id where items.id=?");
        pStatement.setInt(1, id);
        ResultSet result = pStatement.executeQuery();
        if (result.next()) {
            Item i = new Item(result.getInt(1), result.getString("media_type"), result.getString("format"),
                    result.getString("item_title"), result.getDate("release_date"));
            if (result.getString("cover") != null) {
                i.setCover(result.getString("cover"));
            }
            if (result.getInt("label_id") != 0) {
                Label l = new Label(result.getInt("label_id"), result.getString("label_title"));
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
                    + "where artists.id in (select artist_id from tracks where item_id=?)");
            ps.setInt(1, id);
            ResultSet result2 = ps.executeQuery();
            ArrayList<Artist> artists = new ArrayList<Artist>();
            while (result2.next()) {
                Genre g = new Genre(result2.getInt("genre_id"), result2.getString("genre_title"), result2.getString("description"));
                Artist a = new Artist(result2.getInt("artist_id"), result2.getString("artist_title"), g);
                if (result2.getString("artist_country") != null) {
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
            i.setArtists(artists);
            return i;
        } else {
            pStatement.close();
            return null;
        }
    }

    public List<Item> getLastItems() throws SQLException {
        PreparedStatement pStatement=connection.prepareStatement(
                "select id from items where rownum<4 order by id desc");
        ResultSet result=pStatement.executeQuery();
        ArrayList<Item> items=new ArrayList<Item>();
        //TODO optimization
        while (result.next()) {
            int id=result.getInt(1);
            items.add(this.getItem(id));
        }
        pStatement.close();
        return items;
    }
}
