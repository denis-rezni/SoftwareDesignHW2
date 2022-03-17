package entity;

import org.bson.Document;

public class Product {

    private static final String NAME = "name";
    private static final String PRICE = "price";

    private final String name;
    private double price;

    public Product(Document document) {
        this(document.getString(NAME), document.getDouble(PRICE));
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Document getDocument() {
        return new Document()
                .append(NAME, name)
                .append(PRICE, price);
    }

    public String getPricedLabel(Currency currency) {
        return name + ": " + (price * currency.getDollarCost()) + " " + currency.getName();
    }
}
