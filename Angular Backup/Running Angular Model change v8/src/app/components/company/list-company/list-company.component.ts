import { Component, OnInit } from '@angular/core';
import { CompanyService } from '../../../services/company.service';
import { CompanyModel } from '../../../models/company.model';
import { Router } from '@angular/router';
import { NotificationService } from '../../../services/notification.service';

@Component({
  selector: 'app-list-company',
  templateUrl: './list-company.component.html',
  styleUrls: ['./list-company.component.css'],
})
export class ListCompanyComponent implements OnInit {
  companies: CompanyModel[] = [];
  filteredCompanies: CompanyModel[] = [];
  filterText: string = '';
  selectedCompanyBranches: any[] = [];
  selectedCompanyDepartments: any[] = [];

  constructor(
    private companyService: CompanyService,
    private router: Router,
    private notification: NotificationService
  ) {}

  ngOnInit(): void {
    this.loadCompanies();
  }

  loadCompanies() {
    this.companyService.getAllCompanies().subscribe({
      next: (data) => {
        this.companies = data;
        this.filteredCompanies = data;
      },
      error: (error) => {
        console.error('Error fetching company list', error);
        this.notification.showNotify('Error fetching company list', 'error');
      },
    });
  }

  applyFilter() {
    if (this.filterText) {
      this.companyService.findByCompanyName(this.filterText).subscribe({
        next: (company) => {
          this.filteredCompanies = [company];
        },
        error: (error) => {
          console.error('Error fetching company by name', error);
          this.notification.showNotify('Company not found', 'error');
          this.filteredCompanies = [];
        },
      });
    } else {
      this.filteredCompanies = this.companies;
    }
  }

  fetchBranches(companyId: number) {
    this.companyService.getBranchesByCompanyId(companyId).subscribe({
      next: (branches) => {
        this.selectedCompanyBranches = branches;
        console.log('Branches: ', branches);
      },
      error: (error) => {
        console.error('Error fetching branches', error);
        this.notification.showNotify('Error fetching branches', 'error');
      },
    });
  }

  fetchDepartments(companyId: number) {
    this.companyService.getDepartmentsByCompanyId(companyId).subscribe({
      next: (departments) => {
        this.selectedCompanyDepartments = departments;
        console.log('Departments: ', departments);
      },
      error: (error) => {
        console.error('Error fetching departments', error);
        this.notification.showNotify('Error fetching departments', 'error');
      },
    });
  }

  viewCompany(companyId: number) {
    this.router.navigate(['/company/view', companyId]);
  }

  editCompany(companyId: number) {
    this.router.navigate(['/company/update', companyId]);
  }

  deleteCompany(companyId: number) {
    if (confirm('Are you sure you want to delete this company?')) {
      this.companyService.deleteCompany(companyId).subscribe({
        next: () => {
          this.notification.showNotify(
            'Company deleted successfully',
            'success'
          );
          this.loadCompanies();
        },
        error: (error) => {
          console.error('Error deleting company', error);
          this.notification.showNotify('Error deleting company', 'error');
        },
      });
    }
  }
}