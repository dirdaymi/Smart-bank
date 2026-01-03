import { Routes } from '@angular/router';
import { UserHomeComponent } from './components/user/user-home/user-home';
import { LoginComponent } from './components/auth/login/login';
import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard';
import { UserProfileComponent } from './components/user/user-profile/user-profile';
import { UserManagementComponent } from './components/admin/user-management/user-management';
import { TransactionsComponent } from './components/admin/transactions/transactions';
import { AdminLayoutComponent } from './components/admin/admin-layout/admin-layout'; // <--- Import Layout
import { AccountOperationsComponent } from './components/admin/account-operations/account-operations';
import { AccountsComponent } from './components/admin/accounts/accounts';

// LE MOT-CLÉ "export" EST OBLIGATOIRE ICI
export const routes: Routes = [
  { path: '', component: UserHomeComponent },
  { path: 'login', component: LoginComponent },

  // NOUVELLE STRUCTURE ADMIN
  {
    path: 'admin',
    component: AdminLayoutComponent, // Le cadre avec le menu
    children: [
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: 'users', component: UserManagementComponent },
      { path: 'transactions', component: TransactionsComponent },
      { path: 'operations', component: AccountOperationsComponent },
      { path: 'accounts', component: AccountsComponent },
    ]
  },

  { path: 'user/profile', component: UserProfileComponent }
];
