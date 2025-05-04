import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from 'src/login/service/authentication.service';
import { LoaderService } from 'src/service/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'CourseManagement';

  isLoading: Observable<boolean>;

  constructor(private loaderService: LoaderService, private authService: AuthenticationService, private router: Router) {
    this.isLoading = this.loaderService.isLoading;
  }
}
