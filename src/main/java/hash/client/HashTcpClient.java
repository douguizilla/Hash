package hash.client;

import hash.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HashTcpClient {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1",12345);

            while (true) {
                menu();
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();

                optionHandle(option, client);

                if (option == 5) {
                    client.close();
                    System.out.println("Encerrado.");
                    break;
                }

            }
        }catch (Exception e){
            System.out.println("Erro " + e.toString());
        }
    }

    public static void menu(){
        System.out.println("Escolha uma das opções abaixo: ");
        System.out.println("1 - Criar");
        System.out.println("2 - Ler");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Deletar");
        System.out.println("5 - Sair");
        System.out.print("Opção: ");
    }

    public static void optionHandle(int option, Socket client) throws IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);

        String key;
        String value;
        Message toServer;
        Message fromServer;
        switch (option) {
            case 1:
                System.out.print("Digite a chave: ");
                key = input.next();

                System.out.print("Digite o valor: ");
                value = input.next();

                toServer = new Message(0,key.length(),key,value.length(),value,4);
                ObjectOutputStream requestAdd = new ObjectOutputStream(client.getOutputStream());
                requestAdd.writeObject(toServer);

                ObjectInputStream responseAdd = new ObjectInputStream(client.getInputStream());
                fromServer = (Message) responseAdd.readObject();
                System.out.println(handleMessage(fromServer));
                break;

            case 2:
                System.out.print("Digite a chave: ");
                key = input.next();

                toServer = new Message(1,key.length(),key,0,"",4);
                ObjectOutputStream requestRead = new ObjectOutputStream(client.getOutputStream());
                requestRead.writeObject(toServer);

                ObjectInputStream responseRead = new ObjectInputStream(client.getInputStream());
                fromServer = (Message) responseRead.readObject();
                System.out.println(handleMessage(fromServer));
                break;

            case 3:
                System.out.print("Digite a chave: ");
                key = input.next();

                System.out.print("Digite o novo valor: ");
                value = input.next();

                toServer = new Message(2,key.length(),key,value.length(),value,4);
                ObjectOutputStream requestUpdate = new ObjectOutputStream(client.getOutputStream());
                requestUpdate.writeObject(toServer);

                ObjectInputStream responseUpdate = new ObjectInputStream(client.getInputStream());
                fromServer = (Message) responseUpdate.readObject();

                System.out.println(handleMessage(fromServer));
                break;

            case 4:
                System.out.print("Digite a chave: ");
                key = input.next();

                toServer = new Message(3,key.length(),key,0,"",4);
                ObjectOutputStream requestDelete = new ObjectOutputStream(client.getOutputStream());
                requestDelete.writeObject(toServer);

                ObjectInputStream responseDelete = new ObjectInputStream(client.getInputStream());
                fromServer = (Message) responseDelete.readObject();
                System.out.println(handleMessage(fromServer));
                break;

            case 5:

                break;

            default:
                System.out.println("Escolha uma opção válida");
        }
    }

    private static String handleMessage(Message message) {
        switch (message.getContent()){
            case 0:
                if(message.getError() < 5){
                    return "Dados inseridos com sucesso!";
                }else{
                    return "Não foi possível inserir com esta chave!";
                }
            case 1:
                if(message.getError() < 5){
                    return "Valor retornado: " + message.getValue();
                }else{
                    return "Não foi possível ler, pois a chave não existe!";
                }
            case 2:
                if(message.getError() < 5){
                    return "Atualizado com sucesso";
                }else{
                    return "Não foi possível atualizar, pois a chave não existe!";
                }
            case 3:
                if(message.getError() < 5){
                    return "Deletado com sucesso, valor retornado: " + message.getValue();
                }else{
                    return "Não foi possível deletar, pois a chave não existe!";
                }
            default:
                return "Retorno desconhecido.";
        }
    }
}
