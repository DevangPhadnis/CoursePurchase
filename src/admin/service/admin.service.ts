import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  private chartSubject = new BehaviorSubject<any>(null);
  data$ = this.chartSubject.asObservable();

  saveCourseDetails(payload: any): Observable<any> {
    return this.http.post('/admin/add-courses', payload);
  }

  fetchCourseDetails(): Observable<any> {
    return this.http.get('/admin/fetch-course');
  }

  deleteCourseDetails(courseId: number): Observable<any> {
    const params = new HttpParams().set('courseId', courseId);
    return this.http.delete('/admin/delete-course', { params: params });
  }

  getChartData() {
    return this.http.get('/chart/dashboard');
  }

  fetchSalesByGenre() {
    return this.http.get('/admin/sales-by-genre');
  }

  fetchTopSellingCourses() {
    return this.http.get('/admin/top-courses');
  }

  fetchRevenueByGenre() {
    return this.http.get('/admin/revenue-by-genre');
  }
}
