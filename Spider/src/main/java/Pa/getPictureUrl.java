package Pa;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class getPictureUrl {

    public static void main(String[] args) throws IOException {
        String big0="http://pic.netbian.com/tupian/21953.html";
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
    }
    public void getpicurl(){

    }
}
