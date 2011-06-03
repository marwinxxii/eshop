package ru.ifmo.eshop.tags.storage;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Genre;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 * 03.06.2011
 */
public class GenresTag extends RecordsTag {
    private List<Genre> genres;
    private int index=0;
    private boolean rating;

    public Genre fetchGenre() {
        return genres.get(index++);
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
            if (rating) {
                genres=sm.getGenresRating();
            } else {
                genres=sm.getGenres(start,end);
            }
        } catch (SQLException ex) {
            throw new JspException(ex);
        }
        index=0;
        if (genres.isEmpty()) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }

    @Override
    public int doAfterBody() throws JspException {
        if (index<genres.size()) {
            return EVAL_BODY_AGAIN;
        } else {
            return EVAL_PAGE;
        }
    }

    @Override
    public void release() {
        super.release();
        genres=null;
    }
}
