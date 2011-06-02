package ru.ifmo.eshop.storage;

/**
 *
 * @author alex
 * 02.06.2011
 */
public class Customer extends Entity {
    private Integer id;
    private String email;
    private String info;

    public Customer(Integer id,String email) {
        if (id==null || id<=0)
            throw new IllegalArgumentException("id is null or <=0");
        if (email==null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        this.id=id;
        this.email=email;//TODO check email with regexp
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        if (info==null) throw new IllegalArgumentException("Info is null");
        this.info=info;
    }
}
