package cece;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ccc {
    private static String url = "";
    private static String tag = "list_1_";
    private static int k = 91;
    private static String src = "E:\\aaa\\娟娟壁纸\\风景\\";

    public static void main(String[] args) {
        cece();
    }
    public static void cece(){
        try {
            URL uri = new URL(ccc.url);
            URLConnection urlConnection = uri.openConnection();
            InputStream in = urlConnection.getInputStream();
            byte[] by=new byte[1024];
            int changdu=0;
            StringBuffer str = new StringBuffer();
            while ((changdu=in.read(by,0,by.length))>0){
                str.append(new String(by,"utf-8"));
            }
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
