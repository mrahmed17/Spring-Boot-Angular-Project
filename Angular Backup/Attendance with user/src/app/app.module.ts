import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateAttendanceComponent } from './components/attendance/create-attendance/create-attendance.component';
import { ListAttendanceComponent } from './components/attendance/list-attendance/list-attendance.component';
import { ViewAttendanceComponent } from './components/attendance/view-attendance/view-attendance.component';
import { ReportAttendanceComponent } from './components/attendance/report-attendance/report-attendance.component';
import { CreateUserComponent } from './administration/user/create-user/create-user.component';
import { UpdateUserComponent } from './administration/user/update-user/update-user.component';
import { ListUserComponent } from './administration/user/list-user/list-user.component';
import { ViewUserComponent } from './administration/user/view-user/view-user.component';
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
import { NotificationComponent } from './template/notification/notification.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ErrorComponent } from './errorhandling/error/error.component';
import { NotfoundComponent } from './errorhandling/notfound/notfound.component';
import { HeaderComponent } from './template/header/header.component';
import { ForgetpasswordComponent } from './administration/forgetpassword/forgetpassword.component';
import { LoginComponent } from './administration/login/login.component';
import { LogoutComponent } from './administration/logout/logout.component';


@NgModule({
  declarations: [
    AppComponent,
    CreateAttendanceComponent,
    ListAttendanceComponent,
    ViewAttendanceComponent,
    ReportAttendanceComponent,
    CreateUserComponent,
    UpdateUserComponent,
    ListUserComponent,
    ViewUserComponent,
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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    HttpClientModule,
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(withFetch()),   
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}