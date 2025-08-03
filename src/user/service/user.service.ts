import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

  fetchCourseUrl(courseId: number): Observable<any> {
    const params = new HttpParams().set('courseId', courseId);
    return this.http.get('/user/fetch-course-url', { params: params });
  }

  saveProgress(payload: any): Observable<any> {
    const headers = new HttpHeaders({'X-Skip-Loader': 'true'});
    const params = new HttpParams().set('courseId', payload.courseId).set('currentTime', payload?.currentTime);
    return this.http.post('/user/save-progress', null, { params: params, headers: headers });
  }

  fetchCourseProgress(courseId: number): Observable<any> {
    const params = new HttpParams().set('courseId', courseId);
    return this.http.get('/user/get-progress', { params: params });
  }
}
