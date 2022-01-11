package hash.server;

import hash.Message;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadHashServer extends Thread {
    private Socket client;
    private HashTable hashTable;

    public ThreadHashServer(Socket client, HashTable hashTable) {
        this.client = client;
        this.hashTable = hashTable;
    }

    public void run() {
        //logica que o server vai fazer para o client
        try {
            ObjectInputStream entrance = new ObjectInputStream(client.getInputStream());
            Message m = (Message) entrance.readObject();
            System.out.println(m.toString());

            operation(m);

            hashTable.showAll();

            entrance.close();
            client.close();

        } catch (Exception e) {
            System.out.println("Erro " + e.toString());
        }
    }

    public void operation(Message request) throws IOException {
        ObjectOutputStream response = new ObjectOutputStream(client.getOutputStream());
        Message message;
        int result;
        switch (request.getContent()) {
            case 0:
                result = hashTable.add(request.getKey(), request.getValue());

                if (result == 1) {
                    message = new Message(0, 0, "", 0, "", 4);
                } else {
                    message = new Message(0, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 1:
                String read = hashTable.read(request.getKey());
                if (read != null) {
                    message = new Message(1, request.getKeySize(),request.getKey(),read.length(),read, 4);
                } else {
                    message = new Message(1, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 2:
                result = hashTable.update(request.getKey(), request.getValue());

                if (result == 1) {
                    message = new Message(2, 0, "", 0, "", 4);
                } else {
                    message = new Message(2, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 3:
                String value = hashTable.remove(request.getKey());
                if(value != null){
                    message = new Message(3, request.getKeySize(), request.getKey(), value.length(), value, 4);
                } else {
                    message = new Message(3, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            default:
                message = new Message(4, 0, "", 0, "", 5);
                response.writeObject(message);
                break;
        }
    }
}
