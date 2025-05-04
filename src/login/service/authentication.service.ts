import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
    private router: Router
  ) { }

  private tokenKey: string = "token";

  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  private jwtTokenSubject = new BehaviorSubject<string | null>(localStorage.getItem(this.tokenKey));

  private roleKey: string = "role";

  login(credentials: any): Observable<any> {
    return this.http.post('/admin/login', credentials).pipe(tap((response: any) => {
      if(response.data) {
        localStorage.setItem(this.tokenKey, response.data);
        this.loggedIn.next(true);
      }
      else {
        throw new Error('Token not found');
      }
    }))
  }

  hasToken(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.jwtTokenSubject.next(token);
    this.loggedIn.next(true);
  }

  getToken(): string | null {
    return this.jwtTokenSubject.value;
  }

  isLoggedIn(): boolean {
    return this.loggedIn.value;
  }

  getLoginStatus(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.roleKey);
    this.loggedIn.next(false);
    this.jwtTokenSubject.next(null);
    this.router.navigate(['/login']);
  }

  getProfileDetails() {
    return this.http.get('/admin/user-profile');
  }

  setRole(role: string): void {
    localStorage.setItem(this.roleKey, role);
  }

  getRole(): string {
    return String(localStorage.getItem(this.roleKey));
  }

  loginViaOAuth(tokenId: string) {
    return this.http.post('/auth/oauth/google', { tokenId: tokenId });
  }
}
