import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../service/admin.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-car',
  templateUrl: './post-car.component.html',
  styleUrls: ['./post-car.component.css']
})
export class PostCarComponent {
  listOfBrands: string[] = ['Toyota', 'Honda', 'Ford', 'BMW', 'Mercedes','Suzuki'];
  listOfTransmission: string[] = ['Manual', 'Automatic'];
  listOfColor: string[] = ['Red', 'Blue', 'Black', 'White', 'Gray'];
  listOfType: string[] = ['Sedan', 'SUV', 'Truck', 'Coupe'];

  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  postCarForm!:FormGroup
  carDetails = {
    brand: '',
    name: '',
    type: '',
    transmission: '',
    color: '',
    price: '',
    description: ''
  };
  constructor(private fb:FormBuilder,private adminService:AdminService,private message:NzMessageService,private router:Router){


  }
  ngOnInit(){
    this.postCarForm = this.fb.group({
      name:[null,Validators.required],
      brand:[null,Validators.required],
      type:[null,Validators.required],
      color:[null,Validators.required],
      price:[null,Validators.required],
      description:[null,Validators.required],
      transmission:[null,Validators.required],
     
    })
  }
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  postCar(): void {
    const formData: FormData = new FormData();

  // Append all form values
  formData.append('name', this.postCarForm.get('name')?.value);
  formData.append('brand', this.postCarForm.get('brand')?.value);
  formData.append('type', this.postCarForm.get('type')?.value);
  formData.append('color', this.postCarForm.get('color')?.value);
  formData.append('price', this.postCarForm.get('price')?.value);
  formData.append('description', this.postCarForm.get('description')?.value);
  formData.append('transmission', this.postCarForm.get('transmission')?.value);

  // Ensure image is included
  if (this.selectedFile) {
    formData.append('img', this.selectedFile, this.selectedFile.name);
  }

  //console.log('FormData before sending:', formData);

  this.adminService.postCar(formData).subscribe({
    next: (data) => {
      this.message.success("Car Posted Successfully");
      this.router.navigateByUrl("admin/dashboard");
    },
    error: (err) => {
      console.error("Error posting car:", err);
      this.message.error("Failed to post car");
    }
  });
}
}
