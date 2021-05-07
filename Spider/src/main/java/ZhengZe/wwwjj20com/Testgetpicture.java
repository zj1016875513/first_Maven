package ZhengZe.wwwjj20com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testgetpicture {
    private static String index = "http://www.jj20.com/bz/zrfg/";
    private static String tag = "list_1_";
    private static int k = 91;
    private static String src = "E:\\aaa\\娟娟壁纸\\风景\\";

    public static void main(String[] args) throws IOException {
        List<String> page_all = getPage(index, k);
        List<String> group_all = getPagepicturegroup(page_all);
        List<String> pictureHighturl_all=getPictureHighturl(group_all);
        download(pictureHighturl_all,src);
        System.out.println("总共解析了"+pictureHighturl_all.size()+"张图片");

    }
    //计算每页的地址
    private static List<String> getPage(String index, int k) {
        List Page = new ArrayList<String>();
        String FirstPage = index + ".html";
        Page.add(index);
        for (int i = 2; i <= k; i++) {
            String From_two_page = index + tag + i + ".html";
            Page.add(From_two_page);
        }
        return Page;
    }


    public static List<String> getPagepicturegroup(List<String> page_all) throws IOException {
        List<String> PictureGropu_all_url = new ArrayList<String>();
        for (String url:page_all) {
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
            while (matcher.find()) {
                String[] url1 = matcher.group().split("\"");
                String group = "http://www.jj20.com" + url1[1];
                PictureGropu_all_url.add(group);
            }
        }
        return PictureGropu_all_url;
    }

    public static List<String> getPictureHighturl(List<String> group_all){
        List<String> HighPictureurl = new ArrayList<String>();
        for (String url : group_all) {
            try {
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
                Matcher matcher = Pattern.compile("(?<grp0>[^ ]+ id=\"img.*?\"[^<]+<img src=[^<]+)").matcher(s);
//        //匹配进分类图片的组的内部，得到组的url
                while (matcher.find()) {
                    String[] split = matcher.group().split("src=\"");
                    String[] url0 = split[1].split("\"");
                    HighPictureurl.add(url0[0].replace("-lp", ""));
                }
            }catch (IOException e){
                continue;
            }
        }
        return HighPictureurl;
    }

    public static void download(List<String> pictureurl,String filesrc) {
        int i=1;
        for (String src : pictureurl) {
            try {
                String finally_file_src=filesrc +"Fengjing"+i+".jpg";
                URL url = new URL(src);
                InputStream is = url.openStream();
                FileOutputStream fos = new FileOutputStream(finally_file_src);
                byte buf[] = new byte[1024];
                int length = 0;
                while ((length = is.read(buf)) != -1) {
                    fos.write(buf, 0, length);
                }
                fos.close();
                is.close();
                System.out.print("("+i+"/"+pictureurl.size()+")");
                System.out.println(i + ".jpg" + "下载成功");
            } catch (Exception e) {
                System.out.println(i + ".jpg" + "下载失败");
                continue;
            }
            i++;
        }
    }
}

