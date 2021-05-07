package Pa;

import java.io.IOException;

public class PaTest {
    public static void main(String[] args) throws IOException {
        String url = "http://pic.netbian.com/shoujibizhi/index.html";
        Padownload.download(url,1);
        for (int i = 2; i < 202+1; i++) {
            url="http://pic.netbian.com/shoujibizhi/index_"+i+".html";
            Padownload.download(url,i);
        }
    }
}
