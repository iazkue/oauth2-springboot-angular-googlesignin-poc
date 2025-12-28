import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthorizedComponent } from "./components/authorized/authorized.component";
import { HomeComponent } from "./components/home/home.component";

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'authorized', component: AuthorizedComponent },
    { path: 'user', loadComponent: () => import('./components/user/user.component').then(m => m.UserComponent) },
    { path: 'admin', loadComponent: () => import('./components/admin/admin.component').then(m => m.AdminComponent) },
    { path: 'logout', loadComponent: () => import('./components/logout/logout.component').then(m => m.LogoutComponent) },
    { path: '**', redirectTo: '', pathMatch: 'full' },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule { }
