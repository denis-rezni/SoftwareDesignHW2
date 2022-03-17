package service;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import entity.Currency;
import entity.Product;
import entity.User;
import org.bson.Document;
import rx.Observable;

public class StoreService {

    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public StoreService(MongoCollection<Document> users, MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    public Observable<Success> addUser(String login, Currency currency) {
        return users.insertOne(new User(login, currency).toDocument());
    }

    public Observable<Success> addProduct(String name, double price) {
        return products.insertOne(new Product(name, price).getDocument());
    }

    public Observable<String> getProductsForUser(String login) {
        Observable<Currency> c = users.find(Filters.eq(User.LOGIN, login))
                .first()
                .map(d -> new User(d).getCurrency());
        Observable<Product> p = products.find().toObservable().map(Product::new);
        return Observable.combineLatest(c, p, (currency, product) -> product.getPricedLabel(currency));
    }

}
