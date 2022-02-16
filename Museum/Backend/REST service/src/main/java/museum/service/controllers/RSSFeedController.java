package museum.service.controllers;

import museum.service.models.DTOs.RSSFeedEntry;
import museum.service.services.RSSReaderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rss")
public class RSSFeedController
{
    private final RSSReaderService rssReaderService;

    @Value("${rss.culture_feeds}")
    private String cultureFeeds;

    public RSSFeedController(RSSReaderService rssReaderService)
    {
        this.rssReaderService = rssReaderService;
    }

    @GetMapping("/culture")
    public List<RSSFeedEntry> getCultureRSSFeeds()
    {
        return rssReaderService.returnFeeds(cultureFeeds);
    }
}
