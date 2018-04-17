package Assembly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * MySpider
 */
public class MySpider extends Spider {

    private Random random = new Random();

    private int flag = -1;

    private List<String> cookieList = new ArrayList<>();

    public MySpider(PageProcessor pageProcessor) {
        super(pageProcessor);
        site = pageProcessor.getSite();

        var file = new File("cookies.txt");
        try {
            var bReader = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp = bReader.readLine()) != null) {
                temp = temp.replace("\"", "");
                var index = temp.indexOf("=");
                cookieList.add(temp.substring(index + 1));
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSite() {
        site.setSleepTime(1000 + random.nextInt(2000));
        int num = 100;
        if (++flag % num == 0) {
            int position = flag / num;
            if (position == cookieList.size() - 1) {
                flag = -num;
            }
            site.addCookie("z_c0", cookieList.get(position));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}