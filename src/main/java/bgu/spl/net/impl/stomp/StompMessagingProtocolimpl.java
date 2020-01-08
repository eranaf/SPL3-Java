package bgu.spl.net.impl.stomp;

import bgu.spl.net.api.StompMessagingProtocol;
import bgu.spl.net.srv.Connections;

public class StompMessagingProtocolimpl implements StompMessagingProtocol {
    private int OwnerClient;
    private Connections<String> connections;
    @Override
    public void start(int connectionId, Connections<String> connections) {
        OwnerClient = connectionId;
        connections = connections;

    }

    @Override
    public void process(String message) {
        String StompCommand = message.substring(message.indexOf("/n"));
        switch(StompCommand) {
            case "CONNECT": {

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
