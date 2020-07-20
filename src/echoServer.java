import sun.misc.Signal;

import java.io.*;
import java.net.*;
import java.rmi.ServerError;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class echoServer
{
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    static int defaultPort = 20000;
    private int terminate;

    private void init() throws  IOException
    {
        socket = serverSocket.accept(); //suspends the process until something can be read from the port;
        System.out.println("server recieved a connection!");
        out = new PrintWriter(socket.getOutputStream()); //clientSock.getOutputStream gives us the FD of the client
        in = new Scanner(socket.getInputStream());
        terminate = 0;
    }

    public echoServer(int port) throws IOException,  InterruptedException
    {
        serverSocket = new ServerSocket(port);
        init();
        performTasks();
    }


   private void performTasks() throws IOException, InterruptedException
   {
       while(true)
       {
           socket = serverSocket.accept();
           System.out.println("server recieved a connection!");
           out = new PrintWriter(socket.getOutputStream()); //clientSock.getOutputStream gives us the FD of the client
           in = new Scanner(socket.getInputStream());
           Thread temp = new Thread(new execute(out, in));
           temp.start();
       }


   }
   public static void main(String args[]) throws IOException,  InterruptedException
   {
       System.out.println("hello to the world");
       echoServer oof = new echoServer(20001);
   }

}
