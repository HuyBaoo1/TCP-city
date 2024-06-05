package main;

import java.io.*;
import java.util.Map;
import java.net.*;
import java.util.HashMap;

public class CityServer {
    private static Map<String, City> cities = new HashMap<>();

    public static void main(String[] args) {
    	cities.put("Frankfurt",new City("Frankfurt"));
    	cities.put("Berlin",new City("Berlin"));
    	//create socket with port 12345
        try (DatagramSocket socket = new DatagramSocket(3344)) {
            byte[] buffer = new byte[1024];
            
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Message message = (Message) ois.readObject();
                
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

            e.printStackTrace();
        }
    }
    //Check the city's name
    private static Object handleMessage(Message message) {
    	String cityName = message.getcityName();
    	City city = cities.get(cityName);
    	if(city == null) {
    		return "City not found in our map";
    	}
    	
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
