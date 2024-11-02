import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../models/user.model';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent implements OnInit {
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