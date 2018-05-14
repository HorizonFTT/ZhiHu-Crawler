package Assembly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Crawler.Main;
import Database.User;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;

/**
 * Processor
 *
 */
public class Processor implements PageProcessor {

    private final String[] sex = { "未知", "女", "男" };

    private Main program = null;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Site site = Site.me().setRetryTimes(3).setUserAgent(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
            .setCharset("UTF-8").addHeader("Host", "www.zhihu.com").setTimeOut(10000)
            .addHeader("Referer", "https://www.zhihu.com/");

    public Processor(Main program) {
        this.program = program;
    }

    private void setJsonInfo(Page page) {
        var user = new User();

        var id = page.getUrl().regex("people/(.*?)/", 1).get();
        var temp = page.getHtml().css("div#data").regex("data-state=\"(.*?)\"", 1).get();
        if (temp == null) {
            logger.info("The home page of " + id + " is not available.");
            return;// 无法获取该用户主页
        }
        temp = temp.replace("&quot;", "\"").replace("&lt;", "<").replace("&gt;", ">");// 替换转义字符
        var json = new Json(temp);
        var test = json.jsonPath("$..users['" + id + "']").get();
        if (test == null) {
            logger.info("The home page of " + id + " is not available.");
            return;// 该用户账号被封禁
        }
        var userJson = new Json(test);

        // 采用JsonPath抽取用户个人信息
        user.setId(id);
        user.setName(userJson.jsonPath("$.name").get());
        user.setSex(sex[Integer.valueOf(userJson.jsonPath("$.gender").get()) + 1]);
        user.setIntroduction(userJson.jsonPath("$.headline").get());
        user.setSchool(userJson.jsonPath("$.educations[*].school.name").get());
        user.setCompany(userJson.jsonPath("$.employments[*].company.name").get());
        user.setJob(userJson.jsonPath("$.employments[*].job.name").get());
        if (userJson.regex("\"business\":\\{").match()) {
            user.setBusiness(userJson.jsonPath("$.business.name").get());
        }
        user.setLocation(userJson.jsonPath("$.locations[*].name").get());
        user.setAnswer(userJson.jsonPath("$.answerCount").get());
        user.setAgree(userJson.jsonPath("$.voteupCount").get());
        user.setFollower(userJson.jsonPath("$.followerCount").get());

        page.putField("data", user);// 提交给Pipeline处理
    }

    private String getAPI(String url) {
        return url.replace("https://www.zhihu.com/people", "https://www.zhihu.com/api/v4/members").replace("activities",
                "followees?offset=0&limit=20");// 获取关注列表
    }

    @Override
    public void process(Page page) {
        if (page.getHtml().css("div.Unhuman").match()) {
            System.out.println("banned!!!");// 账号/ip被封,终止程序
            logger.error("Account has been banned!");
            program.exitProgram();
        }
        if (page.getStatusCode() != 200) {
            System.out.println(page.getStatusCode());
        }

        if (page.getUrl().regex("/api/v4/members").match()) {
            var urls = page.getJson().jsonPath("$.data[*].url_token").all();
            for (var url : urls) {
                page.addTargetRequest("https://www.zhihu.com/people/" + url + "/activities");
            }
        } else {
            page.addTargetRequest(getAPI(page.getUrl().get()));
            setJsonInfo(page);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
