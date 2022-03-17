package entity;

import java.util.Map;

public class Currency {
    private final String name;
    private double dollarCost;

    private Currency(String name, double dollarCost) {
        this.name = name;
        this.dollarCost = dollarCost;
    }

    public String getName() {
        return name;
    }

    public double getDollarCost() {
        return dollarCost;
    }

    private static Map<String, Currency> currencies = Map.of(
            "usd", new Currency("usd", 1),
            "eur", new Currency("eur", 0.9),
            "rub", new Currency("rub", 100)
    );

    public static Currency getCurrency(String name) {
        return currencies.get(name);
    }

}
