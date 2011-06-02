package ru.ifmo.eshop.storage;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author alex
 * 16.05.2011
 */
public class Delivery extends Entity {

    private Integer id;
    private Distributor distributor;
    private Date orderDate;
    private Date deliverDate;
    private List<DeliveryItem> items;
    private int size;

    public Delivery(int id,Distributor distributor,Date orderDate,Date deliverDate) {
        if (id<=0) throw new IllegalArgumentException("id<=0");
        if (distributor==null || orderDate==null || deliverDate==null) {
            throw new IllegalArgumentException("Some of parameters are null");
        }
        this.id=id;
        this.distributor=distributor;
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

    public List<DeliveryItem> getItems() {
        return items;
    }

    public void setItems(List<DeliveryItem> items) {
        if (items==null) throw new IllegalArgumentException("Items are null");
        if (items.size()==0) throw new IllegalArgumentException("List of items is empty");
        this.items=items;
        size=0;
        for(DeliveryItem d:items) {
            size+=d.getAmount();
        }
    }

    public void addItem(DeliveryItem item) {
        items.add(item);
        size=0;
        for(DeliveryItem d:items) {
            size+=d.getAmount();
        }
    }

    public int getSize() {
        return size;
    }
}
