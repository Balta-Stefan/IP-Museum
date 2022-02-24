import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDetails } from './models/LoginDetails';
import { AuthorizationUtils } from './services/AuthorizationUtil';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'museum-frontend-angular';


  constructor(private sessionService: SessionService, private router: Router){

    this.router.navigateByUrl('/session_check');

    // check whether JWT is in local storage
    const jwt: string | null = localStorage.getItem('jwt');
  
    if(jwt){
      this.sessionService.checkSessionStatus().subscribe({
        error: (err: any) => {
          this.router.navigateByUrl('/login');
        },
        next: (receivedValue: LoginDetails) => {
          AuthorizationUtils.userLogin(receivedValue, false);
          this.router.navigateByUrl('');
        }
      });
    }
    else{
      this.router.navigateByUrl('/login');
      return;
    }
  }
}
