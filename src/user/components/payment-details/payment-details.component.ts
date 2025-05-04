import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { UserService } from 'src/user/service/user.service';

@Component({
  selector: 'app-payment-details',
  templateUrl: './payment-details.component.html',
  styleUrls: ['./payment-details.component.scss']
})
export class PaymentDetailsComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.fetchPaymentDetails().subscribe((res: any) => {
      const rows: [] = res.data.map((row: any) => {
        return {
          title: row?.title,
          description: row?.description,
          genre: row?.genre,
          transactionId: row?.transactionId,
          transactionAmount: row?.transactionAmount,
          paymentMode: row?.paymentMode,
          paymentDate: moment(new Date(row?.paymentDate)).format("DD-MM-YYYY"),
          status: row?.status,
          receiptAttachmentId: row?.receiptAttachmentId
        }
      })
      this.filteredRows = [...rows];
    })
  }

  filteredRows = [];
  onDownloadClick(event: Event, rowDetails: any): void {
    event.preventDefault();
    this.userService.dowloadAttachment(rowDetails?.receiptAttachmentId ? rowDetails?.receiptAttachmentId : -1).subscribe((res: any) => {
      const byteCharacters = atob(res.data.fileBytes); // Base64 decode
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob(
        [byteArray], { type: res.data.contentType }
      )

      const url = window.URL.createObjectURL(blob);

      const aElement = document.createElement('a');
      aElement.href = url;
      aElement.download = res.data.originalFileName;
      aElement.click();

      window.URL.revokeObjectURL(url);
    })
  }
}
