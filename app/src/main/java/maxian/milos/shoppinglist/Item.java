package maxian.milos.shoppinglist;

/**
 * Created by milos on 29.09.2017.
 */

public class Item {

    private int id;
    private String name;
    private int count;
    private double price;

    public Item(String name, int count, double price) {
        this.id = 0;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }
}
