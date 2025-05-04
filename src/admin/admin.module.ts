import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { PieChartComponent } from './component/pie-chart/pie-chart.component';
import { BarChartComponent } from './component/bar-chart/bar-chart.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CardModule } from 'primeng/card';
import { NgChartsModule } from 'ng2-charts';
import { PaginatorModule } from 'primeng/paginator';
import { LineChartComponent } from './component/line-chart/line-chart.component';
import { HomeComponent } from './component/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { NgSelectModule } from '@ng-select/ng-select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { CourseDetailsComponent } from './component/course-details/course-details.component';
import { AddCourseComponent } from './component/add-course/add-course.component';

@NgModule({
  declarations: [
    PieChartComponent,
    BarChartComponent,
    LineChartComponent,
    HomeComponent,
    CourseDetailsComponent,
    AddCourseComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    HttpClientModule,

    MatButtonModule,
    MatIconModule,
    CardModule,
    NgChartsModule,
    PaginatorModule,
    MatDialogModule,
    NgSelectModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatTabsModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatTooltipModule,
    NgxDatatableModule
  ]
})
export class AdminModule { }
