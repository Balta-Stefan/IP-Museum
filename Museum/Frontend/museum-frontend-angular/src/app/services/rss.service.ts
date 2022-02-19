import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseURL, jsonHeaders } from '../app.module';
import { RssFeedEntry } from '../models/RssFeedEntry';

@Injectable({
  providedIn: 'root'
})
export class RssService {

  constructor(private client: HttpClient) { }

  getCultureRssFeeds(): Observable<RssFeedEntry[]>{
      return this.client.get<RssFeedEntry[]>(`${baseURL}/rss/culture`,
      {
        headers: jsonHeaders
      });
  }
}
