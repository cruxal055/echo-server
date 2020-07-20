import java.io.PrintWriter;
import java.util.Scanner;

public class execute implements Runnable
{

    private PrintWriter out;
    private Scanner in;

    public execute(PrintWriter out, Scanner in)
    {
        this.out = out;
        this.in = in;
    }

    public void run()
    {
        String data;
        while(true)
        {
            data = in.nextLine();
            System.out.println("I received: " + data);
            out.write(("You sent: " + data + '\n'));
            out.flush();
            System.out.println("echoed back a response\n");
        }
    }
}
