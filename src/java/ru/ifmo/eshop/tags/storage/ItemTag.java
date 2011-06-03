package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
//import java.util.HashSet;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import ru.ifmo.eshop.storage.Item;
import ru.ifmo.eshop.storage.StorageManager;

public final class ItemTag extends RecordTag {
    //private static HashSet<String> fields=new HashSet<String>();
    private static GregorianCalendar calendar=new GregorianCalendar();
    /*static {
        fields.add("id");
        fields.add("mediaType");
        fields.add("format");
        fields.add("label.title");
        fields.add("label.id");
        fields.add("title");
        fields.add("cover");
        fields.add("releaseDate");
    }*/
    //TODO field list needed?

    @Override
    public int doStartTag() throws JspException {
        Item i;
        if (keyId==-1) {
            ItemsTag tag=(ItemsTag)RecordTag.getAncestor(this,"ru.ifmo.eshop.tags.storage.ItemsTag");
            if (tag==null) {
                throw new JspException("No parent ItemsTag found");
            }
            i=tag.fetchItem();
        } else {
            StorageManager sm=(StorageManager)pageContext.getAttribute("storageManager",PageContext.REQUEST_SCOPE);
            try {
                i = sm.getItem(keyId);
            } catch (SQLException ex) {
                throw new JspException(ex);
            }
        }
        if (i==null) {
            try {
                pageContext.getOut().print(message);
                return SKIP_BODY;
            } catch (IOException ex) {
                throw new JspException(ex);
            }
        }
        fieldMap.put("id", i.getId());
        fieldMap.put("mediaType",i.getMediaType());
        fieldMap.put("format", i.getFormat());
        if (i.getLabel()!=null) {
            fieldMap.put("label.title", i.getLabel().getTitle());
            fieldMap.put("label.id",i.getLabel().getId());
        } else {
            fieldMap.put("label.title", null);
            fieldMap.put("label.id",null);
        }
        fieldMap.put("title",i.getTitle());
        fieldMap.put("cover",i.getCover());
        fieldMap.put("releaseDate", i.getReleaseDate());
        //TODO replace with children tags?
        fieldMap.put("artist.id", i.getArtists().get(0).getId());
        fieldMap.put("artist.title", i.getArtists().get(0).getTitle());
        calendar.setTime(i.getReleaseDate());
        fieldMap.put("year", calendar.get(GregorianCalendar.YEAR));
        fieldMap.put("price", i.getPrice());
        return EVAL_BODY_INCLUDE;
    }
}
