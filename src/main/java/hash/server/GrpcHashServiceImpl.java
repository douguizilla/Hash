package hash.server;

import br.proto.hash.GrpcHashServiceGrpc;
import br.proto.hash.Hash;
import io.grpc.stub.StreamObserver;

public class GrpcHashServiceImpl extends GrpcHashServiceGrpc.GrpcHashServiceImplBase {

    @Override
    public void create(Hash.CreateRequest request, StreamObserver<Hash.CreateResponse> responseObserver) {
        super.create(request, responseObserver);
    }

    @Override
    public void read(Hash.ReadRequest request, StreamObserver<Hash.ReadResponse> responseObserver) {
        super.read(request, responseObserver);
    }

    @Override
    public void update(Hash.UpdateRequest request, StreamObserver<Hash.UpdateResponse> responseObserver) {
        super.update(request, responseObserver);
    }

    @Override
    public void delete(Hash.DeleteRequest request, StreamObserver<Hash.DeleteResponse> responseObserver) {
        super.delete(request, responseObserver);
    }

    @Override
    public void exit(Hash.ExitRequest request, StreamObserver<Hash.ExitResponse> responseObserver) {
        super.exit(request, responseObserver);
    }
}
