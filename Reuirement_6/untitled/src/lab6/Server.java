package lab6;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    public static int MAKE_APPOINTMENT = 6666;
    public static int CANCEL_APPOINTMENT = 6667;

    public static void main(String[] args) throws IOException {
        // MAKE_APPOINTMENT port
        new Thread() {
            public void run() {
                try {
                    ServerSocket sSocket = new ServerSocket(MAKE_APPOINTMENT);
                    while (true) {
                        Socket socket = sSocket.accept();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String patientName = reader.readLine();
                        (new Hospital(socket, patientName)).start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();


        // CANCEL_APPOINTMENT port
        new Thread() {
            public void run() {
                try {
                    ServerSocket sSocket = new ServerSocket(CANCEL_APPOINTMENT);
                    while (true) {
                        Socket socket = sSocket.accept();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String patientName = reader.readLine();
                        (new Hospital(socket, patientName)).start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

    public static class Hospital extends Thread {
        Socket socket;
        String patientName;
        public static List<Doctor> doctors = new ArrayList<>();

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter out = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    int docId = Integer.parseInt(reader.readLine());
                    int timeSlot = Integer.parseInt(reader.readLine());
                    System.out.printf("doctor id = %d and time slot has been received = %d\n", docId, timeSlot);

                    if (this.socket.getLocalPort() == MAKE_APPOINTMENT) {
                        System.out.println("Make socket");
                        String response = makeAppointment(docId, timeSlot, patientName);
                        out.println(response);
                        print();
                    } else {
                        System.out.println("Cancel socket");
                        String response = cancelAppointment(docId, timeSlot, patientName);
                        out.println(response);
                        print();
                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                out.close();
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public Hospital(Socket sock, String patientName) {
            this.socket = sock;
            this.patientName = patientName;
            try {
                File myObj = new File("input.txt");
                Scanner myReader = new Scanner(myObj);
                int size = myReader.nextInt();
                for (int i = 0; i < size; i++) {
                    Doctor doctor = new Doctor();
                    doctor.ID = myReader.nextInt();
                    doctor.name = myReader.nextLine();
                    doctor.name = myReader.nextLine();
                    int s = myReader.nextInt();
                    doctor.patients = new String[s];
                    doctor.timeSlots = new boolean[s];
                    if (doctor.ID >= doctors.size())
                        doctors.add(doctor);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        String makeAppointment(int docID, int timeSlot, String patientName) {
            System.out.println("Doctors size = " + doctors.size());
            if (docID >= doctors.size())
                return "the doctor id is not found in hospital";
            Doctor doctor = doctors.get(docID);

            if (doctor.timeSlots.length <= timeSlot)
                return "the timeslot index is out of boundary";

            if (!doctor.timeSlots[timeSlot]) {

                doctor.timeSlots[timeSlot] = true;
                doctor.patients[timeSlot] = patientName;
                return "Making the appointment is done successfully";
            }
            return "the doctor is already busy at this timeslot";
        }

        String cancelAppointment(int docID, int timeSlot, String patientName) {
            if (docID >= doctors.size())
                return "the doctor id is not found in hospital";
            Doctor doctor = doctors.get(docID);
            if (doctor.timeSlots.length <= timeSlot)
                return "the timeslot index is out of boundary";
            if (doctor.timeSlots[timeSlot] && !doctor.patients[timeSlot].equals(patientName))
                return "the doctor has an appointment to a different patient name at this timeslot";
            if (doctor.timeSlots[timeSlot]) {
                doctor.timeSlots[timeSlot] = false;
                doctor.patients[timeSlot] = null;
                return "Cancelling the appointment is done successfully";
            }
            return "the doctor doesn’t have an appointment at this timeslot";
        }

        void print() {
            for (Doctor doctor : doctors) {
                System.out.println("ID: " + doctor.ID);
                for (int i = 0; i < doctor.timeSlots.length; i++) {
                    System.out.println(doctor.timeSlots[i] + " : " + doctor.patients[i]);
                }
            }
        }
    }
}
