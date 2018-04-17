package Crawler;

import Assembly.*;

import java.util.Scanner;

import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

/**
 * Main
 */
public class Main {
    
    private Processor zhiHu = null;

    private MySpider sp = null;

    private static Scanner sin = new Scanner(System.in);

    private void init(String[] args) {
        var local = false;
        var del = false;
        int threads = 10;

        if (args[0].equals("-s")) {
            System.out.println("Do you want to save the data locally? Enter 'local' to do this:");
            local = sin.nextLine().equals("local");
            System.out.println("Do you want to empty the existing data? Enter 'delete' to do this:");
            del = sin.nextLine().equals("delete");
            System.out.println("Please enter the number of threads:");
            try {
                threads = Integer.valueOf(sin.nextLine());
            } catch (NumberFormatException e) {
                threads = 10;
            }
        }

        zhiHu = new Processor(local);
        zhiHu.empty(del);
        System.out.println("Starting...");
        sp = new MySpider(zhiHu);
        sp.addUrl("https://www.zhihu.com/people/lu-jia-1-62/activities").setDownloader(new MyDownloader(sp))
                .setScheduler(new FileCacheQueueScheduler("logs")).thread(threads);
    }

    public void start(String[] args) {
        init(args);
        sp.start();
    }

    public void checkEnd() {
        String temp = null;
        do {
            temp = sin.nextLine();
        } while (!temp.equals(""));
        System.out.println("Stopping...");
        sp.stop();
        try {
            Thread.sleep(2000);
            System.out.println("Querying...");
            zhiHu.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        var beg = System.currentTimeMillis();

        var program = new Main();
        program.start(args);
        program.checkEnd();

        var end = System.currentTimeMillis();
        System.out.println("Time consuming: " + (end - beg) / 1000 + "(s)");

        sin.close();
    }
}