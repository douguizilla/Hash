package server;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HashServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(12345);
        Socket client = server.accept();
        ObjectInputStream response = new ObjectInputStream(client.getInputStream());
        Pessoa p = (Pessoa) response.readObject();
        System.out.println("Nome " + p.getNome() + " Idade " + p.getIdade());
        response.close();
        client.close();
        server.close();
    }
}
