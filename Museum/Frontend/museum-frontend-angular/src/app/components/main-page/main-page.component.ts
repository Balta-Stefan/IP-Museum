import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDetails } from 'src/app/models/LoginDetails';
import { RssFeedEntry } from 'src/app/models/RssFeedEntry';
import { UserRole } from 'src/app/models/UserRole';
import { AuthorizationUtils } from 'src/app/services/AuthorizationUtil';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  adminMenu: boolean = false;

  adminPanelToken: string = "";

  constructor(private sessionService: SessionService, private router: Router) { 
   const userDetails: LoginDetails | null = AuthorizationUtils.getUser();

   if(userDetails && userDetails.role == UserRole.ADMIN.toString()){
     this.adminMenu = true;
     this.adminPanelToken = userDetails.adminToken;
   }
  }

  ngOnInit(): void {
  }

  logout(): void{
    this.sessionService.logout();

    this.router.navigateByUrl('/login');
  }
}
