package ru.ifmo.eshop.tags.storage;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import ru.ifmo.eshop.storage.Genre;

/**
 *
 * @author alex
 */
public class GenreObjectTag extends TagSupport {

    private String field;
    private int length=Genre.DESCRIPTION_LENGTH;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field=field;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length=length;
    }

    @Override
    public int doStartTag() throws JspException {
        GenreTag genreTag=(GenreTag) getAncestor("ru.ifmo.eshop.tags.storage.GenreTag");
        if (genreTag==null) {
            throw new JspException("No parent GenreTag found");
        }
        Genre g=genreTag.getGenre();
        String content;
        //TODO validation of attribute values?
        if (field.equals("id")) {
            content=String.valueOf(g.getId());
        } else if (field.equals("title")) {
            if (g.getTitle().length()>length) {
                content=g.getTitle().substring(0,length)+"...";
            } else {
                content=g.getTitle();
            }
        } else if (field.equals("description")) {
            if (g.getDescription().length()>length) {
                content=g.getDescription().substring(0,length)+"...";
            } else {
                content=g.getDescription();
            }
        } else {
            throw new JspException("GenreObjectTag:Wrong field name");
        }
        try {
            pageContext.getOut().print(content);
            return SKIP_BODY;
        } catch (IOException ex) {
            throw new JspException(ex);
        }
    }

    private TagSupport getAncestor(String className)
            throws JspException {
        Class klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new JspException(ex);
        }
        return (TagSupport) findAncestorWithClass(this, klass);
    }

    @Override
    public void release() {
        field = null;
    }
}
