
import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../service/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.css'
})
export class UpdateCarComponent {
  carId: any;
  existingImage: string |null = null;
  updateForm!:FormGroup;
  listOfBrands: string[] = ['Toyota', 'Honda', 'Ford', 'BMW', 'Mercedes','Suzuki'];
  listOfTransmission: string[] = ['Manual', 'Automatic'];
  listOfColor: string[] = ['Red', 'Blue', 'Black', 'White', 'Gray'];
  listOfType: string[] = ['Sedan', 'SUV', 'Truck', 'Coupe'];
  imgChanged:boolean = false;
  imagePreview:string | ArrayBuffer | null | undefined;
  selectedFile:any;
  constructor(private adminService:AdminService,private fb:FormBuilder, private message:NzMessageService,private service:AdminService,private activatedRouter:ActivatedRoute,private router :Router){

  }
  ngOnInit(){
    this.updateForm = this.fb.group({
          name:[null,Validators.required],
          brand:[null,Validators.required],
          type:[null,Validators.required],
          color:[null,Validators.required],
          price:[null,Validators.required],
          description:[null,Validators.required],
          transmission:[null,Validators.required],
          
        })
    this.carId = this.activatedRouter.snapshot.params['id'];
    //console.log(this.carId);

    this.getCarById();
  }
  getCarById(){
    this.service.getCarById(this.carId).subscribe((data)=>{
      console.log(data)
      console.log("----------------------")
      // console.log(data.returnedFile)
      // console.log(data.returenedFile)
     
      const carDto = data;
      this.existingImage = 'data:image/jpeg;base64,' + data.returenedFile;

      console.log(carDto);
      //console.log(this.existingImage)
      this.updateForm.patchValue(carDto);
    });
  }
  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.imgChanged = true;
    this.existingImage = null;
    this.previewImage();
  }
  updateCar(){
    const formData: FormData = new FormData();
    // Ensure image is included
    if (this.imgChanged && this.selectedFile) {
      formData.append('img', this.selectedFile, this.selectedFile.name);
    } else {
      console.warn("No new image selected.");
    }
    
    // Append all form values
    formData.append('name', this.updateForm.get('name')?.value);
    formData.append('brand', this.updateForm.get('brand')?.value);
    formData.append('type', this.updateForm.get('type')?.value);
    formData.append('color', this.updateForm.get('color')?.value);
    formData.append('price', this.updateForm.get('price')?.value);
    formData.append('description', this.updateForm.get('description')?.value);
    formData.append('transmission', this.updateForm.get('transmission')?.value);
    console.log("FormData contents:");
    formData.forEach((value, key) => {
        console.log(`${key}:`, value);
    });
    
  

    this.adminService.updateCar(this.carId,formData).subscribe({
      next: (data) => {
        console.log(data)
        this.message.success("Car Updated Successfully");
        this.router.navigateByUrl("admin/dashboard");
      },
      error: (err) => {
        console.error("Error updating car:", err);
        this.message.error("Failed to Update car");
      }
    });
  }
  previewImage(){
    const reader =new  FileReader();
    reader.onload = ()=>{
      this.imagePreview = reader.result;
    }
    reader.readAsDataURL(this.selectedFile);
  }
}
