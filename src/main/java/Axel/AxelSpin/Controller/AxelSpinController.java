package Axel.AxelSpin.Controller;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class AxelSpinController extends WebSocketServer{

    private static final int port = 8080;
    private CopyOnWriteArrayList<WebSocket> webSockets = new CopyOnWriteArrayList<>();
    private List<String> currentNames = new ArrayList<String>();
    private String spinning = "false";
    private String winner = "";

    public AxelSpinController()
    {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onClose(WebSocket connection, int arg1, String arg2, boolean arg3) {
        webSockets.remove(connection);
    }

    @Override
    public void onError(WebSocket arg0, Exception ex) {
        System.err.print((ex));
    }

    @Override
    public void onMessage(WebSocket arg0, String message) {
        String[] decodedMessage = message.split(" ");
        
        switch(decodedMessage[0])
        {
            case "name": {
                    if(!currentNames.contains(decodedMessage[1])){
                        broadcastMessage("name " + decodedMessage[1]);
                        currentNames.add(decodedMessage[1]);    
                    }
                }
                break;
            case "spin": {
                int k = (int)(Math.random() * currentNames.size());
                broadcastMessage("spin " + String.valueOf(k));
                spinning = "true";
                winner = String.valueOf(k);
            }
                break;
            case "delete": {
                broadcastMessage("delete " + decodedMessage[1]);
                currentNames.remove(decodedMessage[1]);
            }
                break;
            case "spindone":{
                spinning = "false";
            }
        }
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake handshake) {
       
        if(currentNames.size() != 0)
        {
            String names = String.join(" ", currentNames);
            connection.send("names " + names);
        }
        if(spinning.equals("true"))
        {
            connection.send("spin " + winner);
        }

        webSockets.add(connection);
    }

    private void broadcastMessage(String message) {
        for(WebSocket socket: webSockets) {
                socket.send(message);
        }
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket started on port " + getPort());
    }
}