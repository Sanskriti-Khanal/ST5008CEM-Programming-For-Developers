import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebCrawler {

    // Thread pool for concurrent crawling
    private final ExecutorService executorService;
    // Queue to store URLs to be crawled
    private final ConcurrentLinkedQueue<String> urlQueue;
    // Set to track visited URLs
    private final Set<String> visitedUrls;

    public WebCrawler(int numThreads) {
        this.executorService = Executors.newFixedThreadPool(numThreads);
        this.urlQueue = new ConcurrentLinkedQueue<>();
        this.visitedUrls = new HashSet<>();
    }

    // Start the crawling process
    public void startCrawling(String seedUrl) {
        urlQueue.add(seedUrl);
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.poll();
            if (url != null && !visitedUrls.contains(url)) {
                visitedUrls.add(url);
                executorService.submit(new CrawlTask(url));
            }
        }
        executorService.shutdown();
    }

    // Task to fetch and process a web page
    private class CrawlTask implements Runnable {
        private final String url;

        public CrawlTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                System.out.println("Crawling: " + url);
                Document document = Jsoup.connect(url).get();

                // Process the page content (e.g., extract data or index)
                processPage(document);

                // Extract links and add them to the queue
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String nextUrl = link.absUrl("href");
                    if (!visitedUrls.contains(nextUrl)) {
                        urlQueue.add(nextUrl);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error crawling " + url + ": " + e.getMessage());
            }
        }

        // Process the page content (e.g., extract data or index)
        private void processPage(Document document) {
            // Example: Print the title of the page
            System.out.println("Title: " + document.title());

            // Example: Print the text of the page
            // System.out.println("Text: " + document.text());
        }
    }

    public static void main(String[] args) {
        // Create a web crawler with 10 threads
        WebCrawler crawler = new WebCrawler(10);

        // Start crawling from a seed URL
        crawler.startCrawling("https://example.com");
    }
}