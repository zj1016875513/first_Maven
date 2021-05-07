package ZhengZe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//得到每张图的高清url
public class TTT2 {
    private static final String url = "http://www.jj20.com/bz/nxxz/nxmt/289096.html";

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
//        System.out.println(s);
        Matcher matcher = Pattern.compile("(?<grp0>[^ ]+ id=\"img.*?\"[^<]+<img src=[^<]+)").matcher(s);
//        //匹配进分类图片的组的内部，得到组的url
        while (matcher.find()){
//            System.out.println(matcher.group());
//            System.out.println("00000000000000");

            String[] split = matcher.group().split("src=\"");
            String[] url0 = split[1].split("\"");
            System.out.println(url0[0].replace("-lp",""));

//            String[] split = matcher.group().split("/");
//            String num0 = split[split.length - 1];
//            String num = num0.substring(0, num0.length() - 1);
//            System.out.println(num);
        }

    }
}
