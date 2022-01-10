package hash.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HashServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(12345);

        while(true){
            //aguardando conexões de clientes
            Socket client = server.accept();

            //mostrar endereço IP do cliente
            System.out.println("Cliente " + client.getInetAddress().getHostAddress() + " conectado");

            //definir uma thread para cada cliente
            ThreadHashServer thread = new ThreadHashServer(client);
            thread.start();
        }

    }
}
