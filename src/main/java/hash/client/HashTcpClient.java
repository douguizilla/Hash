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
            ObjectOutputStream request = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream response = new ObjectInputStream(client.getInputStream());

            while (true) {
                menu();
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();

                optionHandle(option, input, request, response);

                if (option == 5) {
                    client.close();
                    request.close();
                    response.close();
                    System.out.println("Encerrado.");
                    break;
                }

            }
        }catch (Exception e){
            System.out.println("Erro " + e.toString());
            e.printStackTrace();
        }
    }

    public static void menu(){
        System.out.println("======================================");
        System.out.println("Escolha uma das opções abaixo: ");
        System.out.println("1 - Criar");
        System.out.println("2 - Ler");
        System.out.println("3 - Atualizar");
        System.out.println("4 - Deletar");
        System.out.println("5 - Sair");
        System.out.print("Opção: ");
    }

    public static void optionHandle(int option, Scanner input, ObjectOutputStream request, ObjectInputStream response) throws IOException, ClassNotFoundException {

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

                request.writeObject(toServer);

                fromServer = (Message) response.readObject();
                System.out.println(handleMessage(fromServer));
                break;

            case 2:
                System.out.print("Digite a chave: ");

                key = input.next();

                toServer = new Message(1,key.length(),key,0,"",4);
                request.writeObject(toServer);

                fromServer = (Message) response.readObject();
                System.out.println(handleMessage(fromServer));
                break;

            case 3:
                System.out.print("Digite a chave: ");
                key = input.next();

                System.out.print("Digite o novo valor: ");
                value = input.next();

                toServer = new Message(2,key.length(),key,value.length(),value,4);
                request.writeObject(toServer);

                fromServer = (Message) response.readObject();

                System.out.println(handleMessage(fromServer));
                break;

            case 4:
                System.out.print("Digite a chave: ");
                key = input.next();

                toServer = new Message(3,key.length(),key,0,"",4);
                request.writeObject(toServer);

                fromServer = (Message) response.readObject();
                System.out.println(handleMessage(fromServer));
                break;

            case 5:

                toServer = new Message(5,0,"",0,"",4);
                request.writeObject(toServer);

                break;

            default:
                System.out.println("Escolha uma opção válida");
        }
    }

    private static String handleMessage(Message message) {
        switch (message.getContent()){
            case 0:
                if(message.getError() < 5){
                    return "RETORNO: Dados inseridos com sucesso!";
                }else{
                    return "RETORNO: Não foi possível inserir com esta chave!";
                }
            case 1:
                if(message.getError() < 5){
                    return "RETORNO: Valor retornado: " + message.getValue();
                }else{
                    return "RETORNO: Não foi possível ler, pois a chave não existe!";
                }
            case 2:
                if(message.getError() < 5){
                    return "RETORNO: Atualizado com sucesso";
                }else{
                    return "RETORNO: Não foi possível atualizar, pois a chave não existe!";
                }
            case 3:
                if(message.getError() < 5){
                    return "RETORNO: Deletado com sucesso, valor retornado: " + message.getValue();
                }else{
                    return "RETORNO: Não foi possível deletar, pois a chave não existe!";
                }
            default:
                return "RETORNO: opção desconhecida.";
        }
    }
}
