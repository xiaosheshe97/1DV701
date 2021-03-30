import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.UUID;

public class UploadServer {
    Socket socket;
    String request;

    public UploadServer(Socket socket, String request) {
        this.socket = socket;
        this.request = request;
    }

    public void upload() throws IOException {
//        URL url = new URL(request);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("POST");
       // System.out.println("1");
        System.out.println(" request is ==>>>>>" +request);
        System.out.println("request finished here");
        if(request.contains("Content-Disposition:") ){
            System.out.println(2);
            if (request.contains("Content-Type:")){
                System.out.println(3);
                int index1 = request.indexOf("Content-Disposition:");

                int index2 = request.indexOf("filename=",index1);
                int index3 = request.indexOf("Content-Type:",index1);
                String fileName = request.substring(index2+10,index3-3).trim();
                System.out.println("file name is hereeeeeeeeeeeee ==>" +fileName);
                String con = request.substring(index3+23);

               // File uploadFile = new File("/Users/xiaohezhu/Desktop/1dv701/a2/1DV701","Apples.png" );

                File file = new File(fileName);
        PrintStream printStream= new PrintStream(new FileOutputStream(file));
        printStream.println("HTTP/1.1 200 Ok");
                byte[] buffer = new byte[1024 * 1024];
               int len;
        InputStream inputStream = socket.getInputStream();
                while((len = inputStream.read(buffer)) != -1){
                            printStream.write(buffer,0,len);
                        }
                        inputStream.close();
                printStream.flush();
                printStream.close();

//                String boundary =  UUID.randomUUID().toString();  //通过UUID生成唯一标识符
//
//                String PREFIX = "--";
//                String LINE_END = "\r\n";
//
//                try{
//                    URL url1 = new URL(request);
//                    HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
////                    conn.setReadTimeout(TIME_OUT);
////                    conn.setConnectTimeout(TIME_OUT);
////                    conn.setDoInput(true);
////                    conn.setDoOutput(true);
//                    conn.getContent();
//                    //conn.setRequestMethod("POST");//设置请求方式为POST
////                    conn.setRequestProperty("Charset", "utf-8");//设置编码方式
////                    conn.setRequestProperty("connection", "keep-alive");//设置持久连接
////                    conn.setRequestProperty("Content-Type","multipart/form-data" + ";boundary =" + boundary);//这里就是在设置上文中说到的Content-Type请求头
//                    conn.connect();
//
//                    if(file != null){
//                        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//                        StringBuffer sb = new StringBuffer();
//
//                        /**
//                         * 这里开始设置实体内容的格式，首先设置分隔符
//                         */
//                        sb.append(PREFIX);
//                        sb.append(boundary);
//                        sb.append(LINE_END);
//
//                        /**
//                         * 设置Content-Disposition和Content-Type请求头，application/octet-stream是说如果不知道文件的类型，则以二进制流的形式上传下载。
//                         * 注意回车换行也是必须与报文一致的
//                         */
//                        sb.append("Content-Disposition: form-data; name=\"image\"; filename=\""+file.getName()+"\""+LINE_END);
//                        sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);
//                        sb.append(LINE_END);
//
//                        dos.write(sb.toString().getBytes());
//
//                        /**
//                         * 写入文件的二进制数据
//                         */
//                        InputStream in = new FileInputStream(file);
//                        byte[] buf = new byte[1024];
//                        int len = -1;
//                        while((len = in.read(buf)) != -1){
//                            dos.write(buf,0,len);
//                        }
//                        in.close();
//                        dos.write(LINE_END.getBytes());//在结束符之前，需要再添加一个回车换行
//                        /**
//                         * 在boundary后面加上--构成标识符，表示实体内容部分结束了
//                         */
//                        dos.write((PREFIX + boundary + PREFIX + LINE_END).getBytes());
//                        dos.close();
//                    }
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//                InputStream in = new FileInputStream(uploadFile);
//                OutputStream out = new FileOutputStream(file);
//                byte[] buffer = new byte[1024 * 1024];
//                int length;
//                while ((length = in.read(buffer)) > 0) {
//                    out.write(buffer, 0, length);
//                }
//                in.close();
//                out.close();
//                FileOutputStream fop = new FileOutputStream(file);
//                byte[] contentInBytes = con.getBytes();
//
//                fop.write(contentInBytes);
//                fop.flush();
//                fop.close();
//                File uploadFile = new File("/Users/xiaohezhu/Desktop/1dv701/a2/1DV701/assignment2/resources", fileName);
//                FileWriter fileWritter = new FileWriter(uploadFile.getName(),true);
//                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//                bufferWritter.write();
//                bufferWritter.close();
//                byte[] bytes = new byte[0];
//                bytes = new byte[inputStream.available()];
//                inputStream.read(bytes);
//                String str = new String(bytes);
            }
            //String root = request.getRealPath("/upload");

        }
    }
}
