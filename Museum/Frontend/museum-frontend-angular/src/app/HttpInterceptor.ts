import { HttpEvent, HttpHandler, HttpHeaders, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


@Injectable()
export class HttpInterceptor implements HttpInterceptor{
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        let header = new HttpHeaders();

        const jwt: string | null = localStorage.getItem('jwt');

        let newReq: any = null;

        if(jwt){
            header = header.append('Authorization', 'Bearer ' + jwt);

            newReq = request.clone({
                withCredentials: true,
                headers: header
            });
        }
        else{
            newReq = request.clone({
                withCredentials: true,
                headers: header
            });
        }

        return next.handle(newReq);
    }
}