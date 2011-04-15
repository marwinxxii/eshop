package ru.ifmo.eshop.tags.storage;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import ru.ifmo.eshop.Eshop;
import ru.ifmo.eshop.storage.Genre;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class GenreTag extends TagSupport {

    private String identity;
    private Genre genre;
    private List<Genre> genres;
    private boolean iterate=false;
    private int index=0;
    private String message;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String id) {
        this.identity=id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public int doStartTag() throws JspException {
        //TODO error message and exception catching
        try {
            StorageManager sm = Eshop.getStorageManager();
            boolean error=false;
            if (identity.equals("last")) {
                iterate=true;
                genres=sm.getLastGenres();
            } else {
                int i;
                try {
                    i=Integer.valueOf(identity);
                    if (i<=0) error=true;
                    genre=sm.getGenre(i);
                } catch(NumberFormatException e) {
                    error=true;
                }
            }
            sm.close();
            if ((!iterate && genre==null) || (iterate && genres.isEmpty()) || error) {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            }
        } catch (Exception e) {
            throw new JspException(e);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (iterate) {
            index++;
            System.out.println("endTag "+index);
            if (index<genres.size()) {
                System.out.println("endTag size:"+genres.size());
                return EVAL_BODY_AGAIN;
            }
        }
        return EVAL_PAGE;
    }

    public Genre getGenre() {
        System.out.println("getGenre "+index);
        if (iterate) {
            return genres.get(index);
        } else {
            return genre;
        }
    }

    @Override
    public void release() {
        genre=null;
        genres=null;
        identity=null;
    }
}
