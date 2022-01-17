package hash.client;

import br.proto.hash.GrpcHashServiceGrpc.GrpcHashServiceBlockingStub;
import br.proto.hash.Hash.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

import static br.proto.hash.GrpcHashServiceGrpc.newBlockingStub;

public class HashGrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 54321)
                .usePlaintext()
                .build();

        GrpcHashServiceBlockingStub clientStub = newBlockingStub(channel);

        Scanner input = new Scanner(System.in);
        int option;
        while(true){
            menu();

            while(!input.hasNextInt())
            {
                System.out.println("Digite uma opção válida.");
                input.next();
            }

            option = input.nextInt();


            if(option == 5){
                channel.shutdown();
                break;
            }

            options(option, input, clientStub);
        }

        channel.shutdown();
        System.out.println("Encerrado.");
    }

    public static void options(int option, Scanner input, GrpcHashServiceBlockingStub clientStub){
        String key;
        String value;

        switch (option){
            case 1:
                System.out.print("Digite a chave: ");

                while(!input.hasNextInt())
                {
                    System.out.print("Digite um número inteiro! \n");
                    input.next();
                }

                key = input.next();

                System.out.print("Digite o valor: ");
                value = input.next();

                CreateRequest createRequest = CreateRequest
                        .newBuilder()
                        .setKey(key)
                        .setValue(value)
                        .build();

                CreateResponse createResponse = clientStub.create(createRequest);

                if(createResponse.getResponse()){
                    System.out.println("RETORNO: Dados inseridos com sucesso!");
                }else{
                    System.out.println("RETORNO: Não foi possível inserir com esta chave!");
                }

                break;

            case 2:

                System.out.print("Digite a chave: ");

                while(!input.hasNextInt())
                {
                    System.out.print("Digite um número inteiro: \n");
                    input.next();
                }

                key = input.next();

                ReadRequest readRequest = ReadRequest
                        .newBuilder()
                        .setKey(key)
                        .build();

                ReadResponse readResponse = clientStub.read(readRequest);

                if(!readResponse.getValue().isEmpty()){
                    System.out.println("RETORNO: Valor retornado: " + readResponse.getValue());
                }else{
                    System.out.println("RETORNO: Não foi possível ler, pois a chave não existe!");
                }

                break;

            case 3:

                System.out.print("Digite a chave: ");

                while(!input.hasNextInt())
                {
                    System.out.print("Digite um número inteiro: \n");
                    input.next();
                }

                key = input.next();

                System.out.print("Digite o valor: ");
                value = input.next();

                UpdateRequest updateRequest = UpdateRequest
                        .newBuilder()
                        .setKey(key)
                        .setValue(value)
                        .build();

                UpdateResponse updateResponse = clientStub.update(updateRequest);

                if(updateResponse.getResponse()){
                    System.out.println("RETORNO: Atualizado com sucesso");
                }else{
                    System.out.println("RETORNO: Não foi possível atualizar, pois a chave não existe!");
                }

                break;

            case 4:

                System.out.print("Digite a chave: ");

                while(!input.hasNextInt())
                {
                    System.out.print("Digite um número inteiro: \n");
                    input.next();
                }

                key = input.next();

                DeleteRequest deleteRequest = DeleteRequest
                        .newBuilder()
                        .setKey(key)
                        .build();

                DeleteResponse deleteResponse = clientStub.delete(deleteRequest);

                if(deleteResponse.getResponse()){
                    System.out.println("RETORNO: Deletado com sucesso, valor retornado: " + deleteResponse.getMessage());
                }else{
                    System.out.println("RETORNO: Não foi possível deletar, pois a chave não existe!");
                }

                break;

            default:
                System.out.println("RETORNO: opção desconhecida.");
                break;
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
}
