import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

  /*  public void checkIfFileAbsolutPathAndCanonicalPathAreSame(String filePath) throws IOException {
       // checkContentType(uri);
        //System.out.println(uri);
        //String filePath = resource + File.separator + uri;
        File file = new File(filePath);

        String fileAbsolutePath = file.getAbsolutePath();
        String fileCanonicalPath = file.getCanonicalPath();
        String difference = fileCanonicalPath.substring(0,fileAbsolutePath.length());
        if(!difference.equals(fileAbsolutePath))
            throw new SecurityException("Risk of security leak");

    }*/

    /**
     * read from disk and write into output
     * @param socket
     */
    public void sendResponse(Socket socket, String resource) throws IOException {
        checkContentType(uri);
        System.out.println(uri);
        System.out.println(contentType);
       // socket.setSoTimeout(2000);
        String filePath = resource + File.separator+ uri;
        Path newPath = Paths.get(filePath);
        //System.out.println(filePath);
       // File file = new File(filePath);
        PrintStream printStream;
       // if (file.exists()){

            System.out.println(filePath);
        printStream = new PrintStream(socket.getOutputStream());
            try {
                File file = new File(filePath);
              //  checkIfFileAbsolutPathAndCanonicalPathAreSame(filePath);
                if(!file.exists()){
                    throw new FileNotFoundException();

                }
              /*  else if(Files.exists(newPath)){
                    boolean error403=false;
                    String[] s = filePath.split("[\\W]");
                    for(String a :s){
                        System.out.println("string    "+a);
                        if(a.equals("noAccess"))
                            error403=true;
                    }
                   if(error403)
                        throw new SecurityException();
                }*/
                /*else if(!Files.isReadable(newPath)) {
                    System.out.println("can not be read");
                    throw new SecurityException();
                }*/else{
                     // System.out.println(file.getAbsolutePath());
                    //System.out.println(file.getCanonicalPath());

                    boolean changedDirectory = false;
                    System.out.println(file.getAbsolutePath());

                    String[] s = filePath.split("[\\W]");
                    for(String a :s){
                        if(a.equals("noAccess"))
                            throw new SecurityException();
                        if (a.equals("oldDirectory")){
                            changedDirectory = true;
                        }
                    }

                    if (changedDirectory){
                        //set http header
                        InetAddress ip = InetAddress.getLocalHost();
                        uri = uri.replaceAll("old", "new");
                        printStream.println("HTTP/1.1 302 Found");
                        printStream.println("Location: http://" +ip.getHostAddress() +":" + socket.getLocalPort() +"/"+ uri);
                        printStream.println("Content-Type:" + contentType);
                        printStream.println();
                        printStream.flush();
                    }
                    else {
                        //set http header
                        printStream.println("HTTP/1.1 200 Ok");
                        System.out.println("here");

                        printStream.println("Content-Type:" + contentType);
                        printStream.println();
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[fileInputStream.available()];
                        fileInputStream.read(bytes);
                        printStream.write(bytes);
                        printStream.flush();
                    }

                }
            }catch(FileNotFoundException ex){
                String content = "<html><body><h1>404 Not Found</h1></body></html>";
                System.out.println("kkkk");
                String header = "HTTP/1.1 404 Not Found";
                printStream.println(header);
                printStream.println("Content-Type:"+contentType);
                printStream.println();
                printStream.write(content.getBytes());
                printStream.flush();
            }catch(SecurityException ex){
                String content = "<html><body><h1>403 Forbidden</h1></body></html>";
                String header = "HTTP/1.1 403 Forbidden";
                System.out.println("really?");
                printStream.println(header);
                printStream.println("Content-Type:"+contentType);
                printStream.println();
                printStream.write(content.getBytes());
                printStream.flush();
            }
            //catch (IOException e) {
              //  e.printStackTrace();

       // }
    }
}
