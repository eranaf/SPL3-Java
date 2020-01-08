package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.impl.ConnectionImpl;
import bgu.spl.net.srv.Connections;

public class StompMessagingProtocolimpl implements StompMessagingProtocol {
    private int OwnerId;
    private String OwnerUsername;
    private Connections<String> connections;
    @Override
    public void start(int connectionId, Connections<String> connections) {
        OwnerId = connectionId;
        connections = connections;

    }

    @Override
    public void process(String message) {
        String StompCommand = message.substring(0, message.indexOf("\n"));
        String messageBody = message.substring(message.indexOf("\n"));
        switch(StompCommand) {
            case "CONNECT": {

            }
            //message, RECEIPT, ERROR from server to client??
            case "SEND":{
                String dest = messageBody.substring(messageBody.indexOf("destination:"));
                dest.substring(dest.indexOf(":")+1);
                String body = dest.substring(dest.indexOf("\n")+1);
                dest.substring(0, dest.indexOf("\n"));
                //sends a message to a destination - a topic
                ConnectionImpl.send(dest, body);

            }
            case "SUBSCRIBE":{
                String dest = messageBody.substring(messageBody.indexOf("destination:"));
                dest.substring(dest.indexOf(":")+1, dest.indexOf("\n"));
                String id = messageBody.substring(messageBody.indexOf("id:"));
                id.substring(id.indexOf(":")+1,id.indexOf("\n"));
                ConnectionImpl.subscribe(dest, id);
                //Subscribe to topic for client id

            }
            case "UNSUBSCRIBE":{
                String dest = messageBody.substring(messageBody.indexOf("destination:"));
                dest.substring(dest.indexOf(":")+1, dest.indexOf("\n"));
                String id = messageBody.substring(messageBody.indexOf("id:"));
                id.substring(id.indexOf(":")+1,id.indexOf("\n"));
                ConnectionImpl.unsubscribe(dest, id);
                //Will unsubscribe from a topic

            }
            case "DISCONNECT":{
                String receipt = messageBody.substring(messageBody.indexOf("receipt:"));
                receipt.substring(receipt.indexOf(":")+1,receipt.indexOf("\n"));
                ConnectionImpl.disconnect(OwnerId,OwnerUsername);
                //receipt can be added to any frame that needs response to the client
            }
        }
        //protocol.process(message)
        //process the message as needed in STOMP protocol
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
