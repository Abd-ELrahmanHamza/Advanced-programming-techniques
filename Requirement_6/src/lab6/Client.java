package lab6;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static int MAKE_APPOINTMENT = 6666;
    public static int CANCEL_APPOINTMENT = 6667;

    public static void main(String[] args) throws IOException {
        System.out.println("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String patientName = scanner.nextLine();

        Socket makeSocket = new Socket("localhost", MAKE_APPOINTMENT);
        Socket cancelSocket = new Socket("localhost", CANCEL_APPOINTMENT);

        System.out.println("Make and cancel sockets has been created");

        PrintWriter outMake = new PrintWriter(makeSocket.getOutputStream(), true);
        PrintWriter outCancel = new PrintWriter(cancelSocket.getOutputStream(), true);

        outMake.println(patientName);
        outCancel.println(patientName);

        System.out.println("patient's name has been sent to Make and cancel sockets");

        BufferedReader reader = null;
        try {
            while (true) {
                System.out.println("Enter 1 to make an appointment and any other character to cancel: ");
                int action = scanner.nextInt();
                System.out.println("Enter doctor's ID");
                int docID = scanner.nextInt();
                System.out.println("Enter time slot");
                int timeSlot = scanner.nextInt();
                System.out.println("Doctor's ID and time slot has been read");

                if (action == 1) {
                    outMake.println(docID);
                    outMake.println(timeSlot);
                    System.out.println("Data sent to make socket");

                    reader = new BufferedReader(new InputStreamReader(makeSocket.getInputStream()));
                } else {
                    outCancel.println(docID);
                    outCancel.println(timeSlot);
                    System.out.println("Data sent to cancel socket");

                    reader = new BufferedReader(new InputStreamReader(cancelSocket.getInputStream()));
                }
                String serverResponse = reader.readLine();
                System.out.println("Server response : " + serverResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            outCancel.close();
            makeSocket.close();
            reader.close();
        }

    }
}