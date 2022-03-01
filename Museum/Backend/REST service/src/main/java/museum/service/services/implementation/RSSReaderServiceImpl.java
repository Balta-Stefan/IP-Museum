package museum.service.services.implementation;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import museum.service.models.DTOs.RSSFeedEntry;
import museum.service.services.RSSReaderService;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RSSReaderServiceImpl implements RSSReaderService
{
    @Override
    public List<RSSFeedEntry> returnFeeds(String url)
    {
        try
        {
            URL feedURL = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedURL));


            List<RSSFeedEntry> entries = new ArrayList<>();

            for(SyndEntry entry : feed.getEntries())
            {
                String title = entry.getTitle().trim();
                String description = entry.getDescription().getValue().trim();
                LocalDateTime publishedTimestamp = LocalDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault());
                String link = entry.getLink();
                String picture = null;

                for(SyndEnclosure enclosure : entry.getEnclosures())
                {
                    if(enclosure.getType().contains("image"))
                    {
                        picture = enclosure.getUrl();
                        break; // get only one picture - the thumbnail
                    }
                }

                RSSFeedEntry entryDTO = new RSSFeedEntry(title, description, publishedTimestamp, picture, link);
                entries.add(entryDTO);
            }

            return entries;

            /*
            *   Entry 3
                Title: 'Marry Me' And Decoding Jennifer Lopez's Church Of Romantic Comedy
                Description: The actor has a long history of catering to the white gaze in rom-coms, and her newest movie is no exception.
                Published at: Fri Feb 11 11:45:04 CET 2022
                Enclosures:
                Enclosure URL: https://img.huffingtonpost.com/asset/620542243600000d3524e922.jpg?cache=zngkyc3p53&ops=224_126
                Enclosure type: image/jpeg
            * */
        }
        catch(Exception e)
        {
            log.warn("RSS service has thrown an exception: ", e);
            return Collections.emptyList();
        }
    }
}
