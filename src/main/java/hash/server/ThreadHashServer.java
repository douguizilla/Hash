package hash.server;

import java.net.Socket;

public class ThreadHashServer extends Thread{
    private Socket client;
    public ThreadHashServer(Socket client){
        this.client = client;
    }

    public void run(){
        //logica que o server vai fazer para o client
    }
}
