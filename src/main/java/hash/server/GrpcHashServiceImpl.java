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
        synchronized (hashTable) {
            int result = hashTable.add(key, value);

            Hash.CreateResponse response;
            if (result == 1) {
                response = Hash.CreateResponse.newBuilder().setResponse(true).build();
            } else {
                response = Hash.CreateResponse.newBuilder().setResponse(false).build();
            }

            hashTable.showAll();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void read(Hash.ReadRequest request, StreamObserver<Hash.ReadResponse> responseObserver) {
        String key = request.getKey();
        String value;
        synchronized (hashTable){
            value = hashTable.read(key);
            Hash.ReadResponse response;
            if(value != null){
                response = Hash.ReadResponse.newBuilder().setKey(key).setValue(value).build();
            }else {
                response = Hash.ReadResponse.newBuilder().setKey(key).setValue("").build();
            }

            hashTable.showAll();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void update(Hash.UpdateRequest request, StreamObserver<Hash.UpdateResponse> responseObserver) {
        String key = request.getKey();
        String value = request.getValue();
        synchronized (hashTable){
            int result = hashTable.update(key,value);
            Hash.UpdateResponse response;
            if(result == 1){
                response = Hash.UpdateResponse.newBuilder().setResponse(true).build();
            }else{
                response = Hash.UpdateResponse.newBuilder().setResponse(false).build();
            }

            hashTable.showAll();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void delete(Hash.DeleteRequest request, StreamObserver<Hash.DeleteResponse> responseObserver) {
        String key = request.getKey();
        String value;
        synchronized (hashTable){
            value = hashTable.remove(key);
            Hash.DeleteResponse response;
            if(value != null){
                response = Hash.DeleteResponse.newBuilder().setResponse(true).setMessage(value).build();
            }else{
                response = Hash.DeleteResponse.newBuilder().setResponse(false).setMessage(value).build();
            }

            hashTable.showAll();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void exit(Hash.ExitRequest request, StreamObserver<Hash.ExitResponse> responseObserver) {
        super.exit(request, responseObserver);
    }


}
