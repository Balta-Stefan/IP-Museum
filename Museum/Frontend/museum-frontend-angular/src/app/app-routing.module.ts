import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { MainPageComponent } from './components/main-page/main-page.component';
import { MuseumListCardComponent } from './components/museum-list-card/museum-list-card.component';
import { MuseumOverviewComponent } from './components/museum-overview/museum-overview.component';
import { MuseumsPageComponent } from './components/museums-page/museums-page.component';
import { RegistrationPageComponent } from './components/registration-page/registration-page.component';
import { RssFeedCardComponent } from './components/rss-feed-card/rss-feed-card.component';
import { RssFeedPageComponent } from './components/rss-feed-page/rss-feed-page.component';
import { SessionCheckComponent } from './components/session-check/session-check.component';
import { TourPageComponent } from './components/tour-page/tour-page.component';

const routes: Routes = [
  {
    path: '',
    component: MainPageComponent,
    children: [
      {
        path: 'museums',
        component: MuseumsPageComponent
      },
      {
        path: 'museums/:id',
        component: MuseumOverviewComponent
      },
      {
        path: 'museums/:museumID/tour/:tourID',
        component: TourPageComponent
      },
      {
        path: "news",
        component: RssFeedPageComponent
      }
    ]
  },
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'session_check',
    component: SessionCheckComponent
  },
  {
    path: 'register',
    component: RegistrationPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
