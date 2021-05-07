package BBB;

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
        String url0="http://www.jj20.com/bz/qcjt/list_5_11.html";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet1 = new HttpGet(url0);
        // 执行get请求
        CloseableHttpResponse response = client.execute(httpGet1);
        HttpEntity entity = response.getEntity();
        // 获取返回实体
        String content = EntityUtils.toString(entity, "gb2312");
        Document doc = Jsoup.parse(content);
//        System.out.println(doc.toString());picbz
        Elements elements1 = doc.select("div [class=g-box-1200]");
        System.out.println("**********");
        System.out.println(elements1.toString());
//        System.out.println("-------------");
//        String bigurl2 = elements1.select("img").first().attr("src");
//        System.out.println(bigurl2);
    }
    public void getpicurl(){

    }
}
