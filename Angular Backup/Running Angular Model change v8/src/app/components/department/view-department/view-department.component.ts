import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DepartmentService } from '../../../services/department.service';
import { DepartmentModel } from '../../../models/department.model';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-view-department',
  templateUrl: './view-department.component.html',
  styleUrls: ['./view-department.component.css'],
})
export class ViewDepartmentComponent implements OnInit {
  departmentId!: number;
  department!: DepartmentModel;

  constructor(
    private departmentService: DepartmentService,
    private route: ActivatedRoute,
    private notification: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.departmentId = this.route.snapshot.params['id'];
    this.loadDepartment();
  }

  loadDepartment(): void {
    this.departmentService.getDepartmentById(this.departmentId).subscribe({
      next: (data) => {
        this.department = data;
      },
      error: (error) => {
        console.error('Error fetching department', error);
        this.notification.showNotify('Error fetching department', 'error');
      },
    });
  }

  goBack(): void {
    this.router.navigate(['/department/list']);
  }
}
