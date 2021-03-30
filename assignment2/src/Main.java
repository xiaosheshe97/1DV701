import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("/Users/xiaohezhu/Desktop/1dv701/a2/1DV701/Apples.png");
        FileInputStream in = new FileInputStream(f);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
        int ch = 0;

while((ch = isr.read())!=-1) {
System.out.print((char)ch);
}


    }
}
