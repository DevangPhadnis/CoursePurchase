import { AfterViewInit, Component, NgZone, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/login/service/authentication.service';
import * as CryptoJS from 'crypto-js';
import { HttpClient } from '@angular/common/http';
import { LoaderService } from 'src/service/loader.service';

declare var google: any;

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit, AfterViewInit {

  constructor(private authenticationService: AuthenticationService,
    private router: Router,
    private http: HttpClient,
    private loaderService: LoaderService,
    private ngZone: NgZone
  ) { }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    google.accounts.id.initialize({
      client_id: 'abc',
      callback: this.handleCredentialResponse.bind(this),
    });

    google.accounts.id.renderButton(
      document.querySelector('.g_id_signin'),
      { theme: 'outline', size: 'large', shape: 'pill', width: '100%' }
    );
  }

  handleCredentialResponse(response: any): void {
    this.ngZone.run(() => {
      this.loaderService.show();
      const idToken = response.credential;
      
      this.authenticationService.loginViaOAuth(idToken).subscribe({
        next: (res: any) => {
          const jwtToken = res?.data;
          if (jwtToken) {
            this.authenticationService.setToken(jwtToken);
  
            this.authenticationService.getProfileDetails().subscribe({
              next: (userRes: any) => {
                const role = userRes?.data?.role;
                if (role) {
                  this.authenticationService.setRole(role);
                  this.navigateToRoleBasedPage(role);
                } else {
                  console.error('Role missing from profile!');
                }
                this.loaderService.hide();
              },
              error: (err) => {
                console.error('Error fetching profile', err);
                this.loaderService.hide();
              }
            });
          } else {
            console.error('Token not found');
            this.loaderService.hide();
          }
        },
        error: (err) => {
          console.error('Error during OAuth login', err);
          this.loaderService.hide();
        }
      });
    });
  }
  
  private navigateToRoleBasedPage(role: string): void {
    console.log('Navigating user with role:', role);
  
    if (role === 'USER') {
      void this.router.navigate(['/user'], { replaceUrl: true });
    } else if (role === 'ADMIN') {
      void this.router.navigate(['/admin'], { replaceUrl: true });
    } else {
      console.error('Unknown role:', role);
    }
  }
  
  
  passphrase: string = "EmsTestingTeam@2025";

  login = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  hidePassword: boolean = true;
  onLogin(): void {
    /* const key = CryptoJS.SHA256(this.passphrase);
    const iv = CryptoJS.enc.Hex.parse("00000000000000000000000000000000");
    const rawPassword: string = this.login?.controls?.password?.value || '';
    const encryptedPassword = CryptoJS.AES.encrypt(rawPassword, key,
      {
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
        iv: iv
      }
    ).toString(); */
    const credentials = {
      userName: this.login?.controls?.username?.value,
      password: this.login?.controls?.password?.value,
    }
    this.authenticationService.login(credentials).subscribe((res: any) => {
      this.authenticationService.setToken(res?.data);
      this.authenticationService.getProfileDetails().subscribe((res: any) => {
        if(res.data.role == "EMPLOYEE" && this.authenticationService?.isLoggedIn()) {
          this.authenticationService.setRole(res.data.role);
          void this.router.navigate(['/user'], { replaceUrl: true });
        }
        else if(res.data.role == "ADMIN" && this.authenticationService?.isLoggedIn()) {
          this.authenticationService.setRole(res.data.role);
          void this.router.navigate(['/admin'], { replaceUrl: true });
        }
        else {
          console.log("invalid credentials");
        }
      })
    })
  }

  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }
}
