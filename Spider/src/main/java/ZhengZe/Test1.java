package ZhengZe;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {
    /**
     * @param s
     * @return 获得图片
     */
    public static List<String> getImg(String s)
    {
        String regex;
        List<String> list = new ArrayList<String>();
        regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        return list;
    }
    /**
     * 返回存有图片地址的数组
     * @param tar
     * @return
     */
    public static String[] getImgaddress(String tar){
        List<String> imgList = getImg(tar);

        String res[] = new String[imgList.size()];

        if(imgList.size()>0){
            for (int i = 0; i < imgList.size(); i++) {
                int begin = imgList.get(i).indexOf("\"")+1;
                int end = imgList.get(i).lastIndexOf("\"");
                String url[] = imgList.get(i).substring(begin,end).split("/");
                res[i]=url[url.length-1];
            }
        }else{
        }
        return res;
    }
}
