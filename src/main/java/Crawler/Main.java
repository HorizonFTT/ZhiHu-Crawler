package Crawler;

import Assembly.MySpider;
import Assembly.Processor;
import Assembly.MyDownloader;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

/**
 * Main
 */
public class Main {

    private MySpider zhiHuSpider = null;

    private static long beg;

    private static long end;

    private void init(String[] args) {
        var local = false;
        var del = false;
        int threads = 10;

        if (args.length > 0 && args[0].equals("-s")) {
            Scanner sin = new Scanner(System.in);
            System.out.println("Do you want to save the data locally? Enter 'local' to do this:");
            local = sin.nextLine().equals("local");
            System.out.println("Do you want to empty the existing data? Enter 'delete' to do this:");
            del = sin.nextLine().equals("delete");
            System.out.println("Please enter the number of threads:");
            try {
                threads = Integer.valueOf(sin.nextLine());
            } catch (NumberFormatException e) {
                threads = 10;
            }finally{
                sin.close();
            }
        }

        Processor zhiHu = new Processor();
        zhiHuSpider = new MySpider(zhiHu, local);
        System.out.println("Starting...");
        zhiHuSpider.empty(del);
        zhiHuSpider.addUrl("https://www.zhihu.com/people/lu-jia-1-62/activities")
                .setDownloader(new MyDownloader(zhiHuSpider)).setScheduler(new FileCacheQueueScheduler("logs"))
                .thread(threads);
    }

    public void start(String[] args) {
        init(args);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                exitProgram();
            }
        });
        zhiHuSpider.run();
    }

    public void exitProgram() {
        System.out.println("Querying...");
        zhiHuSpider.count();
        System.out.println("Stopping...");
        zhiHuSpider.close();
        end = System.currentTimeMillis();
        System.out.println("Time consuming: " + (end - beg) / 1000 + "(s)");
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("config/log4j.properties");
        beg = System.currentTimeMillis();

        var program = new Main();
        program.start(args);
    }
}