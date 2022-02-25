import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { baseURL, jsonHeaders } from '../app.module';
import { BankTransactionDTO } from '../models/BankTransactionDTO';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  constructor(private http: HttpClient) { }

  sendPaymentInfo(payment: any, url: string): Observable<BankTransactionDTO>{
    return this.http.patch<any>(url, payment, {
      headers: jsonHeaders
    });
  }
}
