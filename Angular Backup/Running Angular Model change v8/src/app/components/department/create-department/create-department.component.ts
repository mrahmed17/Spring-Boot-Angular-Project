import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DepartmentService } from '../../../services/department.service';
import { NotificationService } from '../../../services/notification.service';
import { DepartmentModel } from '../../../models/department.model';
import { BranchModel } from '../../../models/branch.model';

@Component({
  selector: 'app-create-department',
  templateUrl: './create-department.component.html',
  styleUrls: ['./create-department.component.css'],
})
export class CreateDepartmentComponent implements OnInit {
  departmentForm: FormGroup;
  departmentPhoto: File | null = null;
  branches: BranchModel[] = [];
  companyId: number = 1; 

  constructor(
    private formBuilder: FormBuilder,
    private departmentService: DepartmentService,
    private notification: NotificationService,
    private router: Router
  ) {
   
    this.departmentForm = this.formBuilder.group({
      departmentName: ['', Validators.required],
      numOfEmployees: [0, [Validators.required, Validators.min(0)]],
      branchId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadBranches();
  }

  loadBranches(): void {
    console.log('Loading branches...');
    this.departmentService.getBranchesByCompanyId(this.companyId).subscribe({
      next: (data) => {
        this.branches = data || [];
        console.log('Branches loaded:', this.branches);
        if (this.branches.length === 0) {
          console.warn('No branches found for company ID:', this.companyId);
        }
      },
      error: (error) => {
        console.error('Error loading branches', error);
        this.notification.showNotify('Error loading branches', 'error');
      },
    });
  }

  onPhotoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.departmentPhoto = input.files?.[0] || null;
  }

  onSubmit(): void {
    if (this.departmentForm.invalid) {
      this.notification.showNotify(
        'Please fill in all required fields.',
        'error'
      );
      return; 
    }

    const department: DepartmentModel = {
      id: 0,
      departmentName: this.departmentForm.value.departmentName,
      numOfEmployees: this.departmentForm.value.numOfEmployees,
      photo: '', 
      branch: { id: this.departmentForm.value.branchId } as BranchModel,
    };

    this.departmentService
      .createDepartment(department, this.departmentPhoto || undefined)
      .subscribe({
        next: () => {
          this.notification.showNotify(
            'Department created successfully',
            'success'
          );
          this.router.navigate(['/department/list']);
        },
        error: (error) => {
          console.error('Error creating department', error);
          this.notification.showNotify('Error creating department', 'error');
        },
      });
  }
}
