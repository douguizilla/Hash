package hash.server;




import hash.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HashServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(12345);
        Socket client = server.accept();
        ObjectInputStream response = new ObjectInputStream(client.getInputStream());
        Message m = (Message) response.readObject();
        System.out.println(m.toString());
        response.close();
        client.close();
        server.close();
    }
}
