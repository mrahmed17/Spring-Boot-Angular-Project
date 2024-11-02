import { Component } from '@angular/core';
import { BranchModel } from '../../../models/branch.model';
import { BranchService } from '../../../services/branch.service';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-branch',
  templateUrl: './create-branch.component.html',
  styleUrls: ['./create-branch.component.css'],
})
export class CreateBranchComponent {
  branch: BranchModel = new BranchModel();
  branchPhoto?: File;

  constructor(
    private branchService: BranchService,
    private router: Router,
    private notification: NotificationService
  ) {}

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.branchPhoto = file;
    } else {
      this.branchPhoto = undefined;
    }
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.branchService.createBranch(this.branch, this.branchPhoto).subscribe({
        next: (response) => {
          console.log('Branch created successfully', response);
          this.notification.showNotify(
            'Branch created successfully',
            'success'
          );
          this.router.navigate(['/branch/list']);
        },
        error: (error) => {
          console.error('Error creating branch', error);
          this.notification.showNotify('Error creating branch', 'error');
        },
      });
    }
  }
}
