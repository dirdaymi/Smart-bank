import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <--- 1. Import ChangeDetectorRef
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../_services/admin';
import { BaseChartDirective } from 'ng2-charts';
// 2. Import des éléments nécessaires de Chart.js
import { ChartConfiguration, ChartData, ChartType, Chart, registerables } from 'chart.js';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './admin-dashboard.html'
})
export class AdminDashboardComponent implements OnInit {
  stats: any = { totalUsers: 0, totalFunds: 0, totalTransactions: 0 };

  // Configuration du Graphique
  public pieChartOptions: ChartConfiguration['options'] = { responsive: true };
  public pieChartData: ChartData<'pie', number[], string | string[]> = {
    labels: [ 'Fonds Totaux', 'Crédits', 'Débits' ],
    datasets: [ { data: [ 0, 0, 0 ] } ]
  };
  public pieChartType: ChartType = 'pie';

  constructor(
      private adminService: AdminService,
      private cd: ChangeDetectorRef // <--- 3. Injection du détecteur
  ) {
    // 4. Enregistrement global des composants de Chart.js (obligatoire en v3+)
    Chart.register(...registerables);
  }

  ngOnInit() {
    this.adminService.getStats().subscribe(data => {
      this.stats = data;

      // Mise à jour des données du graphique
      this.pieChartData = {
        labels: ['Fonds (k€)', 'Transactions'],
        datasets: [{
          data: [ data.totalFunds / 1000, data.totalTransactions ],
          backgroundColor: ['#10B981', '#3B82F6', '#EF4444'] // Optionnel : couleurs (Vert, Bleu, Rouge)
        }]
      };

      // 5. On force la mise à jour de la vue pour éviter l'erreur NG0100
      this.cd.detectChanges();
    });
  }
}