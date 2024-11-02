import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../models/user.model';
import {
  faUser,
  faCalendar,
  faMapPin,
  faLayerGroup,
  faDollarSign,
  faCommentDots,
  faBriefcase,
  faBars,
  faHome,
  faClock,
  faList,
  faFileAlt,
  faSignInAlt,
  faInfoCircle,
  faCheckCircle,
  faHistory,
  faChartPie,
  faEdit,
  faUserPlus,
  faCalendarPlus,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'], // Updated to match the correct property name
})
export class SidebarComponent implements OnInit {
  faBriefcase = faBriefcase;
  faBars = faBars;
  faHome = faHome;
  faClock = faClock;
  faList = faList;
  faFileAlt = faFileAlt;
  faSignInAlt = faSignInAlt;
  faUser = faUser;
  faCalendar = faCalendar;
  faMapPin = faMapPin;
  faLayerGroup = faLayerGroup;
  faDollarSign = faDollarSign;
  faCommentDots = faCommentDots;
  faInfoCircle = faInfoCircle; // Added icon
  faCheckCircle = faCheckCircle; // Added icon
  faHistory = faHistory; // Added icon
  faChartPie = faChartPie; // Added icon
  faEdit = faEdit; // Added icon
  faUserPlus = faUserPlus; // Added property
  faCalendarPlus = faCalendarPlus;

  userRole: string | null = '';
  currentUser: UserModel | null = null;

  // constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // this.authService.currentUser$.subscribe((user) => {
    //   this.currentUser = user;
    //   this.userRole = user?.role || null;
    // });
  }
}
