import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
// CORRECTION ICI : on importe depuis 'admin' tout court
import { AdminService } from '../../../_services/admin';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transactions.html',
  styleUrls: ['./transactions.css']
})
export class TransactionsComponent implements OnInit {
  transactions: any[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.adminService.getTransactions().subscribe({
      next: (data: any) => {
        this.transactions = data;
      },
      error: (err: any) => console.error(err)
    });
  }
}
