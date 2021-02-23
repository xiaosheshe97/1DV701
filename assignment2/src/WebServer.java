import java.net.*;
import java.io.*;
import java.util.*;

class WebServer {
    public static void main(String[] args) {
        if(args.length !=2){
            System.out.println("For running this app you need to have 2 arguments");
            System.exit(-1);
        }

        ServerSocket server = null;
        Socket client;
        String resourceRelativePath = args[1];

        try {
            int port = Integer.valueOf(args[0]);
            server = new ServerSocket(port);
        }catch(NumberFormatException ex){
            System.out.println("first argument should be integer");
            System.exit(-1);
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
                new Thread(new ClientThread(client,resourceRelativePath)).run();

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
    String resource ;

    public ClientThread(Socket socket, String resource) {

        this.socket = socket;
        this.resource = resource;
    }

    @Override
    public void run() {
        try {
            //read from client
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();

            System.out.println(s);
            StringTokenizer st = new StringTokenizer(s);
            //this will return GET
            st.nextToken();

            String s1 = checkReuest(st.nextToken());
            String fileName = s1.substring(1);
            System.out.println("Filename -> " + fileName );


            Response response = new Response(fileName);
            response.sendResponse(socket, resource);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * check the request
     * ending /is optional
     * directory contains index.html file, then this file should be returned
     * @param s
     * @return string
     */
    public String checkReuest(String s){
        if (s.endsWith("/"))
            s = s.substring(0,s.length()-1);
        if (s.equals("/resources"))
            s = "/index.html";

        return s;
    }
}

