import { Component } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { RoleEnum } from '../../../models/role.enum';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';
// import {
//   faArrowLeft,
//   faCalendarAlt,
//   faCalendarDay,
//   faDollarSign,
//   faEnvelope,
//   faHome,
//   faIdCard,
//   faImage,
//   faPhone,
//   faPlusCircle,
//   faUser,
//   faUserPlus,
//   faUserTag,
//   faVenusMars,
// } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css'],
})
  
export class CreateUserComponent {
  // faUserPlus = faUserPlus;
  // faHome = faHome;
  // faVenusMars = faVenusMars;
  // faCalendarAlt = faCalendarAlt;
  // faUser = faUser;
  // faIdCard = faIdCard;
  // faCalendarDay = faCalendarDay;
  // faDollarSign = faDollarSign;
  // faEnvelope = faEnvelope;
  // faPhone = faPhone;
  // faUserTag = faUserTag;
  // faArrowLeft = faArrowLeft;
  // faImage = faImage;
  // faPlusCircle = faPlusCircle;

  selectedFile: File | null = null;
  user: UserModel = new UserModel();

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    if (this.selectedFile) {
      this.userService.createUser(this.user, this.selectedFile).subscribe({
        next: (res) => {
          
          console.log('User Added succesfully', res);
          this.router.navigate(['/user/list']);
        },
        error: (err) => {
          console.error('User Not Added', err);
        },
      });
    } else {
      alert('Please Select an image');
    }
  }
}
