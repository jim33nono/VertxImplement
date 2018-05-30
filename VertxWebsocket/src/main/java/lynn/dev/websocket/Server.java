package lynn.dev.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;

public class Server extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		final Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Server());
	}

	@Override
	public void start() throws Exception {
		vertx.createHttpServer().websocketHandler(ws -> ws.handler(ws::writeBinaryMessage)).requestHandler(req -> {
			if (req.uri().equals("/")) {
				req.response().sendFile("ws.html");
			}
			req.bodyHandler(bodyHandler -> {
				final JsonObject body = bodyHandler.toJsonObject();
				System.out.println(body.toString());
			});
		}).listen(8180);

		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
			@Override
			public void handle(ServerWebSocket webs) {
				System.out.println("Client connected");
//				webs.writeFinalTextFrame("Hellow");
//				webs.writeBinaryMessage(Buffer.buffer("Hello user"));
				System.out.println("Client's message: ");
				webs.handler(data -> {
					System.out.println("Received data " + data.toString("UTF-8"));
					webs.writeFinalTextFrame(data.toString("UTF-8"));
				});

			}
		}).requestHandler(req -> {
			if (req.uri().equals("/")) {
				req.response().sendFile("ws.html");
			}
		}).listen(8180);
	}
}