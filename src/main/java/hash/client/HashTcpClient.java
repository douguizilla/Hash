package hash.client;

import hash.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HashTcpClient {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",12345);
        ObjectOutputStream request = new ObjectOutputStream(client.getOutputStream());
        Message p = new Message(0,7,"Douglas",5,"doido",-1);
        request.writeObject(p);
        request.close();
        client.close();
    }
}
