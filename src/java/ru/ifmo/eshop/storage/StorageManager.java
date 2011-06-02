package ru.ifmo.eshop.storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
    private static Genre emptyGenre=new Genre(1,"title","desc");
    private static Item emptyItem=new Item(1,"cd","LP","title",new Date(System.currentTimeMillis()));
    private static Distributor emptyDistributor=new Distributor(1,"type","title","country");

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

    public static Item registerItem(String mediaType,String format,String title,
            Date releaseDate) {
        return new Item(1,mediaType,format,title,releaseDate);
    }

    public Item getItem(int id) throws SQLException {
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
            result.close();
            pStatement.close();
            return null;
        }
    }

    public List<Item> getLastItems() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from items where rownum<=5 order by id desc");
        //execute because query should return more than one row
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Item> items = new ArrayList<Item>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            Item i=this.getItem(id);
            if (i!=null) items.add(i);
        }
        result.close();
        pStatement.close();
        return items;
    }

    public void addItem(Item item) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into items values(null,?,?,?,?,?,?)");
        pStatement.setString(1, item.getMediaType());
        pStatement.setString(2, item.getFormat());
        if (item.getLabel()!=null) {
            pStatement.setInt(3, item.getLabel().getId());
        } else {
            pStatement.setNull(3, Types.INTEGER);
        }
        pStatement.setString(4, item.getTitle());
        if (item.getCover()!=null) {
            pStatement.setString(5, item.getCover());
        } else {
            pStatement.setNull(3, Types.VARCHAR);
        }
        pStatement.setDate(6, item.getReleaseDate());
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateItem(Item item) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "update items set media_type=?,format=?,label_id=?,title=?,cover=?,release_date=? where id=?");
        pStatement.setString(1, item.getMediaType());
        pStatement.setString(2, item.getFormat());
        if (item.getLabel()!=null) {
            pStatement.setInt(3, item.getLabel().getId());
        } else {
            pStatement.setNull(3, Types.INTEGER);
        }
        pStatement.setString(4, item.getTitle());
        if (item.getCover()!=null) {
            pStatement.setString(5, item.getCover());
        } else {
            pStatement.setNull(3, Types.VARCHAR);
        }
        pStatement.setDate(6, item.getReleaseDate());
        pStatement.setInt(7, item.getId());
        pStatement.execute();
        pStatement.close();
    }

    public void deleteItem(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from items where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public Genre getGenre(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select * from genres where id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Genre genre=null;
        if (result.next()) {
            genre = new Genre(result.getInt(1), result.getString("title"),
                    result.getString("description"));
        }
        result.close();
        pStatement.close();
        return genre;
    }

    public void addGenre(String title,String description) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into genres values(null,?,?)");
        pStatement.setString(1, title);
        pStatement.setString(2, description);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateGenre(int id,String title,String description)
            throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "update genres set title=?,description=? where id=?");
        pStatement.setString(1, title);
        pStatement.setString(2, description);
        pStatement.setInt(3, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteGenre(int id,boolean deleteDeps) throws SQLException {
        PreparedStatement pStatement;
        if (deleteDeps) {
            pStatement=connection.prepareStatement("delete from artists where genre_id=?");
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            pStatement.close();
        }
        pStatement = connection.prepareStatement(
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
            Genre g=this.getGenre(id);
            if (g!=null) genres.add(g);
        }
        result.close();
        pStatement.close();
        return genres;
    }

    public static Label registerLabel(String title) {
        return new Label(1,title);
    }

    public Label getLabel(int id) throws SQLException {
         PreparedStatement pStatement = connection.prepareStatement(
                "select * from labels where id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Label label=null;
        if (result.next()) {
            label=new Label(result.getInt(1),result.getString("title"));
            if (result.getString("country")!=null) {
                label.setCountry(result.getString("country"));
            }
        }
        result.close();
        pStatement.close();
        return label;
    }

    public List<Label> getLastLabels() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from labels where rownum<=5 order by id desc");
        //execute because query should return more that one row
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Label> labels = new ArrayList<Label>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            Label l=this.getLabel(id);
            if (l!=null) labels.add(l);
        }
        result.close();
        pStatement.close();
        return labels;
    }

    public void addLabel(String title,String country) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into labels values(null,?,?)");
        pStatement.setString(1, title);
        pStatement.setString(2, country);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateLabel(int id,String title,String country) 
            throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "update labels set title=?,country=? where id=?");
        pStatement.setInt(3, id);
        pStatement.setString(1, title);
        pStatement.setString(2, country);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteLabel(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from labels where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    //public List<Label> getLastLabels()

    public static Artist registerArtist(String title,int genreId) {
        return new Artist(1,title,new Genre(genreId,"title","desc"));//TODO genre
    }

    public Artist getArtist(int id) throws SQLException {
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
       result.close();
       ps.close();
       return artist;
    }

    public List<Artist> getLastArtists() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from artists where rownum<=5 order by id desc");
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Artist> artists = new ArrayList<Artist>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            Artist a=this.getArtist(id);
            if (a!=null) artists.add(a);
        }
        result.close();
        pStatement.close();
        return artists;
    }

    public void addArtist(Artist artist) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into artists values(null,?,?,?,?,?)");
        pStatement.setString(1, artist.getTitle());
        pStatement.setInt(2, artist.getGenre().getId());
        if (artist.getCountry()==null) {
            pStatement.setNull(3, Types.VARCHAR);
        } else {
            pStatement.setString(3, artist.getCountry());
        }
        if (artist.getBeginYear()==null) {
            pStatement.setNull(4, Types.INTEGER);
        } else {
            pStatement.setInt(4, artist.getBeginYear());
        }
        if (artist.getEndYear()==null) {
            pStatement.setNull(5, Types.INTEGER);
        } else {
            pStatement.setInt(5, artist.getEndYear());
        }
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateArtist(Artist artist) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "update artists set title=?,genre_id=?,country=?,begin_year=?,end_year=? where id=?");
        pStatement.setString(1, artist.getTitle());
        pStatement.setInt(2, artist.getGenre().getId());
        if (artist.getCountry()==null) {
            pStatement.setNull(3, Types.VARCHAR);
        } else {
            pStatement.setString(3, artist.getCountry());
        }
        if (artist.getBeginYear()==null) {
            pStatement.setNull(4, Types.INTEGER);
        } else {
            pStatement.setInt(4, artist.getBeginYear());
        }
        if (artist.getEndYear()==null) {
            pStatement.setNull(5, Types.INTEGER);
        } else {
            pStatement.setInt(5, artist.getEndYear());
        }
        pStatement.setInt(6,artist.getId());
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteArtist(int id,boolean deleteDeps) throws SQLException {
        PreparedStatement pStatement;
        if (deleteDeps) {
            pStatement=connection.prepareStatement("delete from tracks where artist_id=?");
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            pStatement.close();
        }
        pStatement = connection.prepareStatement(
                "delete from artists where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public static Track registerTrack(int artistId,int itemId,String title,int trackNumber) {
        return new Track(1,new Artist(artistId,"title",emptyGenre),
                new Item(itemId,"cd","LP","title",new Date(System.currentTimeMillis())),
                title,trackNumber);
    }

    public Track getTrack(int id) throws SQLException {
        /*PreparedStatement pStatement=connection.prepareStatement(
                "select" +
                "tracks.id as track_id," +
                "tracks.title as track_title," +
                "tracks.track_number," +
                "tracks.composer," +
                "tracks.duration," +
                "tracks.isvideo," +
                "artists.id as artist_id," +
                "artists.title as artist_title," +
                "");*/
        PreparedStatement pStatement=connection.prepareStatement(
                "select * from tracks where id=?");//TODO rewrite query
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Track track=null;
        if (result.next()) {
            int artistId=result.getInt("artist_id");
            int item_id=result.getInt("item_id");
            track=new Track(id,getArtist(artistId),getItem(item_id),
                    result.getString("title"),result.getInt("track_number"));
            if (result.getString("composer")!=null) {
                track.setComposer(result.getString("composer"));
            }
            if (result.getString("duration")!=null) {
                track.setComposer(result.getString("duration"));
            }
            track.isVideo=result.getBoolean("isvideo");
        }
        result.close();
        pStatement.close();
        return track;
    }

    public List<Track> getLastTracks() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from tracks where rownum<=5 order by id desc");
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Track> tracks = new ArrayList<Track>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            Track t=this.getTrack(id);
            tracks.add(t);
        }
        result.close();
        pStatement.close();
        return tracks;
    }

    public List<Track> getTracksForItem(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select * from tracks where item_id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        ArrayList<Track> tracks=new ArrayList<Track>();
        Track track;
        if (result.next()) {
            int artistId=result.getInt("artist_id");
            int item_id=result.getInt("item_id");
            track=new Track(id,getArtist(artistId),getItem(item_id),
                    result.getString("title"),result.getInt("track_number"));
            if (result.getString("composer")!=null) {
                track.setComposer(result.getString("composer"));
            }
            if (result.getString("duration")!=null) {
                track.setComposer(result.getString("duration"));
            }
            track.isVideo=result.getBoolean("isvideo");
            tracks.add(track);
        }
        result.close();
        pStatement.close();
        return tracks;
    }

    public void addTrack(Track track) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into tracks values(null,?,?,?,?,?,?,?)");
        pStatement.setInt(1,track.getArtist().getId());
        pStatement.setInt(2, track.getItem().getId());
        pStatement.setString(3, track.getTitle());
        pStatement.setInt(4, track.getTrackNumber());
        if (track.getComposer()==null) {
            pStatement.setNull(5, Types.VARCHAR);
        } else {
            pStatement.setString(5, track.getComposer());
        }
        if (track.getDuration()==null) {
            pStatement.setNull(6, Types.VARCHAR);
        } else {
            pStatement.setString(6, track.getDuration());
        }
        pStatement.setBoolean(7, track.isVideo);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateTrack(Track track) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "update tracks set artist_id=?,item_id=?,title=?," +
                "track_number=?,composer=?,duration=?,isvideo=? where id=?");
        pStatement.setInt(1,track.getArtist().getId());
        pStatement.setInt(2, track.getItem().getId());
        pStatement.setString(3, track.getTitle());
        pStatement.setInt(4, track.getTrackNumber());
        if (track.getComposer()==null) {
            pStatement.setNull(5, Types.VARCHAR);
        } else {
            pStatement.setString(5, track.getComposer());
        }
        if (track.getDuration()==null) {
            pStatement.setNull(6, Types.VARCHAR);
        } else {
            pStatement.setString(6, track.getDuration());
        }
        pStatement.setBoolean(7, track.isVideo);
        pStatement.setInt(8, track.getId());
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteTrack(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from tracks where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public static Distributor registerDistributor(String type,String title,String country) {
        return new Distributor(1, type, title, country);
    }

    public Distributor getDistributor(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select * from distributors where id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Distributor distributor=null;
        if (result.next()) {
            distributor=new Distributor(id, result.getString("d_type"),
                    result.getString("title"), result.getString("country"));
        }
        pStatement.close();
        return distributor;
    }

    public List<Distributor> getLastDistributors() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from distributors where rownum<=5 order by id desc");
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Distributor> distributors = new ArrayList<Distributor>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            Distributor d=this.getDistributor(id);
            if (d!=null) distributors.add(d);
        }
        result.close();
        pStatement.close();
        return distributors;
    }

    public void addDistributor(Distributor distributor) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into distributors values (null,?,?,?)");
        pStatement.setString(1, distributor.getType());
        pStatement.setString(2,distributor.getTitle());
        pStatement.setString(3, distributor.getCountry());
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void updateDistributor(Distributor distributor) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "update distributors set d_type=?,title=?,country=? where id=?");
        pStatement.setString(1, distributor.getType());
        pStatement.setString(2, distributor.getTitle());
        pStatement.setString(3, distributor.getCountry());
        pStatement.setInt(4, distributor.getId());
        pStatement.executeUpdate();
        pStatement.close();
    }

    public void deleteDistributor(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from distributors where id=?");
        pStatement.setInt(1, id);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public Delivery getDelivery(int id) throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select * from deliveries where id=?");
        pStatement.setInt(1, id);
        ResultSet result=pStatement.executeQuery();
        Delivery delivery=null;
        if (result.next()) {
            /*PreparedStatement ps = connection.prepareStatement(
                "select * from distributors where id=?");
            ps.setInt(1, result.getInt("distributor_id"));
            ResultSet res=ps.executeQuery();
            res.next();
            Distributor d=new Distributor(res.getInt(1),
                    res.getString("d_type"),res.getString("title"),
                    res.getString("country"));*/
            Distributor d=this.getDistributor(result.getInt("distributor_id"));
            delivery=new Delivery(id,d,result.getDate("order_date"),
                    result.getDate("deliver_date"));
            ArrayList<DeliveryItem> items=new ArrayList<DeliveryItem>();
            PreparedStatement ps = connection.prepareStatement(
                "select * from deliver_lists where deliver_id=?");
            ps.setInt(1, id);
            ps.execute();
            ResultSet res=ps.getResultSet();
            while(res.next()) {
                Item i=this.getItem(res.getInt("item_id"));
                DeliveryItem di=new DeliveryItem(i, res.getInt("amount"),
                        res.getDouble("price"));
                items.add(di);
            }
            delivery.setItems(items);
        }
        pStatement.close();
        return delivery;
    }

    public List<Delivery> getLastDeliveries() throws SQLException {
        PreparedStatement pStatement = connection.prepareStatement(
                "select id from deliveries where rownum<=5 order by id desc");
        pStatement.execute();
        ResultSet result = pStatement.getResultSet();
        ArrayList<Delivery> deliveries = new ArrayList<Delivery>();
        //TODO optimization
        while (result.next()) {
            int id = result.getInt(1);
            Delivery d=this.getDelivery(id);
            if (d!=null) deliveries.add(d);
        }
        pStatement.close();
        return deliveries;
    }

    public void addDelivery(Delivery delivery) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement pStatement = connection.prepareStatement(
                "insert into deliveries values(null,?,?,?)");
        pStatement.setInt(1, delivery.getDistributor().getId());
        pStatement.setDate(2, delivery.getOrderDate());
        pStatement.setDate(3, delivery.getDeliverDate());
        pStatement.executeUpdate();
        pStatement.close();
        pStatement=connection.prepareStatement(
                "select max(id) from deliveries");
        ResultSet result=pStatement.executeQuery();
        if (!result.next()) {
            connection.rollback();
            return;
        }
        int id=result.getInt(1);
        pStatement.close();
        result.close();
        pStatement= connection.prepareStatement(
                "insert into deliver_lists values(?,?,?,?)");
        for (DeliveryItem deliveryItem:delivery.getItems()) {
            pStatement.setInt(1, id);
            pStatement.setInt(2, deliveryItem.getItem().getId());
            pStatement.setInt(3, deliveryItem.getAmount());
            pStatement.setDouble(4, deliveryItem.getPrice());
            pStatement.executeUpdate();
            //pStatement.close();
        }
        pStatement.close();
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void updateDelivery(Delivery delivery) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement pStatement = connection.prepareStatement(
                "update deliveries set distributor_id=?,order_date=?, deliver_date=? where id=?");
        pStatement.setInt(1, delivery.getDistributor().getId());
        pStatement.setDate(2, delivery.getOrderDate());
        pStatement.setDate(3, delivery.getDeliverDate());
        pStatement.setInt(4, delivery.getId());
        if (pStatement.executeUpdate()==0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return;
        }
        pStatement.close();
        pStatement = connection.prepareStatement(
                "update deliver_lists set amount=?, price=? where deliver_id=? and item_id=?");
        for (DeliveryItem deliveryItem:delivery.getItems()) {
            pStatement.setInt(1, deliveryItem.getAmount());
            pStatement.setDouble(2, deliveryItem.getPrice());
            pStatement.setInt(3, delivery.getId());
            pStatement.setInt(4, deliveryItem.getItem().getId());
            pStatement.executeUpdate();
            //pStatement.close();
        }
        pStatement.close();
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void deleteDelivery(int id) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement pStatement = connection.prepareStatement(
                "delete from deliveries where id=?");
        pStatement.setInt(1, id);
        if (pStatement.executeUpdate()==0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return;
        }
        pStatement.close();
        pStatement = connection.prepareStatement(
                "delete from deliver_lists where deliver_id=?");
        pStatement.setInt(1, id);
        if (pStatement.executeUpdate()==0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return;
        }
        pStatement.close();
        connection.commit();
    }

    public static Delivery registerDelivery(Integer distributorId,Date orderDate, Date deliverDate) {
        return new Delivery(1,
                new Distributor(distributorId, "type", "title", "country"),
                orderDate,deliverDate);
    }
}