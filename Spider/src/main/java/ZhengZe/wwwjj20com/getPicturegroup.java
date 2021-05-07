package ZhengZe.wwwjj20com;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getPicturegroup {
    public static List<String> getPagepicturegroup(String url) throws IOException {
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
        List<String> PictureGropu_all_url = new ArrayList<String>();
        //匹配进分类图片的组的内部，得到组的url
        while (matcher.find()){
            String[] url1 = matcher.group().split("\"");
            String group="http://www.jj20.com"+url1[1];
            PictureGropu_all_url.add(group);
        }
        return PictureGropu_all_url;
    }
}
