import java.net.*;
import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

class WebServer {
    public static void main(String[] args) {
        if(args.length !=2){
            //first argument is port number, second argument is "resources"
            System.out.println("For running this app you need to have 2 arguments");
            System.exit(-1);
        }
        if(!new File(args[1]).exists()){
            System.out.println("wrong directory, check resource directory once again");
            System.exit(-1);
        }

        ServerSocket server = null;
        Socket client;
        String resourceRelativePath = args[1];

        try {
            int port = Integer.valueOf(args[0]);
            if(port >65535 || port <30){
                System.out.println("Enter a valid port number between 30 to 65535");
                System.exit(-1);
            }
            server = new ServerSocket(port);
        }catch(NumberFormatException ex){
            System.out.println("first argument should be integer");
            System.exit(-1);
        } catch (Exception e) {
           // e.printStackTrace();
            System.out.println("Port is taken");
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
            String s="";

            s =bufferedReader.readLine();
            StringBuffer sb = new StringBuffer();
            byte[] buf = new byte[1024];
            int len = 0;// 每次读取到的数据的长度

            sb.append(s);
            //if( s != null) {
              // String s = bufferedReader.readLine();
               System.out.println(s+"        sssssssssssssss");
               if (s.contains("GET")){
                   // if (s != null) {
                   StringTokenizer st = new StringTokenizer(s);
                   //this will return GET
                   st.nextToken();

                   String s1 = checkReuest(st.nextToken());
                   String fileName = s1.substring(1);
                   System.out.println("Filename -> " + fileName);


                   Response response = new Response(fileName);
                   response.sendResponse(socket, resource);
                   // }
               }
               else if (s.contains("POST")){
                   String r = "";
                   while ( (r =bufferedReader.readLine()) != null) {// len值为-1时，表示没有数据了
                       // append方法往sb对象里面添加数据
                       sb.append(r);
                   }
                   System.out.println(sb.toString());
                   UploadServer uploadServer = new UploadServer(socket, sb.toString());
                   uploadServer.upload();
//                   String image_path = this.getServletContext().getInitParameter("UPLOAD_IMAGE_PATH");
//                   String dir = ServletActionContext.getRequest()
//                   HttpServletRequest request = ServletActionContext.getRequest();
               }


          // }
        }catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
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
        if(new File("resources/"+s+"/index.html").exists()) {
            s = s + "/index.html";
            System.out.println("exist");
        }
        else if (s.endsWith("/"))
            s = s.substring(0,s.length()-1);
        else if (s.equals("/resources"))
            s = "/index.html";

        return s;
    }


}

