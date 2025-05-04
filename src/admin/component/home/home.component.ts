import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    /* this.adminService.getChartData().subscribe((res: any) => {
      this.chartDetails = res.data;
      this.showCharts = true;
    }) */
  }

  chartDetails: any;
  showCharts: boolean = true;
}
