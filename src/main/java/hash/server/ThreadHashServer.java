package hash.server;

import hash.Message;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ThreadHashServer extends Thread{
    private Socket client;
    public ThreadHashServer(Socket client){
        this.client = client;
    }

    public void run(){
        //logica que o server vai fazer para o client
        try{
            ObjectInputStream entrance = new ObjectInputStream(client.getInputStream());
            Message m = (Message) entrance.readObject();
            System.out.println(m.toString());
            entrance.close();
            client.close();

        }catch(Exception e){
            System.out.println("Erro " + e.toString());
        }
    }
}
