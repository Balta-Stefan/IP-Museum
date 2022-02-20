import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseURL, jsonHeaders } from '../app.module';
import { MuseumDTO } from '../models/MuseumDTO';
import { WeatherDTO } from '../models/WeatherDTO';

@Injectable({
  providedIn: 'root'
})
export class MuseumService {
  
  constructor(private client: HttpClient) { }

  getMuseums(params?: HttpParams): Observable<MuseumDTO[]>{
    return this.client.get<MuseumDTO[]>(`${baseURL}/museum`,
    {
      headers: jsonHeaders,
      params: params
    });
  }

  getMuseum(id: number): Observable<MuseumDTO>{
    return this.client.get<MuseumDTO>(`${baseURL}/museum/${id}`,
    {
      headers: jsonHeaders
    });
  }

  getWeathers(museumID: number): Observable<WeatherDTO[]>{
    return this.client.get<WeatherDTO[]>(`${baseURL}/museum/${museumID}/weather`,
    {
      headers: jsonHeaders
    });
  }
}
