import { Component } from '@angular/core';
import { AdminService } from '../../service/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {
  constructor(private service:AdminService,private message:NzMessageService ){

  }
  cars:any = [];
  ngOnInit(){
    this.getAllCars();  
  }
  deleteCar(id:number){
    console.log(id);
    this.service.deleteCar(id).subscribe((data)=>{
      console.log(data);
      this.getAllCars();
      this.message.success("Car details deleted successfully")
    });
  }
  getAllCars() {
    this.service.getAllCars().subscribe(
      (data) => {
        //console.log("Received Data:", data); // Debugging
  
        if (Array.isArray(data)) {
          this.cars = data.map((element) => {
            //console.log("Returned Image Data:", element.returenedFile); // Check if image exists
            return {
              ...element,
              processedImg: element.returenedFile 
                ? `data:image/jpeg;base64,${element.returenedFile}` 
                : 'assets/default-car.jpg' // Use default if no image
            };
          });
        } else {
          console.error("Error: API did not return an array", data);
        }
      },
      (error) => {
        console.error("API Error:", error);
      }
    );
  }
  /*getAllCars() {
    this.service.getAllCars().subscribe(
      (data) => {
        console.log("Received Data:", data); // Debugging: Inspect the API response
        console.log("Is data.data an array?", Array.isArray(data.data)); // Debugging
  
        if (data && Array.isArray(data.data)) {
          this.cars = data.data.map((element: { returnedFile: any; }) => ({
            ...element,
            processedImg: element.returnedFile
              ? `data:image/jpeg;base64,${element.returnedFile}`
              : 'assets/default-car.jpg'
          }));
        } else {
          console.error("Error: API did not return an array. Received:", data);
        }
      },
      (error) => {
        console.error("API Error:", error);
      }
    );
  }*/
  
  
}
