import { Component, OnInit } from '@angular/core';
import { UserModel } from '../../../models/user.model';
import { RoleEnum } from '../../../models/role.enum';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css',
})
export class UpdateUserComponent implements OnInit {
  user: UserModel = new UserModel();
  roles = Object.values(RoleEnum);
  id!: number;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.userService.getUserById(this.id).subscribe((data) => {
      this.user = data;
    });
  }

  updateUser(): void {
    this.userService.updateUser(this.id, this.user).subscribe(() => {
      this.router.navigate(['/user/list']);
    });
  }
}