package hash.client;

import hash.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HashTcpClient {

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",12345);
        ObjectOutputStream request = new ObjectOutputStream(client.getOutputStream());
        Scanner input = new Scanner(System.in);
        System.out.print("Chave: ");
        String key = input.next();

        System.out.print("Valor: ");
        String value = input.next();

        Message p = new Message(0,key.length(),key,value.length(),value,-1);
        request.writeObject(p);
        request.close();
        client.close();
    }
}
