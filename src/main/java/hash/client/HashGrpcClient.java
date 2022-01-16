package hash.client;

import br.proto.hash.GrpcHashServiceGrpc;
import br.proto.hash.GrpcHashServiceGrpc.GrpcHashServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class HashGrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 54321)
                .usePlaintext()
                .build();

        GrpcHashServiceBlockingStub clientStub = GrpcHashServiceGrpc.newBlockingStub(channel);
        Scanner input = new Scanner(System.in);
        int option;
        while(true){
            menu();
            option = input.nextInt();

            if(option == 5){
                break;
            }


        }

        channel.shutdown();
        System.out.println("FINALIZADO");
    }

    public static void options(int option){
        switch (option){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
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
