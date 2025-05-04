import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../login/service/authentication.service';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    
    if(state.url.includes('/login') && this.authenticationService.isLoggedIn()) {
      const role = this.authenticationService.getRole();
      if(role == 'ADMIN') return this.router.parseUrl('/admin');
      else if(role == 'USER') return this.router.parseUrl('/user');
    }

    if(!this.authenticationService.isLoggedIn() && state.url !== '/login') return this.router.parseUrl('/login');
    
    if(state.url.includes('/admin') && this.authenticationService.getRole() !== 'ADMIN') return this.router.parseUrl("/user");

    if(state.url.includes('/user') && this.authenticationService.getRole() !== 'USER') return this.router.parseUrl("/admin");

    return true;
  }
  
}
