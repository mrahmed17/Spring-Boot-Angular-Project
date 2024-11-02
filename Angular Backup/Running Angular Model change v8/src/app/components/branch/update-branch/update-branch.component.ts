import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { BranchService } from '../../../services/branch.service';
import { BranchModel } from '../../../models/branch.model';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-update-branch',
  templateUrl: './update-branch.component.html',
  styleUrls: ['./update-branch.component.css'],
})
export class UpdateBranchComponent implements OnInit {
  branch: BranchModel = new BranchModel();
  branchPhoto?: File;

  constructor(
    private branchService: BranchService,
    private route: ActivatedRoute,
    private router: Router,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    const branchId = +this.route.snapshot.paramMap.get('id')!;
    this.loadBranch(branchId);
  }

  loadBranch(branchId: number) {
    this.branchService.getBranchById(branchId).subscribe({
      next: (data) => {
        this.branch = data;
      },
      error: (error) => {
        console.error('Error fetching branch details', error);
        this.notification.showNotify('Error fetching branch details', 'error');
      },
    });
  }

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
      this.branchService
        .updateBranch(this.branch.id, this.branch, this.branchPhoto)
        .subscribe({
          next: (response) => {
            console.log('Branch updated successfully', response);
            this.notification.showNotify(
              'Branch updated successfully',
              'success'
            );
            this.router.navigate(['/branch/list']);
          },
          error: (error) => {
            console.error('Error updating branch', error);
            this.notification.showNotify('Error updating branch', 'error');
          },
        });
    }
  }
}
