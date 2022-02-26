import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule, HttpHeaders, HTTP_INTERCEPTORS} from '@angular/common/http';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { GoogleMapsModule } from '@angular/google-maps';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MuseumListCardComponent } from './components/museum-list-card/museum-list-card.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { RssFeedCardComponent } from './components/rss-feed-card/rss-feed-card.component';
import { MuseumsPageComponent } from './components/museums-page/museums-page.component';
import { MuseumOverviewComponent } from './components/museum-overview/museum-overview.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { SessionCheckComponent } from './components/session-check/session-check.component';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component';
import { HttpInterceptor } from './HttpInterceptor';
import { RssFeedPageComponent } from './components/rss-feed-page/rss-feed-page.component';
import { TourCardComponent } from './components/tour-card/tour-card.component';
import { TourPageComponent } from './components/tour-page/tour-page.component';
import { WeatherCardComponent } from './components/weather-card/weather-card.component';

export const staticContentBaseURL: string = "http://localhost:8000/";
export const baseURL: string = "api/v1";
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
    MuseumOverviewComponent,
    LoginPageComponent,
    SessionCheckComponent,
    RegistrationPageComponent,
    RssFeedPageComponent,
    TourCardComponent,
    TourPageComponent,
    WeatherCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    GoogleMapsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
