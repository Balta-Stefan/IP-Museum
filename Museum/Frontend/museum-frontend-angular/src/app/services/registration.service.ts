import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jsonHeaders, baseURL } from '../app.module';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient) { }

  register(params: any): Observable<any>{
    return this.http.post<any>(`${baseURL}/user`, params, {
      headers: jsonHeaders
    });
  }
}
