import { Component } from '@angular/core';
import { faBars, faBriefcase, faClock, faFileAlt, faHome, faList, faSignInAlt } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  faBriefcase = faBriefcase;
  faBars = faBars;
  faHome = faHome;
  faClock = faClock;
  faList = faList;
  faFileAlt = faFileAlt;
  faSignInAlt = faSignInAlt;
}
