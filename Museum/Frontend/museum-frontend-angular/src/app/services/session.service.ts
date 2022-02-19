import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { baseURL } from '../app.module';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  constructor(private http: HttpClient, private router: Router) { 
  }

  authenticate(username: string | null, password: string | null): Observable<any>{
    const headers: HttpHeaders = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(username + ":" + password)
    });

    return this.http.post<any>(`${baseURL}/login`, {}, {
      headers: headers,
    });
  }

  logout(): void{
    this.http.post<any>(`${baseURL}/logout`, null).subscribe(response => {
      complete: () => {
        this.router.navigateByUrl('/login');
      }
    });
  }
}
