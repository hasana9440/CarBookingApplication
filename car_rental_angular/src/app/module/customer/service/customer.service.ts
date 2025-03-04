import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';
const Basic_url =["http://localhost:4041"]
@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient) { }
   getAllCars():Observable<any>{
        return this.http.get(Basic_url+"/api/customer/cars",{
          headers:this.createAuthorizationHeader() 
        })
     }
      createAuthorizationHeader():HttpHeaders{
         let authHeaders:HttpHeaders = new HttpHeaders();
         //console.log(StorageService.getToken())
         return authHeaders.set(
           'Authorization',
           'Bearer '+StorageService.getToken()
         );
       }

       getCarById(id:number):Observable<any>{
        return this.http.get(Basic_url+"/api/customer/car/"+id,{
          headers:this.createAuthorizationHeader() 
        })
     }

     getBookingsById():Observable<any>{
      //console.log("reached customer service")
      //console.log(StorageService.getUserId());
      return this.http.get(Basic_url+"/api/customer/car/booking/"+StorageService.getUserId(),{
        headers:this.createAuthorizationHeader() 
      })
   }

     bookACar(carDto:any):Observable<any>{
      return this.http.post(Basic_url+"/api/customer/car/book",carDto,{
        headers:this.createAuthorizationHeader() 
      })
   }
}
