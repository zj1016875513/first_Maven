package BBB;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Padownload {
    static String url0;
    static String NO;
    @Test
    public static void download(String url0,int num) throws IOException {
//        this.url0=url0;
//        this.num=num+"";
        NO=num+"";
        String url1 = "http://pic.netbian.com";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url0);
        // 执行get请求
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        // 获取返回实体
        String content = EntityUtils.toString(entity, "GBK");
        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("#main .slist ul li a");
        int k=100;
        for (Element a : elements) {
            String big0 = a.attr("href");
            String small = a.select("img").first().attr("src");
            String big0url = url1 + big0;
            String smallurl = url1 + small;
//            System.out.println(big0url);
            String bigpicurl = big1(big0url);

            HttpGet picGet = new HttpGet(bigpicurl);
            CloseableHttpResponse pictureResponse = client.execute(picGet);
            HttpEntity pictureEntity = pictureResponse.getEntity();
            InputStream inputStream = pictureEntity.getContent();
            FileUtils.copyToFile(inputStream, new File("E://aaa//彼岸图网_美女//"+"Girl"+NO+k+".jpg"));
            System.out.println("正在下载第"+NO+"页，第"+k+"张图");
            pictureResponse.close(); // pictureResponse关闭
            k++;
        }
    }
        public static String big1 (String big0) throws IOException {
            CloseableHttpClient client1 = HttpClients.createDefault();
            HttpGet httpGet1 = new HttpGet(big0);
            // 执行get请求
            CloseableHttpResponse response1 = client1.execute(httpGet1);
            HttpEntity entity = response1.getEntity();
            // 获取返回实体
            String content1 = EntityUtils.toString(entity, "GBK");
            Document doc = Jsoup.parse(content1);
            Elements elements1 = doc.select("#main .photo-pic a");
            String bigurl2 = elements1.select("img").first().attr("src");
            String bigurl3= "http://pic.netbian.com/"+bigurl2;
            return bigurl3;
        }
    }
