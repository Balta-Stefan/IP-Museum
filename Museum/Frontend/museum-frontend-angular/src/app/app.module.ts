import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule, HttpHeaders} from '@angular/common/http';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MuseumListCardComponent } from './components/museum-list-card/museum-list-card.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RssFeedCardComponent } from './components/rss-feed-card/rss-feed-card.component';
import { MuseumsPageComponent } from './components/museums-page/museums-page.component';
import { MuseumOverviewComponent } from './components/museum-overview/museum-overview.component';

export const baseURL: string = "http://localhost:8000/api/v1";
export const jsonHeaders: HttpHeaders = new HttpHeaders({
  'Accept': 'application/json', 
  'Content-Type': 'application/json'
});

export function emptyStringsToNull(form: FormGroup){
  for(const field in form.controls){
    const control = form.get(field);

    if(control?.value == ''){
      form.get(field)?.setValue(null);
    }
    
  }
}

@NgModule({
  declarations: [
    AppComponent,
    MuseumListCardComponent,
    MainPageComponent,
    RssFeedCardComponent,
    MuseumsPageComponent,
    MuseumOverviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
