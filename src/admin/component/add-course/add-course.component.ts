import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminService } from 'src/admin/service/admin.service';
import * as pako from 'pako';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.scss']
})
export class AddCourseComponent implements OnInit {

  constructor(private matDialogRef: MatDialogRef<AddCourseComponent>,
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    if(this.formData != null && this.formData != undefined) {
      this.course?.patchValue(this.formData);
      this.showSubmitButton = false;
    }
  }

  header: string = "Add New Course";
  formData: any;
  showSubmitButton: any = true;
  selectedFile: File | null = null;
  selectedFileName: string = '';

  course = new FormGroup({
    courseTitle: new FormControl(null, [Validators.required]),
    courseDescription: new FormControl('', [Validators.required]),
    courseGenre: new FormControl('', [Validators.required]),
    thumbnailUrl: new FormControl(''),
    courseUrl: new FormControl(''),
    coursePrice: new FormControl('', [Validators.required]),
  })

  onFileSelected(event: any): void {
    this.selectedFile = event.files[0];
    this.selectedFileName = String(this.selectedFile?.name);
  }

  onSubmit(): void {
    if(!this.selectedFile) {
      alert('Please Select a file Before Submitting.');
      return;
    }

    const file = this.selectedFile;
    const reader = new FileReader();

    reader.onload = () => {
      const formData = new FormData();
      formData.append('title', String(this.course?.controls?.courseTitle?.value));
      formData.append('description', String(this.course?.controls?.courseDescription?.value));
      formData.append('genre', String(this.course?.controls?.courseGenre?.value));
      formData.append('thumbnailUrl', String(this.course?.controls?.thumbnailUrl?.value));
      formData.append('courseUrl', String(this.course?.controls?.courseUrl?.value));
      formData.append('price', String(this.course?.controls?.coursePrice?.value));
      formData.append('fileBytes', file);

      this.adminService.saveCourseDetails(formData).subscribe((res: any) => {
        this.matDialogRef.close(true);
      })
    }
    reader.readAsArrayBuffer(file);
  }

  onClose(): void {
    this.matDialogRef.close(false);
  }
}
