import { Component } from '@angular/core';
import {MenuItem} from "primeng/api";
import {SectorService} from "../services/sector.service";
import {Sector} from "../models/Sector";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-sector',
  templateUrl: './create-sector.component.html',
  styleUrl: './create-sector.component.css'
})
export class CreateSectorComponent {

  sector: Sector = {name : "", description : "", id : 0};
  items: MenuItem[] = [];
  constructor(private sectorService : SectorService, private router: Router) {
    this.items = [
      /*{ label: "Create", icon: 'pi pi-fw pi-plus', command: () => {this.create();}},*/
      { label: "Edit", icon: 'pi pi-fw pi-pencil', command: () => {this.update();}},
      { label: "Delete", icon: 'pi pi-fw pi-trash',command: () => {this.delete();}},
      { label: "Clear", icon: 'pi pi-fw pi-pencil',command: () => {this.clear();}},
    ];
  }

  create() {
    if (!this.sector.name || !this.sector.description) {
      // Handle empty fields
      alert('Please enter both name and description.');
      return;
    }

    this.sectorService.saveSector(this.sector).subscribe(
      (response: Sector) => {
        console.log(response);
        // Success message and navigation
        alert('Sector created successfully!'); // Or use a toaster for a more subtle notification
        this.router.navigateByUrl('/sectors'); // Navigate to sectors list
      },
      (error) => {
        console.error(error);
        alert('Error creating sector!'); // Or use a toaster for error notification
      }
    );
  }
  update() {
    alert("update ok!");
  }
  delete(){}
  clear(){
    this.sector = { name: '', description: '', id: 0 }; // Reset sector object to empty values
  }


  ngOnInit() {
  }


  save(info: string) {

  }
}



