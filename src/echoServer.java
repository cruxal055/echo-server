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
    public echoServer() throws IOException,  InterruptedException
    {
        serverSocket = new ServerSocket(20000);
        init();
        performTasks();
    }

    public echoServer(int port) throws IOException,  InterruptedException
    {
        serverSocket = new ServerSocket(port);
        init();
        performTasks();
    }

    private void closeFDs() throws IOException
    {
        serverSocket.close();
        socket.close();
        out.close();
        in.close();
    }

   private void performTasks() throws IOException, InterruptedException
   {

       Signal.handle(new Signal("INT"), signal ->
       {
           System.out.println("Terminating Client..");
           try
           {
               serverSocket.close();
               socket.close();

           }
           catch (IOException e)
           {
               e.printStackTrace();
           }
           out.close();
           in.close();
           exit(1);
       } );
       String data;
       while(true && terminate == 0)
       {
           data = in.nextLine();
           System.out.println("I received: " + data);
           out.write(("You sent: " + data + '\n'));
           out.flush();
           System.out.println("echoed back a response\n");
       }
       closeFDs();
   }
   public static void main(String args[]) throws IOException,  InterruptedException
   {
       System.out.println("hello to the world");
       echoServer oof = new echoServer(20001);
   }

}
