package hash.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HashServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashTable hashTable = new HashTable();

        ServerSocket server = new ServerSocket(12345);

        Server grpcServer = ServerBuilder.forPort(54321).addService(
                new GrpcHashServiceImpl(hashTable)).build();

        while(true){
            //aguardando conexões de clientes
            Socket client = server.accept();
            grpcServer.start();

            //mostrar endereço IP do cliente
            System.out.println("Cliente " + client.getInetAddress().getHostAddress() + " conectado");

            //definir uma thread para cada cliente
            ThreadTcpHashServer thread = new ThreadTcpHashServer(client,hashTable);

            thread.start();
        }

    }
}
