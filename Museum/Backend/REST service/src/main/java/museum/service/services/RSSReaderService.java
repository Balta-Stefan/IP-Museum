package museum.service.services;

import museum.service.models.DTOs.RSSFeedEntry;

import java.util.List;

public interface RSSReaderService
{
    List<RSSFeedEntry> returnFeeds(String url);
}
