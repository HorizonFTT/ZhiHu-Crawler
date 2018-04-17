package Assembly;

import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;

/**
 * MyDownloader
 */
public class MyDownloader extends HttpClientDownloader {

    private MySpider sp = null;

    public MyDownloader(MySpider sp) {
        this.sp = sp;
    }

    @Override
    public Page download(Request request, Task task) {
        sp.setSite();
        var cookie=sp.getSite().getCookies();
        request.addCookie("z_c0",cookie.get("z_c0"));
        return super.download(request, task);
    }

}