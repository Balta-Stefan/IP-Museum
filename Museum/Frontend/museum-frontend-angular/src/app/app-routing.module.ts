import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './components/main-page/main-page.component';
import { MuseumListCardComponent } from './components/museum-list-card/museum-list-card.component';
import { MuseumOverviewComponent } from './components/museum-overview/museum-overview.component';
import { MuseumsPageComponent } from './components/museums-page/museums-page.component';
import { RssFeedCardComponent } from './components/rss-feed-card/rss-feed-card.component';

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
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
