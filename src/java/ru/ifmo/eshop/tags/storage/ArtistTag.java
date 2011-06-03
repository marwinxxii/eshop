package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Artist;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public final class ArtistTag extends RecordTag {

    @Override
    public int doStartTag() throws JspException {
        Artist a;
        if (keyId==-1) {
            ArtistsTag tag=(ArtistsTag)RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.ArtistsTag");
            if (tag==null) {
                throw new JspException("No parent ArtistsTag found");
            }
            a=tag.fetchArtist();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                a = sm.getArtist(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (a==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", a.getId());
        fieldMap.put("genre.id",a.getGenre().getId());
        fieldMap.put("genre.title", a.getGenre().getTitle());
        fieldMap.put("country",a.getCountry());
        fieldMap.put("title",a.getTitle());
        fieldMap.put("beginYear",a.getBeginYear());
        fieldMap.put("endYear", a.getEndYear());
        return EVAL_BODY_INCLUDE;
    }
}
