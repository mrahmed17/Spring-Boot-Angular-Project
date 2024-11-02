import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DepartmentService } from '../../../services/department.service';
import { NotificationService } from '../../../services/notification.service';
import { DepartmentModel } from '../../../models/department.model';

@Component({
  selector: 'app-update-department',
  templateUrl: './update-department.component.html',
  styleUrls: ['./update-department.component.css'],
})
export class UpdateDepartmentComponent implements OnInit {
  departmentForm: FormGroup;
  departmentId!: number;
  departmentPhoto?: File;
  department!: DepartmentModel;

  constructor(
    private formBuilder: FormBuilder,
    private departmentService: DepartmentService,
    private notification: NotificationService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.departmentForm = this.formBuilder.group({
      departmentName: ['', Validators.required],
      numOfEmployees: [0, Validators.required],
      photo: [null],
    });
  }

  ngOnInit(): void {
    this.departmentId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadDepartment();
  }

  loadDepartment(): void {
    this.departmentService.getDepartmentById(this.departmentId).subscribe({
      next: (data) => {
        this.department = data;
        this.departmentForm.patchValue({
          departmentName: this.department.departmentName,
          numOfEmployees: this.department.numOfEmployees,
        });
      },
      error: (error) => {
        console.error('Error loading department', error);
        this.notification.showNotify('Error loading department', 'error');
      },
    });
  }

  onPhotoSelected(event: any): void {
    this.departmentPhoto = event.target.files[0];
  }

  onSubmit(): void {
    const updatedDepartment: DepartmentModel = {
      id: this.departmentId,
      departmentName: this.departmentForm.value.departmentName,
      numOfEmployees: this.departmentForm.value.numOfEmployees,
      photo: '', // Assign the photo as per your requirement
      branch: {
        id: this.department.branch.id, // Existing branch ID
        branchName: this.department.branch.branchName,
        address: this.department.branch.address,
        city: this.department.branch.city,
        zipCode: this.department.branch.zipCode,
        country: this.department.branch.country,
        createdAt: this.department.branch.createdAt,
        updateAt: this.department.branch.updateAt,
        photo: this.department.branch.photo,
        company: this.department.branch.company, // Ensure to include the company property
      },
    };

    this.departmentService
      .updateDepartment(
        this.departmentId,
        updatedDepartment,
        this.departmentPhoto
      )
      .subscribe({
        next: () => {
          this.notification.showNotify(
            'Department updated successfully',
            'success'
          );
          this.router.navigate(['/department/list']);
        },
        error: (error) => {
          console.error('Error updating department', error);
          this.notification.showNotify('Error updating department', 'error');
        },
      });
  }
}
