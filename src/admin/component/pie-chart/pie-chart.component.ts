import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss']
})
export class PieChartComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.fetchSalesByGenre().subscribe((res: any) => {
      const COLORS = [
        '#42A5F5', '#66BB6A', '#FFA726', '#AB47BC', '#FF7043', '#26C6DA',
        '#EF5350', '#8E24AA', '#29B6F6', '#66BB6A', '#FFA726', '#8D6E63',
        '#FFB74D', '#BA68C8', '#4DB6AC', '#E57373'
      ];
      
      this.pieChartData = {
        labels: Object.keys(res.data),
        datasets: [
          {
            data: Object.values(res.data),
            backgroundColor: COLORS.slice(0, res.data.length)
          }
        ]
      }
      this.showCharts = true;
    })
  }

  public pieChartType: ChartType = 'pie';

  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top'
      }
    },
    aspectRatio: 2
  };

  public pieChartData: any = {
    labels: [],
    datasets: []
  };

  showCharts: boolean = false;
    // labels: [],
    // datasets: [
    //   { data: [], backgroundColor: [] }
    // ]
}
