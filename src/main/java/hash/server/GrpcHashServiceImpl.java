package hash.server;

import br.proto.hash.GrpcHashServiceGrpc;
import br.proto.hash.Hash;
import io.grpc.stub.StreamObserver;

public class GrpcHashServiceImpl extends GrpcHashServiceGrpc.GrpcHashServiceImplBase {
    private HashTable hashTable;

    GrpcHashServiceImpl(HashTable hashTable) {
        this.hashTable = hashTable;
    }

    @Override
    public void create(Hash.CreateRequest request, StreamObserver<Hash.CreateResponse> responseObserver) {
        String key = request.getKey();
        String value = request.getValue();

        int result = hashTable.add(key, value);

        Hash.CreateResponse response;
        if (result == 1) {
            System.out.println("CREATE GRPC REQ KEY: " + key + ", VALUE: " + value + ", STATUS: SUCCESS");
            response = Hash.CreateResponse.newBuilder().setResponse(true).build();
        } else {
            System.out.println("CREATE GRPC REQ KEY: " + key + ", VALUE: " + value + ", STATUS: FAILURE");
            response = Hash.CreateResponse.newBuilder().setResponse(false).build();
        }

        hashTable.showAll();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void read(Hash.ReadRequest request, StreamObserver<Hash.ReadResponse> responseObserver) {
        String key = request.getKey();
        String value;

        value = hashTable.read(key);
        Hash.ReadResponse response;
        if (value != null) {
            System.out.println("READ GRPC REQ KEY: " + key + ", VALUE: " + value + ", STATUS: SUCCESS");
            response = Hash.ReadResponse.newBuilder().setKey(key).setValue(value).build();
        } else {
            System.out.println("READ GRPC REQ KEY: " + key + ", VALUE: null, STATUS: FAILURE");
            response = Hash.ReadResponse.newBuilder().setKey(key).setValue("").build();
        }

        hashTable.showAll();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void update(Hash.UpdateRequest request, StreamObserver<Hash.UpdateResponse> responseObserver) {
        String key = request.getKey();
        String value = request.getValue();

        String oldValue = hashTable.read(key);
        int result = hashTable.update(key, value);
        Hash.UpdateResponse response;
        if (result == 1) {
            System.out.println("UPDATE GRPC REQ KEY: " + key + ", CHANGE OLD_VALUE: " + oldValue + ", TO NEW_VALUE: " + value + ", STATUS: SUCCESS");
            response = Hash.UpdateResponse.newBuilder().setResponse(true).build();
        } else {
            System.out.println("UPDATE GRPC REQ KEY: " + key + ", VALUE: null, STATUS: FAILURE");
            response = Hash.UpdateResponse.newBuilder().setResponse(false).build();
        }

        hashTable.showAll();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void delete(Hash.DeleteRequest request, StreamObserver<Hash.DeleteResponse> responseObserver) {
        String key = request.getKey();
        String value;

        value = hashTable.remove(key);
        Hash.DeleteResponse response;
        if (value != null) {
            System.out.println("DELETE GRPC REQ KEY: " + key + ", RETURN_VALUE: " + value + ", STATUS: SUCCESS");
            response = Hash.DeleteResponse.newBuilder().setResponse(true).setMessage(value).build();
        } else {
            System.out.println("DELETE GRPC REQ KEY: " + key + ", VALUE: null, STATUS: FAILURE");
            response = Hash.DeleteResponse.newBuilder().setResponse(false).setMessage("").build();
        }

        hashTable.showAll();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void exit(Hash.ExitRequest request, StreamObserver<Hash.ExitResponse> responseObserver) {
        System.out.println("LOGOUT CLIENT GRPC");
        super.exit(request, responseObserver);
    }


}
