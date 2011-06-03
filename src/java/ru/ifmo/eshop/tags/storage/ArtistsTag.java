package ru.ifmo.eshop.tags.storage;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Artist;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 02.06.2011
 */
public final class ArtistsTag extends RecordsTag {

    private List<Artist> artists;
    private int index=0;
    private int trackId=-1;
    private int genreId=-1;
    private boolean rating;

    public Artist fetchArtist() {
        return artists.get(index++);
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        if (trackId<=0) throw new IllegalArgumentException("id is <=0");
        this.trackId = trackId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        if (genreId<=0) throw new IllegalArgumentException("id is <=0");
        this.genreId = genreId;
    }

    public boolean isRating() {
        return rating;
    }

    public void setRating(boolean rating) {
        this.rating = rating;
    }

    @Override
    public int doStartTag() throws JspException {
        StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
        try {
            if (genreId!=-1) {
                artists=sm.getArtistsForGenre(genreId);
            } else if (trackId!=-1) {
                artists=sm.getArtistsForTrack(trackId);
            } else if (rating) {
                artists=sm.getArtistsRating();
            } else {
                artists=sm.getArtists(start, end);
            }
        } catch (SQLException ex) {
            throw new JspException(ex);
        }
        index=0;
        if (artists.isEmpty()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        if (index<artists.size()) {
            return EVAL_BODY_AGAIN;
        } else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void release() {
        super.release();
        artists=null;
    }
}
