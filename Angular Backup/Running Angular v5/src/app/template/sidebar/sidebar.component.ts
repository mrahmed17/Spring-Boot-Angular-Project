import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../models/user.model';
import { faBars, faBriefcase, faClock, faFileAlt, faHome, faList, faSignInAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent implements OnInit {
  faBriefcase = faBriefcase;
  faBars = faBars;
  faHome = faHome;
  faClock = faClock;
  faList = faList;
  faFileAlt = faFileAlt;
  faSignInAlt = faSignInAlt;

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