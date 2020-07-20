import sun.misc.Signal;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.Scanner;

import static java.lang.System.exit;

public class echoClient
{
    private Socket client;
    public echoClient() throws IOException
    {
        client = new Socket("127.0.0.1", 20001);
        PrintWriter writer = new PrintWriter(client.getOutputStream());
        BufferedReader retrieve = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Scanner cin = new Scanner(System.in);
        Signal.handle(new Signal("INT"), signal ->
        {
            System.out.println("Terminating Client..");
            cin.close();
            writer.close();
            try
            {
                retrieve.close();
                client.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("sucuessfully terminated");
            exit(1);
        } );

        String input, response;
        while(true)
        {
            System.out.println("what do you want to send to the server?");
            input = cin.nextLine();
            System.out.println("Sending: " + input);
            writer.write((input + '\n'));
            writer.flush();
            response = retrieve.readLine();
            System.out.println("server responded with: " + response);
        }
    }
    public static void main(String args[]) throws IOException
    {
        System.out.println("starting up the client!");
        echoClient client = new echoClient();

    }
}
