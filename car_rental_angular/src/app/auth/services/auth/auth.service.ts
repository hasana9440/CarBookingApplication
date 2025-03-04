import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
const base_url = ["http://localhost:4041"]
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }
  register(signuprequest:any):Observable<any>{
    return this.http.post(base_url+"/api/auth/signup",signuprequest)
  }
  login(loginRequest:any):Observable<any>{
    return this.http.post(base_url+"/api/auth/login",loginRequest);
  }
}
