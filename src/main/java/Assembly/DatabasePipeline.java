package Assembly;

import Database.*;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * DatabasePipeline
 */
public class DatabasePipeline implements Pipeline {

    private Database db = null;

    public DatabasePipeline(Database db) {
        this.db = db;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        User user=resultItems.get("data");
        if (user!=null) {
            db.add(resultItems.get("data"));
        }
    }
}