import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './template/home/home.component';
import { DashboardComponent } from './template/dashboard/dashboard.component';
import { SidebarComponent } from './template/sidebar/sidebar.component';
import { CreateAttendanceComponent } from './components/attendance/create-attendance/create-attendance.component';
import { ReportAttendanceComponent } from './components/attendance/report-attendance/report-attendance.component';
import { ViewAttendanceComponent } from './components/attendance/view-attendance/view-attendance.component';
import { ListAttendanceComponent } from './components/attendance/list-attendance/list-attendance.component';
import { CreateUserComponent } from './administration/user/create-user/create-user.component';
import { UpdateUserComponent } from './administration/user/update-user/update-user.component';
import { ViewUserComponent } from './administration/user/view-user/view-user.component';
import { ListUserComponent } from './administration/user/list-user/list-user.component';
import { NotfoundComponent } from './errorhandling/notfound/notfound.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'sidebar',
    component: SidebarComponent,
  },
  {
    path: 'attendance',
    children: [
      {
        path: 'create',
        component: CreateAttendanceComponent,
      },
      {
        path: 'report',
        component: ReportAttendanceComponent,
      },
      {
        path: 'view/:id',
        component: ViewAttendanceComponent,
      },
      {
        path: 'list',
        component: ListAttendanceComponent,
      },
    ],
  },
  {
    path: 'user',
    children: [
      {
        path: 'create',
        component: CreateUserComponent,
      },
      {
        path: 'edit/:id',
        component: UpdateUserComponent,
      },
      {
        path: 'view/:id',
        component: ViewUserComponent,
      },
      {
        path: 'list',
        component: ListUserComponent,
      },
    ],
  },
  { path: '**', component: NotfoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
