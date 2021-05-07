package ZhengZe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//通过正则表达式来过滤出图片的地址
public class T1 {
    private static final String url = "https://www.ivsky.com/bizhi/tower_bridge_v58567/";
    public static void main(String[] args) throws IOException {
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        int length = 0;
        StringBuffer sb = new StringBuffer();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, "utf-8"));
        }
        in.close();

        String s = sb.toString();

//        Document doc = Jsoup.parse(s);
//        System.out.println(doc.toString());
//        Elements elements = doc.select("il_img .");

//        System.out.println(elements.toString()); //  <a.*href=(.*)>  //  <img.*src=(.*?)[^>]*?>  <a.*href=(.*?)[^>]*?blank
//<div class="il_img".*?</div>
        Matcher matcher = Pattern.compile("<div class=.il_img.*?</div>").matcher(s);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        for (String s1 : listImgUrl) {
            String[] s2 = s1.split("\"");
//            System.out.println(Arrays.toString(s2));
            System.out.println("-----------");
            System.out.println(s2[3]);
            System.out.println("**********");
        }

//        List<String> listImgSrc = new ArrayList<String>();
//        for (String image : listImgUrl) {
//            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
//            while (matcher.find()) {
//                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
//            }
//        }
    }
}
