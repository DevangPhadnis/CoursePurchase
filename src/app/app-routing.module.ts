import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from '../service/authentication.guard';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  {
    path: 'login',
    canActivate: [AuthenticationGuard],
    loadChildren: ()=> import('../login/login.module').then((m) => m.LoginModule),
  },
  {
    path: '',
    canActivate: [AuthenticationGuard],
    component: DashboardComponent,
    children: [
      {
        path: 'admin',
        loadChildren: () => 
          import('src/admin/admin.module').then(
            (m) => m.AdminModule
          )
      },
      {
        path: 'user',
        loadChildren: () => 
          import('src/user/user.module').then(
            (m) => m.UserModule
          )
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
