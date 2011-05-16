package ru.ifmo.eshop.tags.storage;

import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import ru.ifmo.eshop.Eshop;
import ru.ifmo.eshop.storage.Entity;
import ru.ifmo.eshop.storage.StorageManager;

/**
 *
 * @author alex
 */
public class RecordTag extends TagSupport {

    private String identity;
    private String entity;
    private String message;
    private Entity record;
    private List<? extends Entity> records;
    private boolean iterate=false;
    private int index=0;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String id) {
        this.identity=id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity=entity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public int doStartTag() throws JspException {
        index=0;
        //TODO error message and exception catching
        try {
            StorageManager sm = Eshop.getStorageManager();
            boolean error=false;
            if (identity.equals("last")) {
                iterate=true;
                if (entity.equals("Genre")) {
                    records=sm.getLastGenres();
                } else if (entity.equals("Label")) {
                    records=sm.getLastLabels();
                } else if (entity.equals("Artist")) {
                    records=sm.getLastArtists();
                } else if (entity.equals("Item")) {
                    records=sm.getLastItems();
                } else if (entity.equals("Track")) {
                    records=sm.getLastTracks();
                } else if (entity.equals("Distributor")) {
                    records=sm.getLastDistributors();
                } else {
                    throw new JspException("Wrong entity type");
                }
            } else {
                int i=0;
                try {
                    i=Integer.valueOf(identity);
                    if (i<=0) error=true;
                } catch(NumberFormatException e) {
                    error=true;
                }
                if (!error) {
                    if (entity.equals("Genre")) {
                        record=sm.getGenre(i);
                    } else if (entity.equals("Label")) {
                        record=sm.getLabel(i);
                    } else if (entity.equals("Artist")) {
                        record=sm.getArtist(i);
                    } else if (entity.equals("Item")) {
                        record=sm.getItem(i);
                    } else if (entity.equals("Track")) {
                        record=sm.getTrack(i);
                    } else if (entity.equals("Distributor")) {
                        record=sm.getDistributor(i);
                    } else {
                        throw new JspException("Wrong entity type");
                    }
                }
            }
            sm.close();
            if ((!iterate && record==null) || (iterate && records.isEmpty()) || error) {
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
            if (index<records.size()) {
                return EVAL_BODY_AGAIN;
            }
        }
        return EVAL_PAGE;
    }

    public Entity getRecord() {
        if (iterate) {
            return records.get(index);
        } else {
            return record;
        }
    }

    @Override
    public void release() {
        record=null;
        records=null;
        identity=null;
        message=null;
    }

    public static TagSupport getAncestor(Tag tag,String className)
            throws JspException {
        Class klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new JspException(ex);
        }
        return (TagSupport) findAncestorWithClass(tag, klass);
    }
}
