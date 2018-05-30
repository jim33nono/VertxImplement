package VertxHttpServiceClient;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class HttpClient {
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		WebClient client = WebClient.create(vertx);

		client.get(8180, "localhost", "/").send(ar -> {
			if (ar.succeeded()) {
				HttpResponse<Buffer> response = ar.result();
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println("Received response " + response.bodyAsString());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());

			}
		});

		client.put(8180, "localhost", "/products/prod1").sendJsonObject(
				new JsonObject().put("id", "prod1").put("name", "Test").put("price", "5.11").put("weight", "1222"),
				ar -> {
					if (ar.succeeded()) {
						HttpResponse<Buffer> response = ar.result();
						System.out.println("Received response with status code " + response.statusCode());
					} else {
						System.out.println("Something went wrong " + ar.cause().getMessage());

					}
				});
		
		client.get(8180, "localhost", "/products").send(ar -> {
			if (ar.succeeded()) {
				HttpResponse<Buffer> response = ar.result();
				System.out.println("Received response with status code " + response.statusCode());
				System.out.println("Received response " + response.bodyAsJsonArray());
			} else {
				System.out.println("Something went wrong " + ar.cause().getMessage());

			}
		});

	}
}
