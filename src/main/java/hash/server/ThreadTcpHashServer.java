package hash.server;

import hash.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadTcpHashServer extends Thread {
    private Socket client;
    private HashTable hashTable;

    public ThreadTcpHashServer(Socket client, HashTable hashTable) {
        this.client = client;
        this.hashTable = hashTable;
    }


    public void run() {
        //logica que o server vai fazer para o client
        try {
            ObjectOutputStream response = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream entrance = new ObjectInputStream(client.getInputStream());
            while (true) {
                Message m = (Message) entrance.readObject();

                if (m.getContent() == 5) {
                    response.close();
                    entrance.close();
                    client.close();
                    break;
                }

                operation(m, response);

                hashTable.showAll();
            }

        } catch (Exception e) {
            System.out.println("Erro " + e.toString());
            e.printStackTrace();
        }
    }

    public void operation(Message request, ObjectOutputStream response) throws IOException {

        Message message;
        int result;
        String value;

        switch (request.getContent()) {
            case 0:

                result = hashTable.add(request.getKey(), request.getValue());

                if (result == 1) {
                    System.out.println("CREATE TPC REQ KEY: " + request.getKey() + ", VALUE: " + request.getValue() + ", STATUS: SUCCESS");
                    message = new Message(0, 0, "", 0, "", 4);
                } else {
                    System.out.println("CREATE TPC REQ KEY: " + request.getKey() + ", VALUE: " + request.getValue() + ", STATUS: FAILURE");
                    message = new Message(0, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;

            case 1:

                value = hashTable.read(request.getKey());

                if (value != null) {
                    System.out.println("READ TPC REQ KEY: " + request.getKey() + ", VALUE: " + value + ", STATUS: SUCCESS");
                    message = new Message(1, request.getKeySize(), request.getKey(), value.length(), value, 4);
                } else {
                    System.out.println("READ TPC REQ KEY: " + request.getKey() + ", VALUE: null, STATUS: FAILURE");
                    message = new Message(1, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;

            case 2:

                result = hashTable.update(request.getKey(), request.getValue());

                if (result == 1) {
                    System.out.println("UPDATE TPC REQ KEY: " + request.getKey() + ", CHANGE OLD_VALUE: " + result + ", TO NEW_VALUE: " + request.getValue() + ", STATUS: SUCCESS");
                    message = new Message(2, 0, "", 0, "", 4);
                } else {
                    System.out.println("UPDATE TPC REQ KEY: " + request.getKey() + ", VALUE: null, STATUS: FAILURE");
                    message = new Message(2, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;

            case 3:

                value = hashTable.remove(request.getKey());

                if (value != null) {
                    System.out.println("DELETE TPC REQ KEY: " + request.getKey() + ", RETURN_VALUE: " + value + ", STATUS: SUCCESS");
                    message = new Message(3, request.getKeySize(), request.getKey(), value.length(), value, 4);
                } else {
                    System.out.println("DELETE TPC REQ KEY: " + request.getKey() + ", VALUE: null, STATUS: FAILURE");
                    message = new Message(3, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;

            case 5:

                System.out.println("LOGOUT CLIENT TPC");
                break;

            default:

                message = new Message(4, 0, "", 0, "", 5);
                response.writeObject(message);
                break;
        }
    }
}
