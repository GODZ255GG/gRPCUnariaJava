package grpcholamundo.servidor;

import io.grpc.stub.StreamObserver;
import calculator.CalculatorServiceGrpc;
import calculator.Holamundo.CalculatorRequest;
import calculator.Holamundo.CalculatorResponse;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void add(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        double result = request.getNumber1() + request.getNumber2();
        CalculatorResponse response = CalculatorResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void subtract(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        double result = request.getNumber1() - request.getNumber2();
        CalculatorResponse response = CalculatorResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void multiply(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        double result = request.getNumber1() * request.getNumber2();
        CalculatorResponse response = CalculatorResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void divide(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        if (request.getNumber2() == 0) {
            responseObserver.onError(new IllegalArgumentException("Division by zero"));
            return;
        }
        double result = request.getNumber1() / request.getNumber2();
        CalculatorResponse response = CalculatorResponse.newBuilder().setResult(result).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

