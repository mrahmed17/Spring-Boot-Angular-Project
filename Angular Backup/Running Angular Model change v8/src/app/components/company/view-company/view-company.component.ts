import { Component, OnInit } from '@angular/core';
import { CompanyModel } from '../../../models/company.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from '../../../services/company.service';
import { NotificationService } from '../../../services/notification.service';
import { faArrowLeft, faBuilding } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-view-company',
  templateUrl: './view-company.component.html',
  styleUrls: ['./view-company.component.css'],
})
export class ViewCompanyComponent implements OnInit {
  faArrowLeft = faArrowLeft;
  faBuilding = faBuilding;

  company: CompanyModel = new CompanyModel();
  companyId!: number;

  constructor(
    private companyService: CompanyService,
    private route: ActivatedRoute,
    private router: Router,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.companyId = this.route.snapshot.params['id'];
    this.loadCompanyDetails();
  }

  loadCompanyDetails() {
    this.companyService.getCompanyById(this.companyId).subscribe({
      next: (data) => {
        this.company = data;
      },
      error: (error) => {
        console.error('Error fetching company details', error);
        this.notification.showNotify('Error fetching company details', 'error');
        this.router.navigate(['/company/list']);
      },
    });
  }

  backToList() {
    this.router.navigate(['/company/list']);
  }
}
