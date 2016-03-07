package com.example.camel.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;

public class WebsocketClient {

	private static List<String> received = new ArrayList<String>();
	private static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {

		try {
			AsyncHttpClient c = new AsyncHttpClient();
			WebSocket websocket = c.prepareGet("ws://127.0.0.1:9292/echo")
					.execute(new WebSocketUpgradeHandler.Builder()
							.addWebSocketListener(new WebSocketTextListener() {

								@Override
								public void onOpen(WebSocket websocket) {

								}

								@Override
								public void onClose(WebSocket websocket) {
									System.out.println("session close~~~~~~");
								}

								@Override
								public void onError(Throwable t) {
									t.printStackTrace();
								}

								@Override
								public void onMessage(String message) {
									received.add(message);
									System.out.println("haha --> " + message);
									latch.countDown();
								}

							}).build())
					.get();
			websocket.sendMessage("Beer");
			Thread.sleep(999999999);
			// websocket.close();
			// c.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
