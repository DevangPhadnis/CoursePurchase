import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  saveUserDetails(payload: any): Observable<any> {
    return this.http.post('/user/save-details', payload);
  }

  fetchUnPaidCourseList(pageNumber: number, rowCount: number) {
    const params = new HttpParams().set('pageNumber', pageNumber).set('rowCount', rowCount);
    return this.http.get('/user/course-details', { params: params });
  }

  fetchPurchasedCourseList(pageNumber: number, rowCount: number) {
    const params = new HttpParams().set('pageNumber', pageNumber).set('rowCount', rowCount);
    return this.http.get('/user/purchased-course-details', { params: params });
  }

  createPaymentOrder(payload: any) {
    return this.http.post('/user/create-payment-order', payload);
  }

  verifyPayment(payload: any) {
    return this.http.post('/user/verify-payment', payload);
  }

  fetchPaymentDetails() {
    return this.http.get('/user/fetch-payment-details');
  }

  dowloadAttachment(attachmentId: any) {
    const params = new HttpParams().set('attachmentId', attachmentId);
    return this.http.get('/user/download-attachment', { params: params });
  }
}
