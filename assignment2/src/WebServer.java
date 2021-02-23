import java.net.*;
import java.io.*;
import java.util.*;

class WebServer {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client;
        int port = 8888;
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid port number");
            System.exit(1);
        }
        System.out.println("Webserver is ready waiting for connection");
        while (true) {
            try {
                //wait for connection
                client = server.accept();
                System.out.println("connect to client");
                new Thread(new ClientThread(client)).run();

            } catch (IOException e) {
                System.out.println("cannot connect to client");
            }
        }
    }
}

/**
 * Thread class to handle client
 */
class ClientThread implements Runnable{
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //read from client
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();

            StringTokenizer st = new StringTokenizer(s);
            //this will return GET
            st.nextToken();
            String fileName = st.nextToken().substring(1);
            System.out.println("Filename -> " + fileName);

            Response response = new Response(fileName);
            response.sendResponse(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

