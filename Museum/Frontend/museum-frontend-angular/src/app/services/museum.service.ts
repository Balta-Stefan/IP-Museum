import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseURL, jsonHeaders } from '../app.module';
import { MuseumDTO } from '../models/MuseumDTO';

@Injectable({
  providedIn: 'root'
})
export class MuseumService {
  
  constructor(private client: HttpClient) { }

  getMuseums(params?: HttpParams): Observable<MuseumDTO[]>{
    return this.client.get<MuseumDTO[]>(`${baseURL}/museums`,
    {
      headers: jsonHeaders,
      params: params
    });
  }
}
