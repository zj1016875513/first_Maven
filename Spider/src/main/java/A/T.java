package A;

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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class T {

    public static void main(String[] args) throws IOException {
        String url = "http://pic.netbian.com/4kfengjing/index_200.html";
        String url1 = "http://pic.netbian.com";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 执行get请求
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        // 获取返回实体
        String content = EntityUtils.toString(entity, "GBK");

//        System.out.println(content);

//        Document document = Jsoup.parseBodyFragment(content);
//        System.out.println(document.toString());
//        Elements elements = document.getElementsByTag("span");
//        for (int i = 0; i <10 ; i++) {
//
//            System.out.println(elements.get(i).text());
//            System.out.println("%%%%%%%%%%%%%%%%%"+i);
//        }

        // 解析网页 得到文档对象
        Document doc = Jsoup.parse(content);
        Elements elements = doc.select("#main .slist ul li a");
//        Elements pagenum = doc.select("span[class=text]").text();//.first().select("span.text");
//            String s = pagenum.toString();
//        String s= doc.select(".page").text();
//        Document dd =Jsoup.connect(url).get();
//        String s=doc.toString();
//            System.out.println("*******");
//            System.out.println(s);

//        ArrayList<String> picurl = new ArrayList<String>();
        int k=1;
        for (Element a : elements) {
            String big = a.attr("href");
            String small = a.select("img").first().attr("src");
            String bigurl=url1+big;
            String smallurl=url1+small;
//            picurl.add(bigurl);
            HttpGet picGet = new HttpGet(smallurl);
            CloseableHttpResponse pictureResponse = client.execute(picGet);
            HttpEntity pictureEntity = pictureResponse.getEntity();
            InputStream inputStream = pictureEntity.getContent();
            FileUtils.copyToFile(inputStream, new File("E://aaa//bb//" + k + ".jpg"));
            System.out.println("正在下载第"+k+"张图");
            pictureResponse.close(); // pictureResponse关闭
            k++;
        }
        response.close(); // response关闭
        client.close(); // httpClient关闭

    }
}
