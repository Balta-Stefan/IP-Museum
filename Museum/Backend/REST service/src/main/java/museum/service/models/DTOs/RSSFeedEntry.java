package museum.service.models.DTOs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RSSFeedEntry
{
    private final String title;
    private final String description;
    private final LocalDateTime published;
    private final String thumbnail;
    private final String link;
}
