import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAttendanceComponent } from './components/attendance/create-attendance/create-attendance.component';
import { ListAttendanceComponent } from './components/attendance/list-attendance/list-attendance.component';
import { ViewAttendanceComponent } from './components/attendance/view-attendance/view-attendance.component';
import { ReportAttendanceComponent } from './components/attendance/report-attendance/report-attendance.component';
import { CreateUserComponent } from './administration/user/create-user/create-user.component';
import { ListUserComponent } from './administration/user/list-user/list-user.component';

import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
  provideHttpClient,
  withFetch,
} from '@angular/common/http';
import { BreadcrumbComponent } from './template/breadcrumb/breadcrumb.component';
import { DashboardComponent } from './template/dashboard/dashboard.component';
import { HomeComponent } from './template/home/home.component';
import { SidebarComponent } from './template/sidebar/sidebar.component';
import { NotificationComponent } from './errorhandling/notification/notification.component';

import { RouterModule } from '@angular/router';
import { ErrorComponent } from './errorhandling/error/error.component';
import { NotfoundComponent } from './errorhandling/notfound/notfound.component';
import { HeaderComponent } from './template/header/header.component';
import { ForgetpasswordComponent } from './administration/forgetpassword/forgetpassword.component';
import { LoginComponent } from './administration/login/login.component';
import { LogoutComponent } from './administration/logout/logout.component';
import { UserDetailComponent } from './administration/user/user-detail/user-detail.component';
import { EditUserComponent } from './administration/user/edit-user/edit-user.component';
import { ApplyLeaveComponent } from './components/leave/apply-leave/apply-leave.component';
import { ListLeaveComponent } from './components/leave/list-leave/list-leave.component';
import { DetailsLeaveComponent } from './components/leave/details-leave/details-leave.component';
import { LeaveApprovalComponent } from './components/leave/leave-approval/leave-approval.component';
import { LeaveSummaryComponent } from './components/leave/leave-summary/leave-summary.component';
import { LeaveHistoryComponent } from './components/leave/leave-history/leave-history.component';
import { BonusManagementComponent } from './components/bonus/bonus-management/bonus-management.component';
import { BonusSummaryComponent } from './components/bonus/bonus-summary/bonus-summary.component';
import { BonusHistoryComponent } from './components/bonus/bonus-history/bonus-history.component';
import { BonusReportComponent } from './components/bonus/bonus-report/bonus-report.component';
import { BonusCreateComponent } from './components/bonus/bonus-create/bonus-create.component';
import { BonusAnalyticsComponent } from './components/bonus/bonus-analytics/bonus-analytics.component';
import { CreateAdvanceSalaryComponent } from './components/advance-salary/create-advance-salary/create-advance-salary.component';
import { ListAdvanceSalaryComponent } from './components/advance-salary/list-advance-salary/list-advance-salary.component';
import { EditAdvanceSalaryComponent } from './components/advance-salary/edit-advance-salary/edit-advance-salary.component';
import { ViewAdvanceSalaryComponent } from './components/advance-salary/view-advance-salary/view-advance-salary.component';
import { CreateSalaryComponent } from './components/salary/create-salary/create-salary.component';
import { ListSalaryComponent } from './components/salary/list-salary/list-salary.component';
import { EditSalaryComponent } from './components/salary/edit-salary/edit-salary.component';
import { ReportAdvanceSalaryComponent } from './components/advance-salary/report-advance-salary/report-advance-salary.component';
import { CreateCompanyComponent } from './components/company/create-company/create-company.component';
import { UpdateCompanyComponent } from './components/company/update-company/update-company.component';
import { ViewCompanyComponent } from './components/company/view-company/view-company.component';
import { ListCompanyComponent } from './components/company/list-company/list-company.component';
import { ReportCompanyComponent } from './components/company/report-company/report-company.component';
import { CreateBranchComponent } from './components/branch/create-branch/create-branch.component';
import { UpdateBranchComponent } from './components/branch/update-branch/update-branch.component';
import { ViewBranchComponent } from './components/branch/view-branch/view-branch.component';
import { ListBranchComponent } from './components/branch/list-branch/list-branch.component';
import { ReportBranchComponent } from './components/branch/report-branch/report-branch.component';
import { CreateDepartmentComponent } from './components/department/create-department/create-department.component';
import { UpdateDepartmentComponent } from './components/department/update-department/update-department.component';
import { ViewDepartmentComponent } from './components/department/view-department/view-department.component';
import { ListDepartmentComponent } from './components/department/list-department/list-department.component';
import { ReportDepartmentComponent } from './components/department/report-department/report-department.component';


@NgModule({
  declarations: [
    AppComponent,
    CreateAttendanceComponent,
    ListAttendanceComponent,
    ViewAttendanceComponent,
    ReportAttendanceComponent,
    CreateUserComponent,
    ListUserComponent,
    BreadcrumbComponent,
    DashboardComponent,
    HomeComponent,
    SidebarComponent,
    NotificationComponent,
    ErrorComponent,
    NotfoundComponent,
    HeaderComponent,
    ForgetpasswordComponent,
    LoginComponent,
    LogoutComponent,
    UserDetailComponent,
    EditUserComponent,
    ApplyLeaveComponent,
    ListLeaveComponent,
    DetailsLeaveComponent,
    LeaveApprovalComponent,
    LeaveSummaryComponent,
    LeaveHistoryComponent,
    BonusManagementComponent,
    BonusSummaryComponent,
    BonusHistoryComponent,
    BonusReportComponent,
    BonusCreateComponent,
    BonusAnalyticsComponent,
    CreateAdvanceSalaryComponent,
    ListAdvanceSalaryComponent,
    EditAdvanceSalaryComponent,
    ViewAdvanceSalaryComponent,
    CreateSalaryComponent,
    ListSalaryComponent,
    EditSalaryComponent,
    ReportAdvanceSalaryComponent,
    CreateCompanyComponent,
    UpdateCompanyComponent,
    ViewCompanyComponent,
    ListCompanyComponent,
    ReportCompanyComponent,
    CreateBranchComponent,
    UpdateBranchComponent,
    ViewBranchComponent,
    ListBranchComponent,
    ReportBranchComponent,
    CreateDepartmentComponent,
    UpdateDepartmentComponent,
    ViewDepartmentComponent,
    ListDepartmentComponent,
    ReportDepartmentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
    FontAwesomeModule,
  ],
  providers: [provideClientHydration(), provideHttpClient(withFetch())],
  bootstrap: [AppComponent],
})
export class AppModule {

}
