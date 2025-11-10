import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LinkShortener {
    private Map<String, Link> links = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public LinkShortener() {
        scheduler.scheduleAtFixedRate(this::removeExpiredLinks, 0, 1, TimeUnit.MINUTES);
    }

    public String shortenLink(String longUrl, String userId, int maxClicks, int lifetimeInSeconds) {
        String shortUrl = generateShortUrl();
        Link link = new Link(longUrl, shortUrl, userId, maxClicks, lifetimeInSeconds);
        links.put(shortUrl, link);
        return shortUrl;
    }

    private String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void redirect(String shortUrl) throws URISyntaxException, IOException {
        Link link = links.get(shortUrl);
        if (link != null && link.isActive()) {
            link.incrementClickCount();
            // Перенаправление на длинный URL
            System.out.println("Перенаправление на: " + link.getLongUrl());
            Desktop.getDesktop().browse(new URI("https://ru.stackoverflow.com"));
        } else {
            System.out.println("Ссылка недоступна или истекла.");
        }
    }

    private void removeExpiredLinks() {
        links.values().removeIf(Link::isExpired);
        System.out.println("Удалены просроченные ссылки.");
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        LinkShortener shortener = new LinkShortener();

        String shortLink = shortener.shortenLink("https://ru.stackoverflow.com", "user123", 5, 60);
        System.out.println("Сокращенная ссылка: " + shortLink);

        for (int i = 0; i < 6; i++) {
            shortener.redirect(shortLink);
        }

        try {
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Проверка редиректа после истечения срока
        shortener.redirect(shortLink);
    }
}