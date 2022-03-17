package entity;

import org.bson.Document;

public class User {

    public static final String LOGIN = "login";
    public static final String CURRENCY = "currency";

    private final String login;
    private final Currency currency;

    public String getLogin() {
        return login;
    }

    public Currency getCurrency() {
        return currency;
    }

    public User(String login, Currency currency) {
        this.login = login;
        this.currency = currency;
    }

    public User(Document document) {
        this(document.getString(LOGIN), Currency.getCurrency(document.getString(CURRENCY)));
    }

    public Document toDocument() {
        return new Document()
                .append(LOGIN, login)
                .append(CURRENCY, currency.getName());
    }
}
