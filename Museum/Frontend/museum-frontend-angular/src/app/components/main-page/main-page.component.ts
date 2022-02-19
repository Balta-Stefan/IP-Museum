import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RssFeedEntry } from 'src/app/models/RssFeedEntry';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {


  constructor(private sessionService: SessionService, private router: Router) { 
   
  }

  ngOnInit(): void {
  }

  logout(): void{
    this.sessionService.logout();

    this.router.navigateByUrl('/login');
  }
}
