import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import controller.StoreController;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;
import service.StoreService;

public class Main {

    public static void main(String[] args) {
        MongoDatabase db = createMongoClient().getDatabase("reactive");
        StoreController controller = new StoreController(new StoreService(
                db.getCollection("users"), db.getCollection("products")));

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = controller.getResponse(req);
                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}
