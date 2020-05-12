package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static ServerSocket ss;

    public static ServerSocket getServerSocket()
    {
        return ss;
    }

    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                ObjectInputStream ois = new ObjectInputStream(dis);
                ObjectOutputStream oos = new ObjectOutputStream(dos);

                System.out.println("Assigning new thread for this client");
                Runnable runnable = new ClientHandler(s, dis, dos, ois, oos);
                // create a new thread object
                Thread t = new Thread(runnable);

                // Invoking the start() method
                t.start();
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

}
