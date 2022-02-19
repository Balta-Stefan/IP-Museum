import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  formData: FormGroup;

  loginMessage: string = "";

  constructor(private fb: FormBuilder, private sessionService: SessionService, private router: Router) { 
    this.formData = fb.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }

  ngOnInit(): void {
  }

  login(): void{
    this.sessionService.authenticate(this.formData.value['username'], this.formData.value['password']).subscribe({
      error: () => {
        this.loginMessage = "Prijava neuspješna";
      },
      complete: () => {
        this.router.navigateByUrl('');
      }
    });
  }
}
