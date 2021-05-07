package ZhengZe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTT1 {
    //获取当前页的每个最小图片组有多少个子图
    private static final String url = "http://www.jj20.com/bz/qcjt/qcpp/8905.html";

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
        String s = sb.toString();//(?<grp0>.+?\(1/.{1,3}\))   (?<grp0>[^ ]+ id="img.{1,3}"[^<]+show1[^<]+)
        Matcher matcher = Pattern.compile("(?<grp0>.+?\\(1/.{1,3}\\))").matcher(s);
        //匹配进分类图片的组的内部，得到组的url
        while (matcher.find()){
//            System.out.println(matcher.group());
            String[] split = matcher.group().split("/");
            String num0 = split[split.length - 1];
            String num = num0.substring(0, num0.length() - 1);
            System.out.println(num);
        }

    }
}
