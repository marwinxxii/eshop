package ru.ifmo.eshop.storage;

import java.sql.Date;

/**
 *
 * @author alex
 * 16.05.2011
 */
public class Delivery extends Entity {

    private int id;
    private Distributor distributor;
    private Date orderDate;
    private Date deliverDate;

    public Delivery(int id,Distributor distributor,Date orderDate,Date deliverDate) {
        if (id<=0) throw new IllegalArgumentException("id<=0");
        if (distributor==null || orderDate==null || deliverDate==null) {
            throw new IllegalArgumentException("Some of parameters are null");
        }
        this.id=id;
        this.orderDate=orderDate;
        this.deliverDate=deliverDate;
    }

    public int getId() {
        return id;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }
}
