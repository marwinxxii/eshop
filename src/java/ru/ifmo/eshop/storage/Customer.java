package ru.ifmo.eshop.storage;

/**
 *
 * @author alex
 * 02.06.2011
 */
public class Customer extends Entity {

    public static final int EMAIL_LENGTH=30;
    public static final int INFO_LENGTH=100;
    public static final int PASSWORD_LENGTH=50;
    public static final int ADDRESS_LENGTH=100;
    public static final int PHONE_LENGTH=50;

    private Integer id;
    private String email;
    private String address;
    private String phone;
    private String password;

    public Customer(Integer id,String email,String password,String address,String phone) {
        if (id==null || id<=0)
            throw new IllegalArgumentException("id is null or <=0");
        if (email==null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is null or empty");
        }
        if (address==null || address.isEmpty()) {
            throw new IllegalArgumentException("Address is null or empty");
        }
        if (phone==null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone is null or empty");
        }
        this.id=id;
        this.email=email;//TODO check email with regexp
        this.address=address;
        this.phone=phone;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
