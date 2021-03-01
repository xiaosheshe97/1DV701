import java.net.Socket;

public class UploadServer {
    Socket socket;
    String request;

    public UploadServer(Socket socket, String request) {
        this.socket = socket;
        this.request = request;
    }

    public void upload(){
        System.out.println("1");
        if(request.contains("Content-Disposition:") ){
            System.out.println(2);
            if (request.contains("Content-Type:")){
                System.out.println(3);
                int index1 = request.indexOf("Content-Disposition:");
                int index2 = request.indexOf("Content-Type:",index1);
                System.out.println(index1);
                System.out.println(index2);
                System.out.println(request.indexOf("1.b.png"));
                System.out.println(request.substring(index1,index2));
            }
            //String root = request.getRealPath("/upload");

        }
    }
}
