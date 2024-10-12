import { Component } from '@angular/core';
import {Sector} from "../models/Sector";
import {SectorService} from "../services/sector.service";
import {Router} from "@angular/router";
import {MessageService, ConfirmationService} from "primeng/api";
import { ConfirmDialogModule } from 'primeng/confirmdialog';

@Component({
  selector: 'app-sector',
  templateUrl: './sector.component.html',
  styleUrl: './sector.component.css'
})
export class SectorComponent {
  sectors: Sector[] = [];
  cols: any[] = [
    {header: 'Id', field: 'id'},
    {header: 'Name', field: 'name'},
    {header: 'Description', field: 'description'},
    {header: 'Action', field: null}
  ];
  visible: boolean = false;
  selectedSector: Sector = {id: 0, name: '', description: ''};


  value!: string;

  constructor(private sectorService: SectorService, private router: Router, private messageService: MessageService,
              private confirmationService: ConfirmationService) {

  }

  ngOnInit() {
    this.sectorService.getSectors().subscribe(
      (data: Sector[]) => {
        console.log(data);
        this.sectors = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  showDialog(sector: Sector) {
    this.selectedSector = {...sector}; // Clone the sector object
    this.visible = true;
  }


  items = [
    /*{ label: "Create", icon: 'pi pi-fw pi-plus', command: () => {this.create();}},*/
    {
      label: "Edit", icon: 'pi pi-fw pi-pencil', command: () => {
        this.updateSector();
      }
    },
    {
      label: "Delete", icon: 'pi pi-fw pi-trash', command: () => {
        this.deleteSector();
      }
    },
    {
      label: "Create", icon: 'pi pi-fw pi-plus', command: () => {
        this.saveSector();
      }
    },
  ];



  saveSector() {

    this.confirmationService.confirm({
      header: 'Are you sure you want to create this sector?',
      message: 'Please confirm to proceed.',
      accept: () => {

        if (!this.selectedSector.name || !this.selectedSector.description) {
          // Handle empty fields
          alert('Please enter both name and description.');
          return;
        }

        //reset the selectedSector's id to 0 in order to create new Sectors
        this.selectedSector.id = 0;
        this.sectorService.saveSector(this.selectedSector).subscribe(
          (response: Sector) => {
            console.log(response);
            // Success message and navigation
            this.showSuccess();
            this.ngOnInit();
          },
          (error) => {
            console.error(error);
            this.showError()
          }
        );
        // Save the updated sector information
        console.log('Saved sector:', this.selectedSector);
        this.visible = false;
        //this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });
  }


  updateSector() {
    this.confirmationService.confirm({
      header: 'Are you sure you want to alter this sector?',
      message: 'Please confirm to proceed.',
      accept: () => {

        if (!this.selectedSector.name || !this.selectedSector.description) {
          // Handle empty fields
          alert('Please enter both name and description.');
          return;
        }

        if (this.selectedSector.id) { // Check if ID exists for update
          this.sectorService.updateSector(this.selectedSector)
            .subscribe(
              (updatedSector) => {
                console.log('Sector updated successfully:', updatedSector);
                // Success message and navigation
                this.ngOnInit();
                this.showInfo();
              },
              (error) => {
                console.error('Error updating sector:', error);
                this.showError()
              }
            );

          // Save the updated sector information
          console.log('Updated sector:', this.selectedSector);
          this.visible = false;
          //this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
        } else {//if the id doesn't exist

        }
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });
  }


  deleteSector() {
    this.confirmationService.confirm({
      header: 'Are you sure you want to delete this sector?',
      message: 'Please confirm to proceed.',
      accept: () => {

        if (this.selectedSector.id && this.selectedSector.id!=0) { // Check if ID exists for update
          this.sectorService.deleteSector(this.selectedSector.id)
            .subscribe(
              () => {
                console.log('Sector deleted successfully');
                // Handle successful deletion
                this.ngOnInit();
                this.showWarn();
              },
              (error) => {
                console.error('Error deleting sector:', error);
                this.showError()
              }
            );
          // Save the updated sector information
          console.log('Updated sector:', this.selectedSector);
          this.visible = false;
        }else {
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'There is no sector with this id. Please enter a valid id!'});
        }
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });
  }



  showSuccess() {
    this.messageService.add({ severity: 'success', summary: 'Created', detail: 'Sector created successfully!t' });
  }
  showInfo() {
    this.messageService.add({ severity: 'info', summary: 'Updated', detail: 'Sector updated successfully!' });
  }
  showWarn() {
    this.messageService.add({ severity: 'warn', summary: 'Deleted', detail: 'Sector deleted successfully!' });
  }
  showError() {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can\'t perform this action'  });
  }

}


