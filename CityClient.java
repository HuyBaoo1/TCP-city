package main;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Set;

public class CityClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress address = InetAddress.getByName("localhost");

            while (true) {
                System.out.println("Choose method: 1 - Add Inhabitant, 2 - Get All Dates of Birth, 3 - Get Marital Status");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Message message = null;
                if (choice == 1) {
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter date of birth: ");
                    String DoB = scanner.nextLine();
                    System.out.println("Enter marital status: ");
                    String MS = scanner.nextLine();

                    message = new Message("addInhabitant", name, DoB, MS);

                } else if (choice == 2) {
                    message = new Message("getAllDoBs");

                } else if (choice == 3) {
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    message = new Message("getMaritalStatus", name);

                } else {
                    System.out.println("Invalid choice");
                    continue;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(message);
                byte[] sendData = baos.toByteArray();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 12345);
                socket.send(sendPacket);

                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Object response = ois.readObject();

                if (response instanceof Set) {
                    Set<String> dobs = (Set<String>) response;
                    System.out.println("Dates of Birth: " + dobs);
                } else {
                    System.out.println("Response: " + response);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
