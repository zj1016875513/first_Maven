package A;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
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

public class Test2 {

    public static void main(String[] args) throws ClientProtocolException, IOException {

        // 创建httpclient实例
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpget实例
        HttpGet httpget = new HttpGet("http://www.daimg.com/photo/tour/");

        // 执行get请求
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        // 获取返回实体
        String content = EntityUtils.toString(entity, "utf-8");

        // 解析网页 得到文档对象
        Document doc = Jsoup.parse(content);
        // 获取指定的 <img />
        Elements elements = doc.select(".img_box img");

        for (int i = 0; i < 15; i++) {
            Element element = elements.get(i);
            // 获取 <img /> 的 src
            String url = element.attr("src");

            // 再发请求最简单了，并由于该链接是没有 https:开头的，得人工补全 ✔
            HttpGet PicturehttpGet = new HttpGet(url);
            System.out.println("asdasd:"+PicturehttpGet);
            CloseableHttpResponse pictureResponse = httpclient.execute(PicturehttpGet);
            HttpEntity pictureEntity = pictureResponse.getEntity();
            InputStream inputStream = pictureEntity.getContent();

            // 使用 common-io 下载图片到本地，注意图片名不能重复 ✔
            FileUtils.copyToFile(inputStream, new File("E://aaa//cc//" + i + ".jpg"));
            pictureResponse.close(); // pictureResponse关闭

        }

        response.close(); // response关闭
        httpclient.close(); // httpClient关闭

    }

}