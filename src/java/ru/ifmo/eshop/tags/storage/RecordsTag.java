package ru.ifmo.eshop.tags.storage;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Tag class for work with lists of all database entities.
 * @author alex
 * 02.06.2011
 */
//TODO doc!!
public abstract class RecordsTag extends TagSupport {

    protected int start=1;
    protected int end=5;
    /**
     * Message that will be displayed if no record in db was found.
     */
    protected String message;

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        if (end<=0) throw new IllegalArgumentException("end<=0");
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        if (start<=0) throw new IllegalArgumentException("start<=0");
        this.start = start;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
