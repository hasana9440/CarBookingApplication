import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';
const Basic_url =["http://localhost:8080"]
@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }
  postCar(carDto:any):Observable<any>{
    return this.http.post(Basic_url+"/api/admin/car",carDto,{
      headers:this.createAuthorizationHeader() 
    });
  }
  searchCar(carDto:any):Observable<any>{
    console.log("reached admin service")
    return this.http.post(Basic_url+"/api/admin/car/search",carDto,{
      headers:this.createAuthorizationHeader() 
    });
  }
  // updateCar(carId:number,carDto:any):Observable<any>{
  //   return this.http.put(Basic_url+"/api/admin/car/"+carId,carDto,{
  //     headers:this.createAuthorizationHeader() 
  //   });
  // }

  updateCar(carId: number, carDto: FormData): Observable<any> {
    return this.http.put(Basic_url+"/api/admin/car/"+carId, carDto, {
      headers: this.createAuthorizationHeader(),
    });
  }
  
  getCarBookings():Observable<any>{
    return this.http.get(Basic_url+"/api/admin/car/bookings",{
      headers:this.createAuthorizationHeader() 
    })
 }

   getAllCars():Observable<any>{
      return this.http.get(Basic_url+"/api/admin/cars",{
        headers:this.createAuthorizationHeader() 
      })
   }

   
   deleteCar(id:number):Observable<any>{
    return this.http.delete(Basic_url+"/api/admin/car/"+id,{
      headers:this.createAuthorizationHeader()
    });
   }
   getCarById(id:number):Observable<any>{
    return this.http.get(Basic_url+"/api/admin/car/"+id,{
      headers:this.createAuthorizationHeader()
    })
   }
   changeBookingStatus(id:number,status:string):Observable<any>{
    console.log("entered admin service ")
    return this.http.get(Basic_url+`/api/admin/car/bookings/${id}/${status}`,{
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


}
