package Assembly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Database.Database;
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

    private Database db = null;

    public MySpider(PageProcessor pageProcessor, boolean local) {
        super(pageProcessor);
        db = new Database(local);
        this.addPipeline(new DatabasePipeline(db));

        initCookies();
    }

    private void initCookies() {
        var file = new File("config/cookies.txt");
        try {
            var bReader = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp = bReader.readLine()) != null) {
                temp = temp.replace("\"", "");//删除引号
                var index = temp.indexOf("=");
                cookieList.add(temp.substring(index + 1));
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSite() {
        site.setSleepTime(2000 + random.nextInt(1000));//随机休眠时间减少被封风险
        int num = 100;
        if (++flag % num == 0) {
            int position = flag / num;
            if (position == cookieList.size() - 1) {
                flag = -num;
                db.keepAlive();
            }
            site.addCookie("z_c0", cookieList.get(position));//爬取num个页面后切换账号
            System.out.println("Account number: " + position);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void count() {
        db.num();
    }

    public void empty(boolean del) {
        if (del == true) {
            db.delete();
            try {
                var file = new File("logs/www.zhihu.com.cursor.txt");
                if (file.exists()) {
                    var temp = new File("logs/temp.txt");
                    if (!temp.exists()) {
                        file.createNewFile();
                    }
                    Files.copy(file.toPath(), temp.toPath());//清空保存爬取位置防止误删
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}