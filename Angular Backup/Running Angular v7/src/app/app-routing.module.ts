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
import { BonusAnalyticsComponent } from './components/bonus/bonus-analytics/bonus-analytics.component';
import { BonusCreateComponent } from './components/bonus/bonus-create/bonus-create.component';
import { BonusHistoryComponent } from './components/bonus/bonus-history/bonus-history.component';
import { BonusManagementComponent } from './components/bonus/bonus-management/bonus-management.component';
import { BonusReportComponent } from './components/bonus/bonus-report/bonus-report.component';
import { BonusSummaryComponent } from './components/bonus/bonus-summary/bonus-summary.component';
import { CreateAdvanceSalaryComponent } from './components/advance-salary/create-advance-salary/create-advance-salary.component';
import { ListAdvanceSalaryComponent } from './components/advance-salary/list-advance-salary/list-advance-salary.component';
import { EditAdvanceSalaryComponent } from './components/advance-salary/edit-advance-salary/edit-advance-salary.component';
import { ViewAdvanceSalaryComponent } from './components/advance-salary/view-advance-salary/view-advance-salary.component';
import { FilterAdvanceSalaryComponent } from './components/advance-salary/filter-advance-salary/filter-advance-salary.component';

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
  {
    path: 'bonus',
    children: [
      { path: 'analytics', component: BonusAnalyticsComponent },
      { path: 'create', component: BonusCreateComponent },
      { path: 'history', component: BonusHistoryComponent },
      { path: 'management', component: BonusManagementComponent },
      { path: 'report', component: BonusReportComponent },
      { path: 'summary', component: BonusSummaryComponent },
    ],
  },
   {
    path: 'advance-salary',
    children: [
      { path: 'create', component: CreateAdvanceSalaryComponent },
      { path: 'list', component: ListAdvanceSalaryComponent },
      { path: 'edit/:id', component: EditAdvanceSalaryComponent },
      { path: 'view/:id', component: ViewAdvanceSalaryComponent },
      { path: 'filter', component: FilterAdvanceSalaryComponent },
    ],
  },
  { path: '**', component: NotfoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
