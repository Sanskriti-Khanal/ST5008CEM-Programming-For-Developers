import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class PrimaryKeyGenerator {
    private int userId;
    private int tweetId;
    private static final String FILE_NAME = "ids.txt";

    public PrimaryKeyGenerator() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String userIdLine = reader.readLine();
                String tweetIdLine = reader.readLine();
                this.userId = Integer.parseInt(userIdLine.split(": ")[1]);
                this.tweetId = Integer.parseInt(tweetIdLine.split(": ")[1]);
            } catch (IOException e) {
            }
        } else {
            this.userId = 0;
            this.tweetId = 0;
        }
    }

    public synchronized int getNextUserId() {
        userId++;
        saveToFile();
        return userId;
    }

    public synchronized int getNextTweetId() {
        tweetId++;
        saveToFile();
        return tweetId;
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("userId: " + userId + "\n");
            writer.write("tweetId: " + tweetId + "\n");
        } catch (IOException e) {
        }
    }
}

class Tweet {
    private final int userId;
    private final int tweetId;
    private final String tweetText;
    private final Date tweetDate;
    private final List<String> hashtags;

    public Tweet(int userId, int tweetId, String tweetText, Date tweetDate) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.tweetText = tweetText;
        this.tweetDate = tweetDate;
        this.hashtags = extractHashtags(tweetText);
    }

    private List<String> extractHashtags(String text) {
        return Arrays.stream(text.split(" "))
                .filter(word -> word.startsWith("#"))
                .collect(Collectors.toList());
    }

    public int getUserId() {
        return userId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public String getTweetText() {
        return tweetText;
    }

    public Date getTweetDate() {
        return tweetDate;
    }

    public List<String> getHashtags() {
        return hashtags;
    }
}

public class TrendingHashtags {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final Date FEB_START_DATE;
    private static final Date FEB_END_DATE;

    static {
        try {
            FEB_START_DATE = DATE_FORMAT.parse("2024-02-01");
            FEB_END_DATE = DATE_FORMAT.parse("2024-02-29");
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format", e);
        }
    }

    private final PrimaryKeyGenerator keyGenerator;
    private final List<Tweet> tweets;

    public TrendingHashtags() {
        this.keyGenerator = new PrimaryKeyGenerator();
        this.tweets = new ArrayList<>();
    }

    public void addTweet(String tweetText, String tweetDateStr) throws ParseException {
        Date tweetDate = DATE_FORMAT.parse(tweetDateStr);
        int userId = keyGenerator.getNextUserId();
        int tweetId = keyGenerator.getNextTweetId();
        tweets.add(new Tweet(userId, tweetId, tweetText, tweetDate));
    }

    public void printInputTable() {
        System.out.println("User ID | Tweet ID | Tweet Text | Hashtags | Tweet Date");
        System.out.println("-------------------------------------------------------");
        for (Tweet tweet : tweets) {
            System.out.printf("%7d | %8d | %s | %s | %s%n",
                    tweet.getUserId(),
                    tweet.getTweetId(),
                    tweet.getTweetText(),
                    String.join(", ", tweet.getHashtags()),
                    DATE_FORMAT.format(tweet.getTweetDate()));
        }
    }

    public List<Map.Entry<String, Integer>> findTopTrendingHashtags() {
        Map<String, Integer> hashtagCounts = new HashMap<>();

        // Filter tweets for February 2024 and count hashtags
        for (Tweet tweet : tweets) {
            if (tweet.getTweetDate().compareTo(FEB_START_DATE) >= 0 &&
                    tweet.getTweetDate().compareTo(FEB_END_DATE) <= 0) {
                for (String hashtag : tweet.getHashtags()) {
                    hashtagCounts.put(hashtag, hashtagCounts.getOrDefault(hashtag, 0) + 1);
                }
            }
        }

        // Sort hashtags by count (descending) and alphabetically (ascending)
        List<Map.Entry<String, Integer>> sortedHashtags = new ArrayList<>(hashtagCounts.entrySet());
        sortedHashtags.sort((a, b) -> {
            int countCompare = b.getValue().compareTo(a.getValue());
            if (countCompare == 0) {
                return a.getKey().compareTo(b.getKey());
            }
            return countCompare;
        });

        return sortedHashtags;
    }

    public void printTopTrendingHashtags() {
        List<Map.Entry<String, Integer>> topHashtags = findTopTrendingHashtags();
        System.out.println("\nTop 3 Trending Hashtags for February 2024:");
        System.out.println("Hashtag | Count");
        System.out.println("---------------");
        for (int i = 0; i < Math.min(3, topHashtags.size()); i++) {
            System.out.printf("%s | %d%n", topHashtags.get(i).getKey(), topHashtags.get(i).getValue());
        }
    }

    public static void main(String[] args) {
        TrendingHashtags trendingHashtags = new TrendingHashtags();
        try {
            trendingHashtags.addTweet("This is a #test tweet!", "2024-02-10");
            trendingHashtags.addTweet("Another #example tweet with #test", "2024-02-15");
            trendingHashtags.addTweet("Just a #random tweet", "2024-01-25");
            trendingHashtags.addTweet("February is great! #february #test", "2024-02-20");
            trendingHashtags.addTweet("More #test tweets for #february", "2024-02-22");

            trendingHashtags.printInputTable();
            trendingHashtags.printTopTrendingHashtags();
        } catch (ParseException e) {
        }
    }
}