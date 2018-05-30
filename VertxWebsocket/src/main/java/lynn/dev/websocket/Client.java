package lynn.dev.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;

public class Client extends AbstractVerticle {
	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Client());
		
	}

	@Override
	public void start() throws Exception {
		HttpClient client = vertx.createHttpClient();

		client.websocket(8180, "localhost", "/", websocket -> {
			websocket.handler(data -> {
				System.out.println("Received data " + data.toString("ISO-8859-1"));
				client.close();
			});
			websocket.writeBinaryMessage(Buffer.buffer("Hello world!"));
		});
	}
}
