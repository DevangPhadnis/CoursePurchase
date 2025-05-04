import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.fetchRevenueByGenre().subscribe((res: any) => {
      const COLORS = ['#AEDFF7', '#C3FDB8', '#FFECB3', '#F8BBD0', '#FFE0B2', '#B2EBF2'];

      this.revenueByGenreData = {
        labels: Object.keys(res.data),
        datasets: [
          {
            data: Object.values(res.data),
            backgroundColor: COLORS, 
            hoverBackgroundColor: COLORS
          }
        ]
      }
      this.showCharts = true;
    })
  }

  public chartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
        labels: {
          color: '#000000'
        }
      }
    },
    aspectRatio: 2
  };
  
  showCharts = false;
  
  revenueByGenreData: ChartConfiguration['data'] = {
    labels: [],
    datasets: [{
      data: [],
      backgroundColor: []
    }]
  };
  
  public revenueByGenreType: ChartType = 'doughnut';
}
