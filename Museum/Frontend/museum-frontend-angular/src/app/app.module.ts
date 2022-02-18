import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MuseumListCardComponent } from './components/museum-list-card/museum-list-card.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RssFeedCardComponent } from './components/rss-feed-card/rss-feed-card.component';
import { MuseumsPageComponent } from './components/museums-page/museums-page.component';

export const baseURL: string = "http://localhost:8080/api/v1";

@NgModule({
  declarations: [
    AppComponent,
    MuseumListCardComponent,
    MainPageComponent,
    RssFeedCardComponent,
    MuseumsPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
