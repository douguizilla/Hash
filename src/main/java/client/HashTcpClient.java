package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HashTcpClient {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",12345);
        ObjectOutputStream request = new ObjectOutputStream(client.getOutputStream());
        Pessoa p = new Pessoa("Douglas", "24");
        request.writeObject(p);
        request.close();
        client.close();
    }
}
