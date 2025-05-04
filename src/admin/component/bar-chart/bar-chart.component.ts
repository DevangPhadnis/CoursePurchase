import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})
export class BarChartComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.fetchTopSellingCourses().subscribe((res: any) => {
      this.barChartData = {
        labels: res.data.map((r: any) => r.title),
        datasets: [
          {
            label: 'Purchases',
            data: res.data.map((r: any) => r.count)
          }
        ]
      };
    })
    // this.barChartData.datasets[0].data = [this.chartDetails.active, this.chartDetails.inActive];
    // this.isShow = true;
  }

  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    aspectRatio: 6
  };
  
  isShow = false;
  public barChartLabels: string[] = ['Active', 'In Active'];
  
  public barChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [
      {
        label: '',
        data: [],
        backgroundColor: '#42A5F5'
      }
    ]
  };
  
  public barChartType: ChartType = 'bar';
}
