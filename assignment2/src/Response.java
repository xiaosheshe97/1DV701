import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Response {
    String uri;
    String path = System.getProperty("user.dir") + File.separator+ "resources";

    public Response(String uri) {
        this.uri = uri;
    }

    public void sendResponse(Socket socket){
        String filePath = path + File.separator + uri;
        File file = new File(filePath);

        if (file.exists()){
            System.out.println(filePath);

            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                //set http header
                printStream.println("Http/1.1 200 OK");
                printStream.println("text/html;");
                printStream.println();
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                printStream.write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
