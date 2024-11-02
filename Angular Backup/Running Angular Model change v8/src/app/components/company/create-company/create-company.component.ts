import { Component } from '@angular/core';
import { CompanyModel } from '../../../models/company.model';
import { Router } from '@angular/router';
import { CompanyService } from '../../../services/company.service';
import { NgForm } from '@angular/forms';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-create-company',
  templateUrl: './create-company.component.html',
  styleUrls: ['./create-company.component.css'],
})
export class CreateCompanyComponent {
  company: CompanyModel = new CompanyModel();
  companyPhoto?: File;

  constructor(
    private companyService: CompanyService,
    private router: Router,
    private notification: NotificationService
  ) {}

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
        .createCompany(this.company, this.companyPhoto)
        .subscribe({
          next: (response) => {
            if (response.status === 201) {
              console.log('Company created successfully', response);
              this.notification.showNotify(
                'Company created successfully',
                'success'
              );
              this.router.navigate(['/company/list']);
            } else {
              console.error('Unexpected response status:', response);
            }
          },
          error: (error) => {
            console.error('Error creating company', error);
            this.notification.showNotify('Error creating company', 'error');
          },
        });
    }
  }
}
