import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BranchService } from '../../../services/branch.service';
import { NotificationService } from '../../../services/notification.service';
import { BranchModel } from '../../../models/branch.model';

@Component({
  selector: 'app-view-branch',
  templateUrl: './view-branch.component.html',
  styleUrls: ['./view-branch.component.css'],
})
export class ViewBranchComponent implements OnInit {
  branch!: BranchModel; 
  branchId!: number;

  constructor(
    private route: ActivatedRoute,
    private branchService: BranchService,
    private notification: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.branchId = +this.route.snapshot.paramMap.get('id')!; 
    this.loadBranch();
  }

  loadBranch() {
    this.branchService.getBranchById(this.branchId).subscribe({
      next: (data) => {
        this.branch = data; 
      },
      error: (error) => {
        console.error('Error fetching branch details', error);
        this.notification.showNotify('Error fetching branch details', 'error');
        this.router.navigate(['/branch/list']);
      },
    });
  }

  goBack() {
    this.router.navigate(['/branch/list']);
  }
}
