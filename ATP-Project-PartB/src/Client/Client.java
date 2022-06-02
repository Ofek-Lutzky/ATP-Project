package Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private int serverPortNumber;
    private InetAddress serverIpAddress;
    private IClientStrategy strategy;

    public Client(InetAddress serverIpAddress, int serverPortNumber , IClientStrategy strategy) {
        this.serverPortNumber = serverPortNumber;
        this.serverIpAddress = serverIpAddress;
        this.strategy = strategy;
    }


    public void communicateWithServer(){
        try(Socket serverSocket = new Socket(serverIpAddress,serverPortNumber)) {
            System.out.println("connected to server - IP = " + serverIpAddress + ", Port = " + serverPortNumber);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
