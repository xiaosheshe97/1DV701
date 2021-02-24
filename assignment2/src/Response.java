import java.io.*;
import java.net.Socket;

public class Response {
    String uri;
    String path = System.getProperty("user.dir") + File.separator+ "resources";
    String contentType;

    public Response(String uri) {
        this.uri = uri;
    }


    /**
     * check content type for http
     * @param s
     */
    public void checkContentType(String s) {
        if (s.endsWith(".html"))
            contentType = "text/html";
        if (s.endsWith(".png"))
            contentType = "image/png";
    }

    /**
     * read from disk and write into output
     * @param socket
     */
    public void sendResponse(Socket socket, String resource) throws IOException {
        checkContentType(uri);
        System.out.println(uri);
        String filePath = resource + File.separator + uri;
       // File file = new File(filePath);
        PrintStream printStream;
       // if (file.exists()){
            System.out.println(filePath);
        printStream = new PrintStream(socket.getOutputStream());
            try {
                File file = new File(filePath);

                //set http header
                printStream.println("HTTP/1.1 200 OK");

                printStream.println("Content-Type:"+contentType);
                printStream.println();
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                printStream.write(bytes);
                printStream.flush();
            }catch(FileNotFoundException ex){
                printStream.println(("404 Not Found"));
            }
            //catch (IOException e) {
              //  e.printStackTrace();

       // }
    }
}
