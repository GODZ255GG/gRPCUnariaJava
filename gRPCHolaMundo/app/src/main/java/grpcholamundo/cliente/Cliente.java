package grpcholamundo.cliente;

import calculator.CalculatorServiceGrpc;
import calculator.Holamundo.CalculatorRequest;
import calculator.Holamundo.CalculatorResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import javax.swing.*;

public class Cliente {

    public static void main(String[] args) {
        String host = "localhost"; // La IP o nombre de dominio del servidor gRPC
        int puerto = 8080; // El puerto del servidor gRPC

        ManagedChannel ch = ManagedChannelBuilder
            .forAddress(host, puerto)
            .usePlaintext()
            .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(ch);

        while (true) {
            String opt = JOptionPane.showInputDialog(
                "Seleccione una operacion\n" +
                "1. Suma\n" +
                "2. Resta\n" +
                "3. Multiplicacion\n" +
                "4. Division\n\n" +
                "Cancelar para salir");

            if (opt == null) { // Si el usuario cancela o cierra la ventana
                break;
            }

            try {
                double a = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el primer numero:"));
                double b = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el segundo numero:"));

                CalculatorRequest request = CalculatorRequest.newBuilder()
                        .setNumber1(a)
                        .setNumber2(b)
                        .build();

                CalculatorResponse response;

                switch (opt) {
                    case "1":
                        response = stub.add(request);
                        JOptionPane.showMessageDialog(null, a + " + " + b + " = " + response.getResult());
                        break;

                    case "2":
                        response = stub.subtract(request);
                        JOptionPane.showMessageDialog(null, a + " - " + b + " = " + response.getResult());
                        break;

                    case "3":
                        response = stub.multiply(request);
                        JOptionPane.showMessageDialog(null, a + " x " + b + " = " + response.getResult());
                        break;

                    case "4":
                        if (b == 0) {
                            JOptionPane.showMessageDialog(null, "Error: División por cero");
                        } else {
                            response = stub.divide(request);
                            JOptionPane.showMessageDialog(null, a + " / " + b + " = " + response.getResult());
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida. Elija entre 1 y 4.");
                        break;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Por favor, ingrese un número válido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error desconocido:\n" + e);
            }
        }

        ch.shutdown(); // Apaga el canal cuando termina
    }
}
