package de.bonprix.test.feature;

public class Product {
    private int id;
    private String name;
    private float price;

    public Product(final int id, final String name, final float price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(final int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(final float price) {
        this.price = price;
    }
}
