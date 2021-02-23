import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
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
    public void sendResponse(Socket socket){
        checkContentType(uri);
        System.out.println(uri);
        String filePath = path + File.separator + uri;
        File file = new File(filePath);

        if (file.exists()){
            System.out.println(filePath);

            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                //set http header
                printStream.println("Http/1.1 200 OK");

                printStream.println(contentType);
                printStream.println();
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                printStream.write(bytes);
                printStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
