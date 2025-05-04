import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/user/service/user.service';

@Component({
  selector: 'app-purchased-course-details',
  templateUrl: './purchased-course-details.component.html',
  styleUrls: ['./purchased-course-details.component.scss']
})
export class PurchasedCourseDetailsComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadCourses({ first: 0, rows: this.rowCount });
  }

  courses: any[] = [];
  totalRecords: number = 0;
  pageNumber: number = 1;
  rowCount: number = 8;

  loadCourses(event: any): void {
    const page = event.first / event.rows + 1;  // Calculate page number
    this.pageNumber = page;
    this.rowCount = event.rows;
    
    this.courses.splice(0);
    this.courses = [...this.courses];
    this.userService.fetchPurchasedCourseList(this.pageNumber, this.rowCount).subscribe((res: any) => {
      if(res.data != null && res.data.length > 0) {
        this.courses = res.data;
        this.totalRecords = res.data[0].totalElements;
      }
    })
  }

  onLearningClick(courseDetails: any): void {
    window.open(courseDetails?.courseUrl, '_blank');
  }
}
