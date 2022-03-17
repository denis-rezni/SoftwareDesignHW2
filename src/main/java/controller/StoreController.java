package controller;

import entity.Currency;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;
import service.StoreService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StoreController {

    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    private final Map<String, Function<HttpServerRequest<ByteBuf>, Observable<String>>> controller = Map.of(
            "add-product", this::addProduct,
            "register", this::register,
            "products", this::getProducts
    );

    public Observable<String> getResponse(HttpServerRequest<ByteBuf> req) {
        return controller.get(req.getDecodedPath().substring(1)).apply(req);
    }

    private Observable<String> getProducts(HttpServerRequest<ByteBuf> req) {
        String login = req.getQueryParameters().get("login").get(0);
        return service.getProductsForUser(login);
    }

    private Observable<String> register(HttpServerRequest<ByteBuf> req) {
        String login = req.getQueryParameters().get("login").get(0);
        Currency currency = Currency.getCurrency(req.getQueryParameters().get("currency").get(0));
        return service.addUser(login, currency).map(s -> "ok");
    }

    private Observable<String> addProduct(HttpServerRequest<ByteBuf> req) {
        String name = req.getQueryParameters().get("name").get(0);
        double price = Double.parseDouble(req.getQueryParameters().get("price").get(0));
        return service.addProduct(name, price).map(s -> "ok");
    }
}
