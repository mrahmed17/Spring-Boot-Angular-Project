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
import { ListUserComponent } from './administration/user/list-user/list-user.component';
import { NotfoundComponent } from './errorhandling/notfound/notfound.component';
import { UserDetailComponent } from './administration/user/user-detail/user-detail.component';
import { EditUserComponent } from './administration/user/edit-user/edit-user.component';
import { ApplyLeaveComponent } from './components/leave/apply-leave/apply-leave.component';
import { DetailsLeaveComponent } from './components/leave/details-leave/details-leave.component';
import { LeaveApprovalComponent } from './components/leave/leave-approval/leave-approval.component';
import { LeaveHistoryComponent } from './components/leave/leave-history/leave-history.component';
import { LeaveSummaryComponent } from './components/leave/leave-summary/leave-summary.component';
import { ListLeaveComponent } from './components/leave/list-leave/list-leave.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'sidebar', component: SidebarComponent },
  {
    path: 'attendance',
    children: [
      { path: 'create', component: CreateAttendanceComponent },
      { path: 'report', component: ReportAttendanceComponent },
      { path: 'view/:id', component: ViewAttendanceComponent },
      { path: 'list', component: ListAttendanceComponent },
    ],
  },
  {
    path: 'user',
    children: [
      { path: 'create', component: CreateUserComponent },
      { path: 'view/:id', component: UserDetailComponent },
      { path: 'list', component: ListUserComponent },
      { path: 'edit/:id', component: EditUserComponent },
    ],
  },
  {
    path: 'leave',
    children: [
      { path: 'apply', component: ApplyLeaveComponent },
      { path: 'details/:id', component: DetailsLeaveComponent },
      { path: 'approval', component: LeaveApprovalComponent },
      { path: 'history', component: LeaveHistoryComponent },
      { path: 'summary', component: LeaveSummaryComponent },
      { path: 'list', component: ListLeaveComponent },
    ],
  },
  { path: '**', component: NotfoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
