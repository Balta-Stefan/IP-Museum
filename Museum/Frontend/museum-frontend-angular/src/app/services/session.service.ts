import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { baseURL } from '../app.module';
import { LoginDetails } from '../models/LoginDetails';
import { LoginRequest } from '../models/LoginRequest';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http: HttpClient, private router: Router) { 
  }

  checkSessionStatus(): Observable<LoginDetails>{
    const creds: LoginRequest = {username: 'a', password: 'b'};

    return this.authenticate(creds);
  }

  authenticate(credentials: LoginRequest): Observable<LoginDetails>{
    return this.http.post<any>(`${baseURL}/login`, credentials);
  }

  logout(): void{
    this.http.post<any>(`${baseURL}/logout`, {}).subscribe(response => {
      complete: () => {
        localStorage.removeItem('jwt');
        this.router.navigateByUrl('/login');
      }
    });
  }

}
