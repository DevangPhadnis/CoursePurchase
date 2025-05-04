import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import * as moment from 'moment';
import { AdminService } from 'src/admin/service/admin.service';
import { AddCourseComponent } from '../add-course/add-course.component';

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.scss']
})
export class CourseDetailsComponent implements OnInit {

  constructor(private adminService: AdminService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.adminService.fetchCourseDetails().subscribe((res: any) => {
      const rows: [] = res.data.map((row: any) => {
        return {
          courseId: row?.courseId,
          tile: row?.title,
          description: row?.description,
          genre: row?.genre,
          thumbnailUrl: row?.thumbnailUrl,
          courseUrl: row?.courseUrl,
          createdDate: moment(new Date(row?.createdDate)).format('DD-MM-YYYY'),
          coursePrice: row?.price
        }
      })
      this.filteredRows = [...rows];
    })
  }

  filteredRows = [];
  columns = [
    {
      prop: 'tile',
      name: 'Course Title'
    },
    {
      prop: 'description',
      name: 'Course Description'
    },
    {
      prop: 'genre',
      name: 'Course Genre'
    },
    {
      prop: 'thumbnailUrl',
      name: 'Thumbnail URL'
    },
    {
      prop: 'courseUrl',
      name: 'Course URL'
    },
    {
      prop: 'coursePrice',
      name: 'Course Price'
    },
    {
      prop: 'createdDate',
      name: 'Course Creation Date'
    }
  ]

  onDelete(row: any): void {
    this.adminService.deleteCourseDetails(row?.courseId).subscribe((res: any) => {
      this.ngOnInit();
    })
  }

  addCourse(): void {
    const dialogRef = this.dialog.open(AddCourseComponent, {
      height: '50vh',
      width: '45vw',
    })
    dialogRef.afterClosed().subscribe((res: boolean) => {
      if(res) {
        this.ngOnInit();
      }
    })
  }

  onClick(event: Event, row: any): void {
    event.preventDefault();
    window.open(row?.courseUrl, '_blank');
  }

  onThumbnailClick(event: Event, row: any): void {
    event.preventDefault();
    if(row?.thumbnailUrl) {
      window.open(row?.thumbnailUrl, '_blank')
    }
  }
}
