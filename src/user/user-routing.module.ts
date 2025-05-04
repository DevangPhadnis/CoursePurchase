import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { CourseDetailsComponent } from './components/course-details/course-details.component';
import { PurchasedCourseDetailsComponent } from './components/purchased-course-details/purchased-course-details.component';
import { PaymentDetailsComponent } from './components/payment-details/payment-details.component';

const routes: Routes = [
  {
    path: 'user-details', 
    component: UserDetailsComponent
  },
  {
    path: 'course-details',
    component: CourseDetailsComponent
  },
  {
    path: 'purchased-course-details',
    component: PurchasedCourseDetailsComponent
  },
  {
    path: 'payment-details',
    component: PaymentDetailsComponent
  },
  {
    path: '',
    redirectTo: 'user-details',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
