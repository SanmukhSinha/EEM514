import java.net.*;
import java.io.*;

public class Client {

    Socket socket = null;
    BufferedReader reader = null;
    // BufferedWriter writer = null;
    PrintWriter writer = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String line = "";
            int req = 0;
            try {
                System.out.println("Enter Number of requests(N):");
                line = reader.readLine();
                // writer.write(line);
                // writer.newLine();
                req = Integer.parseInt(line);
                System.out.println("Enter words:");
                while (req > 0) {
                    req--;
                    line = reader.readLine();
                    writer.println(line);
                }
                // writer.newLine();
            } catch (IOException e) {
            }

            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 9999);
    }
}