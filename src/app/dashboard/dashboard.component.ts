import { PlatformLocation } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/login/service/authentication.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private router: Router,
    private authenticationService: AuthenticationService,
    private platformLocation: PlatformLocation
  ) { 
    history.pushState(null, '', location.href);
    this.platformLocation.onPopState(() => {
      history.pushState(null, '', location.href);
    })
  }

  ngOnInit(): void {
    this.authenticationService.getProfileDetails().subscribe((res: any) => {
      this.role = res.data.role;
      if(res.data.role == "USER") {
        if(!res.data.mobileNumber) void this.router.navigate(['/user/user-details']);
        else void this.router.navigate(['/user/course-details']);
      }
    })
  }

  isSidebarCollapsed = false;
  role: string = "";

  toggleSidebar() {
    this.isSidebarCollapsed = !this.isSidebarCollapsed;
  }

  onRedirect(event: Event, action: string): void {
    event.preventDefault();
    if(action == 'courseDetail') {
      if(this.role == 'ADMIN')
        this.router.navigate(['/admin/course-details']);
    }

    if(action == 'logout') {
      this.authenticationService.logout();
    }

    if(action == 'home') {
      void this.router.navigate(['/admin']);
    }

    if(action == 'userDetail') {
      void this.router.navigate(['/user/user-details']);
    }

    if(action == 'courseDetailUser') {
      void this.router.navigate(['/user/course-details']);
    }

    if(action == 'courseDetailPurchased') {
      void this.router.navigate(['/user/purchased-course-details']);
    }

    if(action == 'paymentDetails') {
      void this.router.navigate(['/user/payment-details']);
    }
  }
}
