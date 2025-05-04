import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminService } from 'src/admin/service/admin.service';

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

  course = new FormGroup({
    courseTitle: new FormControl(null, [Validators.required]),
    courseDescription: new FormControl('', [Validators.required]),
    courseGenre: new FormControl('', [Validators.required]),
    thumbnailUrl: new FormControl(''),
    courseUrl: new FormControl('', [Validators.required]),
    coursePrice: new FormControl('', [Validators.required]),
  })

  onSubmit(): void {
    const payload = {
      title: this.course?.controls?.courseTitle?.value,
      description: this.course?.controls?.courseDescription?.value,
      genre: this.course?.controls?.courseGenre?.value,
      thumbnailUrl: this.course?.controls?.thumbnailUrl?.value,
      courseUrl: this.course?.controls?.courseUrl?.value,
      price: this.course?.controls?.coursePrice?.value
    }

    this.adminService.saveCourseDetails(payload).subscribe((res: any) => {
      this.matDialogRef.close(true);
    })
  }

  onClose(): void {
    this.matDialogRef.close(false);
  }
}
