import java.util.*;

class Link {
    private final String longUrl;
    private final String shortUrl;
    private int clickCount;
    private final int maxClicks;
    private final Date expirationDate;

    public Link(String longUrl, String shortUrl, String userId, int maxClicks, int lifetimeInSeconds) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.clickCount = 0;
        this.maxClicks = maxClicks;
        this.expirationDate = new Date(System.currentTimeMillis() + lifetimeInSeconds * 1000L);
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