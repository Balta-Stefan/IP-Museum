import { Component, Input, OnInit } from '@angular/core';
import { RssFeedEntry } from 'src/app/models/RssFeedEntry';

@Component({
  selector: 'app-rss-feed-card',
  templateUrl: './rss-feed-card.component.html',
  styleUrls: ['./rss-feed-card.component.css']
})
export class RssFeedCardComponent implements OnInit {
  @Input() rssEntry!: RssFeedEntry;

  constructor() { }

  ngOnInit(): void {
  }

}
