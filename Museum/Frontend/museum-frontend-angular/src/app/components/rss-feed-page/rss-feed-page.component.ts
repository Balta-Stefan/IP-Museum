import { Component, OnInit } from '@angular/core';
import { RssFeedEntry } from 'src/app/models/RssFeedEntry';
import { RssService } from 'src/app/services/rss.service';

@Component({
  selector: 'app-rss-feed-page',
  templateUrl: './rss-feed-page.component.html',
  styleUrls: ['./rss-feed-page.component.css']
})
export class RssFeedPageComponent implements OnInit {

  feeds: RssFeedEntry[] = [];

  constructor(private rssService: RssService) { 
    this.rssService.getCultureRssFeeds().subscribe(vals => this.feeds = vals);
  }

  ngOnInit(): void {
  }

}
