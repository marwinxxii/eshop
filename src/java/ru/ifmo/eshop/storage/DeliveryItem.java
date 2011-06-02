package ru.ifmo.eshop.storage;

/**
 *
 * @author alex
 * 31.05.2011
 */
public class DeliveryItem {
    private Item item;
    private int amount;
    private double price;

    public DeliveryItem(Item item,int amount,double price) {
        if (item==null || amount<=0 || price<=0) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        this.item=item;
        this.amount=amount;
        this.price=price;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
