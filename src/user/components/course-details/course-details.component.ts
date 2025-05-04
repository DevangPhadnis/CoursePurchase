import { Component, NgZone, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/login/service/authentication.service';
import { UserService } from 'src/user/service/user.service';

declare var Razorpay: any;

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.scss']
})
export class CourseDetailsComponent implements OnInit {

  constructor(private userService: UserService,
    private ngZone: NgZone,
    private router: Router,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.getProfileDetails().subscribe((res: any) => {
      this.userDetails = res.data;
      this.loadCourses({ first: 0, rows: this.rowCount });
    })
  }

  courses: any[] = [];
  totalRecords: number = 0;
  pageNumber: number = 1;
  rowCount: number = 8;
  userDetails: any;

  loadCourses(event: any): void {
    const page = event.first / event.rows + 1;  // Calculate page number
    this.pageNumber = page;
    this.rowCount = event.rows;
    
    this.courses.splice(0);
    this.courses = [...this.courses];
    this.userService.fetchUnPaidCourseList(this.pageNumber, this.rowCount).subscribe((res: any) => {
      if(res.data != null && res.data.length > 0) {
        this.courses = res.data;
        this.totalRecords = res.data[0].totalElements;
      }
    })
  }

  onPaymentClick(row: any): void {
    const orderPayload = {
      amount: row?.price
    }
    this.userService.createPaymentOrder(orderPayload).subscribe((res: any) => {
      const razorpayOptions = {
        key: 'rzp_test_EGJtpbScjzPKf8',
        amount: res.data.amount,
        currency: res.data.currency,
        name: 'Course Purchase System',
        description: 'Payment for Course Purchase',
        order_id: res.data.id,
        handler: (response: any) => {
          this.ngZone.run(() => {
            const verifyPayload = {
              signature: response.razorpay_signature,
              orderId: response.razorpay_order_id,
              paymentId: response.razorpay_payment_id,
              amount: row?.price,
              courseId: row?.courseId,
            }
            this.userService.verifyPayment(verifyPayload).subscribe((res: any) => {
              void this.router.navigate(['/user/purchased-course-details']);
            }, (err) => {
              throw new Error('Payment Verififcation Failed');
            })
          })
        },
        prefill: {
          name: this.userDetails.fullName,
          email: this.userDetails.email,
          contact: this.userDetails.mobileNumber
        },
        theme: {
          color: '#3399cc'
        }
      };

      const rzp = new Razorpay(razorpayOptions);
      rzp.open();
    }, (err) => {
      throw new Error('Error in Order Creation');
    })
  }
}
