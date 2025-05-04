import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError, finalize, Observable, tap, throwError } from 'rxjs';
import { AuthenticationService } from 'src/login/service/authentication.service';
import { LoaderService } from 'src/service/loader.service';
import { MessageService } from 'primeng/api';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService,
    private loaderService: LoaderService,
    private messageService: MessageService
  ) {}
  
  private startUrl: String = "http://localhost:8080";

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.loaderService.show()
    const token = this.authenticationService.getToken();
    const url = request?.url?.startsWith('http') ? request?.url : this.startUrl + request?.url;
    const cloned = request.clone({
      url: url,
      setHeaders: token ? { Authorization: `Bearer ${token}`} : {},
    })
    return next.handle(cloned).pipe(tap((event: HttpEvent<any>) => {
      if(event instanceof HttpResponse) {
        if(event.status == 200 || event.status == 201) {
          if(request?.url != '/admin/user-profile') {
            this.messageService.add({ severity: 'success', summary: 'Operation Successful', detail: event.body?.message || "Operation Successful" });
          }
        }
        this.loaderService.hide();
      }
    }), catchError((error: HttpErrorResponse) => {
      if (error.status === 500 || error.status === 401 || error.status === 403) {
        this.messageService.add({
          severity: 'error',
          summary: 'Backend Error',
          detail: error.error?.message || 'Something went wrong'
        });
        if(error.status == 401) {
          this.authenticationService.logout();
        }
      }
      this.loaderService.hide();
      return throwError(() => error);
    }));
  }
}
