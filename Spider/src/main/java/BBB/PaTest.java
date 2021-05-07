package BBB;

import java.io.IOException;

public class PaTest {
    public static void main(String[] args) throws IOException {
        String url = "";
        for (int i = 1; i < 172+1; i++) {
            Padownload.download(url,i);
            url="http://pic.netbian.com/4kmeinv/index_"+i+".html";
        }
    }
}
