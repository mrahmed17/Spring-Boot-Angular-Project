import { Component, OnInit } from '@angular/core';
import { CompanyModel } from '../../../models/company.model';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from '../../../services/company.service';
import { NgForm } from '@angular/forms';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-update-company',
  templateUrl: './update-company.component.html',
  styleUrls: ['./update-company.component.css'],
})
export class UpdateCompanyComponent implements OnInit {
  company: CompanyModel = new CompanyModel();
  companyPhoto?: File;
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
      },
    });
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.companyPhoto = file;
    } else {
      this.companyPhoto = undefined;
    }
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.companyService
        .updateCompany(this.companyId, this.company, this.companyPhoto)
        .subscribe({
          next: (response) => {
            console.log('Company updated successfully', response);
            this.notification.showNotify(
              'Company updated successfully',
              'success'
            );
            this.router.navigate(['/company/list']);
          },
          error: (error) => {
            console.error('Error updating company', error);
            this.notification.showNotify('Error updating company', 'error');
          },
        });
    }
  }
}
