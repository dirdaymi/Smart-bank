import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../../_services/customer.service';
import { Customer } from '../../../_models/core-models';

@Component({
  selector: 'app-user-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-management.html'
})
export class UserManagementComponent implements OnInit {
  customers: Customer[] = [];
  newCustomer: Customer = { id: 0, name: '', email: '' };

  keyword: string = '';
  errorMessage: string = '';

  constructor(private customerService: CustomerService) {}

  ngOnInit() {
    this.loadCustomers();
  }

  loadCustomers() {
    this.customerService.getCustomers().subscribe({
      next: (data) => this.customers = data,
      // CORRECTION : On retire 'err' car on ne l'utilise pas
      error: () => this.errorMessage = "Impossible de charger les clients"
    });
  }

  handleSearch() {
    if (this.keyword.trim()) {
      this.customerService.searchCustomers(this.keyword).subscribe({
        next: (data) => this.customers = data,
        // CORRECTION : On retire 'err'
        error: () => this.errorMessage = "Erreur recherche"
      });
    } else {
      this.loadCustomers();
    }
  }

  saveCustomer() {
    // 1. On copie l'objet pour ne pas toucher au formulaire
    const customerToSend = { ...this.newCustomer };

    // 2. CORRECTION CRITIQUE : Si c'est une création (id=0), on supprime le champ ID
    // Cela force Java à générer un nouvel ID auto-incrémenté
    if (customerToSend.id === 0) {
      // @ts-ignore
      delete customerToSend.id;
    }

    this.customerService.saveCustomer(customerToSend).subscribe({
      next: () => {
        this.loadCustomers(); // Recharger la liste
        this.newCustomer = { id: 0, name: '', email: '' }; // Vider le formulaire
        alert("Utilisateur ajouté avec succès !");
      },
      error: (err) => {
        console.error(err); // Affiche l'erreur réelle dans la console (F12)
        // Affiche un message plus précis si possible
        const msg = err.error?.message || "Erreur technique (Vérifiez que l'email n'existe pas déjà)";
        alert("Échec : " + msg);
      }
    });
  }

  deleteCustomer(id: number) {
    if(confirm("Voulez-vous vraiment supprimer ce client ?")) {
      this.customerService.deleteCustomer(id).subscribe(() => this.loadCustomers());
    }
  }
}