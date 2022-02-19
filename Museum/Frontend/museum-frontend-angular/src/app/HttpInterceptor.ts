import { HttpEvent, HttpHandler, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


@Injectable()
export class HttpInterceptor implements HttpInterceptor{
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        let newReq = request.clone({
            withCredentials: true,
            headers: request.headers.set('X-Requested-With', 'XMLHttpRequest')
        });

        return next.handle(newReq);
    }
}