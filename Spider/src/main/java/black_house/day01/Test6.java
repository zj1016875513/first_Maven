package black_house.day01;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Test6 {
    public static void main(String[] args) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        //    设置最大连接数
        cm.setMaxTotal(200);

        //    设置每个主机的并发数
        cm.setDefaultMaxPerRoute(20);

        doGet(cm);

        doGet(cm);

    }

    private static void doGet(PoolingHttpClientConnectionManager cm) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet("http://www.itcast.cn/");

        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);

            // 判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content.length());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放连接
            if (response == null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //不能关闭HttpClient
                //httpClient.close();
            }
        }
    }

}
