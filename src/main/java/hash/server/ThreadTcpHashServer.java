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
           while(true) {
               Message m = (Message) entrance.readObject();
               System.out.println(m.toString());

               operation(m, response);

               showHashTable();

               if (m.getContent() == 5){
                   response.close();
                   entrance.close();
                   client.close();
                   break;
               }
           }

        } catch (Exception e) {
            System.out.println("Erro " + e.toString());
            e.printStackTrace();
        }
    }

    private void showHashTable(){
        System.out.println("=================");
        System.out.println("Tabela atualizada:");
        hashTable.showAll();
        System.out.println("=================");
    }

    public void operation(Message request,ObjectOutputStream response) throws IOException {

        Message message;
        int result;
        String value;

        switch (request.getContent()) {
            case 0:
                synchronized (hashTable) {
                    result = hashTable.add(request.getKey(), request.getValue());
                }
                if (result == 1) {
                    message = new Message(0, 0, "", 0, "", 4);
                } else {
                    message = new Message(0, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 1:

                synchronized (hashTable) {
                    value = hashTable.read(request.getKey());
                }
                if (value != null) {
                    message = new Message(1, request.getKeySize(),request.getKey(),value.length(),value, 4);
                } else {
                    message = new Message(1, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 2:
                synchronized (hashTable) {
                    result = hashTable.update(request.getKey(), request.getValue());
                }
                if (result == 1) {
                    message = new Message(2, 0, "", 0, "", 4);
                } else {
                    message = new Message(2, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 3:
                synchronized (hashTable) {
                    value = hashTable.remove(request.getKey());
                }
                if(value != null){
                    message = new Message(3, request.getKeySize(), request.getKey(), value.length(), value, 4);
                } else {
                    message = new Message(3, 0, "", 0, "", 5);
                }
                response.writeObject(message);
                break;
            case 5:
                System.out.println("cliente saiu");
                break;
            default:
                message = new Message(4, 0, "", 0, "", 5);
                response.writeObject(message);
                break;
        }
    }
}
