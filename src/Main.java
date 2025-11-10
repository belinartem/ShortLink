import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.*;

class Link {
    private String longUrl;
    private String shortUrl;
    private String userId;
    private int clickCount;
    private int maxClicks;
    private Date expirationDate;

    public Link(String longUrl, String shortUrl, String userId, int maxClicks, int lifetimeInSeconds) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.userId = userId;
        this.clickCount = 0;
        this.maxClicks = maxClicks;
        this.expirationDate = new Date(System.currentTimeMillis() + lifetimeInSeconds * 1000);
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public boolean isActive() {
        return clickCount < maxClicks && new Date().before(expirationDate);
    }

    public void incrementClickCount() {
        clickCount++;
    }

    public boolean isExpired() {
        return new Date().after(expirationDate);
    }
}