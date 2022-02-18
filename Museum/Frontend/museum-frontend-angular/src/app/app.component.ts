import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'museum-frontend-angular';

  constructor(private sessionService: SessionService, private router: Router){
    /*this.sessionService.authenticate(null, null).subscribe({
      error: () => {
        this.router.navigateByUrl('/login');
      }
    });

    this.router.navigateByUrl('/session_check');*/
    this.router.navigateByUrl('/register');
  }
}
