package ZhengZe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//获取当前页面里有多少个最小图片组
public class TTT {
    private static final String url = "http://www.jj20.com/bz/qcjt/list_5_39.html";
    public static void main(String[] args) throws IOException {
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        StringBuffer sb = new StringBuffer();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, "gb2312"));
        }
        in.close();
        String s = sb.toString();
        Matcher matcher = Pattern.compile("(?<grp0>.+?_blank[^<]+)").matcher(s);
        //匹配进分类图片的组的内部，得到组的url
        while (matcher.find()){
            String[] url1 = matcher.group().split("\"");
            System.out.println(url1[1]);
        }

    }
}
