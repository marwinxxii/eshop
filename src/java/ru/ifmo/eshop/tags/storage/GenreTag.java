package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Genre;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class GenreTag extends RecordTag {

    @Override
    public int doStartTag() throws JspException {
        Genre g;
        if (keyId==-1) {
            GenresTag tag=(GenresTag)RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.GenresTag");
            if (tag==null) {
                throw new JspException("No parent ItemsTag found");
            }
            g=tag.fetchGenre();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                g = sm.getGenre(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (g==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", g.getId());
        fieldMap.put("title",g.getTitle());
        fieldMap.put("description", g.getDescription());
        return EVAL_BODY_INCLUDE;
    }
}
