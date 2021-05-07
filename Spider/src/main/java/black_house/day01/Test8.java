package black_house.day01;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class Test8 {
    @Test
    public void testJsoupUrl() throws Exception {
        //    解析url地址
        Document document = Jsoup.parse(new URL("http://www.itcast.cn/"), 1000);

        //获取title的内容
        Element title = document.getElementsByTag("title").first();
        System.out.println(title.text());
    }

    @Test
    public void testJsoupString() throws Exception {
        //读取文件获取
        String html = FileUtils.readFileToString(new File("D:\\jsoup.html"), "UTF-8");

        //    解析字符串
        Document document = Jsoup.parse(html);

        //获取title的内容
        Element title = document.getElementsByTag("title").first();
        System.out.println(title.text());
    }

    @Test
    public void testJsoupHtml() throws Exception {
        //    解析文件
        Document document = Jsoup.parse(new File("D:\\jsoup.html"),"UTF-8");

        //获取title的内容
        Element title = document.getElementsByTag("title").first();
        System.out.println(title.text());
    }





}
