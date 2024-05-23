package main;

import java.io.*;
import java.net.*;

public class CityServer {
    private static City city = new City("Berlin");

    public static void main(String[] args) {
    	//create socket with port 12345
        try (DatagramSocket socket = new DatagramSocket(12345)) {
        	//create buffer used to store data of received UDP packet
            byte[] buffer = new byte[1024];
            //main loop for receiving and processing packets
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                //deserialization of received data
                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                //read and deserializes the object from the stream
                Message message = (Message) ois.readObject();
                
                //handle the messagae
                Object result = handleMessage(message);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(result);
                byte[] responseBytes = baos.toByteArray();

                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (IOException | ClassNotFoundException e) {
            //print the stack trace of a throwable object
            //throwable is the superclass of all exceptions and errors in Java
            e.printStackTrace();
        }
    }

    private static Object handleMessage(Message message) {
        String methodName = message.getMethodName();
        Object[] params = message.getParameters();

        switch (methodName) {
            case "addInhabitant":
                String name = (String) params[0];
                String DoB = (String) params[1];
                String MS = (String) params[2];
                city.addInhabitant(name, DoB, MS);
                return "Inhabitant added successfully.";
            case "getAllDoBs":
                return city.getAllDoBs();
            case "getMaritalStatus":
                name = (String) params[0];
                return city.getMaritalStatus(name);
            default:
                return "Unknown method";
        }
    }
}
