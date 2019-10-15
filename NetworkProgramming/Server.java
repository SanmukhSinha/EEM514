import java.net.*;
import java.io.*;

class ServerThread extends Thread {

    Socket socket = null;
    BufferedReader reader = null;

    public ServerThread(Socket insocket) {
        socket = insocket;
    }

    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "", str = "";

            while ((str = reader.readLine()) != null) {
                int vowel = 0, cons = 0, req = 0;
                Thread.sleep(1000);// for 1 sec delay
                
                line = str.replaceAll("\\s+", "");
                for (int i = 0; i < line.length(); i++) {
                    char ch = Character.toLowerCase(line.charAt(i));
                    if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                        vowel++;
                    } else {
                        cons++;
                    }
                }

                System.out.println("\n" + str);
                System.out.println("Vowels = " + vowel);
                System.out.println("Consonants = " + cons);
            }

            socket.close();
            reader.close();
        } catch (Exception e) {
        }
    }
}

public class Server {

    Socket socket = null;
    ServerSocket server = null;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started...");

            while (true) {
                socket = server.accept();
                ServerThread sThread = new ServerThread(socket);
                sThread.start();
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {

        int port = 9999;
        Server s = new Server(port);
    }
}