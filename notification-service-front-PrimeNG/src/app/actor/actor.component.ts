import { Component } from '@angular/core';
import {Sector} from "../models/Sector";
import {SectorService} from "../services/sector.service";
import {Actor} from "../models/Actor";
import {ActorService} from "../services/actor.service";
import {ConfirmationService, MessageService, Message} from "primeng/api";


@Component({
  selector: 'app-actor',
  templateUrl: './actor.component.html',
  styleUrl: './actor.component.css'
})
export class ActorComponent {

  cols: any[] = [
    {header: 'Id', field: 'id'},
    {header: 'Name', field: 'username'},
    {header: 'Email', field: 'email'},
    {header: 'Phone Number', field: 'phoneNumber'},
    {header: 'Sector', field: 'sector'},
    {header: 'Action', field: null}
  ];

  visible: boolean = false;

  sectors: Sector[] = [];
  actors: Actor[] = [];
  selectedActor: Actor = {id: 0, username: '', email: '', phoneNumber: "", password: "", sector: { id: 0, name: "", description: "" }};

  /*sector : Sector = {id: 0, name:"", description:""}
  sectors!: Sector[];
  selectedSector: Sector | undefined;


  actors: Actor[] = [];
  actor : Actor = {id : 0, email : "", password : "", username : "", phoneNumber :"", sector: this.sector};
//  selectedActor: Actor = {id: 0, username: '', email: '', phoneNumber:"",password:"", sector: this.sector};

  selectedActor: Actor =
    {
      id: 0, username: '', email: '', phoneNumber: "", password: "", sector: { id: 0, name: "", description: "" }, // New object for selectedActor
    };*/


  constructor(private actorService:ActorService, private messageService : MessageService,
              private confirmationService: ConfirmationService, private sectorService: SectorService) {

  }

  messages: Message[] | undefined;

  ngOnInit() {
    this.messages = [{ severity: 'info', detail: 'Message Content' }];

    this.loadSectors();
    this.loadActors();

    //filling the 'actors' array with data from the service
    /*this.actorService.getActors().subscribe(
      (data: Actor[]) => {
        console.log(data);
        this.actors = data;
      },
      (error) => {
        console.log(error);
      }
    );*/


    //filling the 'sectors' array with data from the service
    /*this.sectorService.getSectors().subscribe(
      (data: Sector[]) => {
        console.log(data);
        this.sectors = data;
      },
      (error) => {
        console.log(error);
      }
    );*/
  }


  loadSectors() {
    this.sectorService.getSectors().subscribe(sectors => {
      this.sectors = sectors;
    });
  }

  loadActors() {
    this.actorService.getActors().subscribe(actors => {
      this.actors = actors;
    });
  }

  // Display of the dialog where the actions can be performed
  showDialog(actor: Actor) {
    this.selectedActor = { ...actor };

    // Find the sector object from the sectors array and set it to selectedActor.sector
    this.selectedActor.sector = this.sectors.find(sector => sector.id === actor.sector.id) || { id: 0, name: "", description: "" };
    this.visible = true;
  }



  items = [
    /*{ label: "Create", icon: 'pi pi-fw pi-plus', command: () => {this.create();}},*/
    {
      label: "Edit", icon: 'pi pi-fw pi-pencil', command: () => {
        this.updateActor();
      }
    },
    {
      label: "Delete", icon: 'pi pi-fw pi-trash', command: () => {
        this.deleteActor();
      }
    },
    {
      label: "Create", icon: 'pi pi-fw pi-plus', command: () => {
        this.saveActor();
      }
    },
    {
      label: "Clear", icon: 'pi pi-fw pi-file', command: () => {
        this.clearField();
      }
    },
  ];


  saveActor() {
    this.confirmationService.confirm({
      header: 'Are you sure you want to create this actor?',
      message: 'Please confirm to proceed.',
      accept: () => {

        if (!this.selectedActor.username || !this.selectedActor.email || !this.selectedActor.phoneNumber) {
          // Handle empty fields
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'Please enter all required fields: Username, Email, Phone number'});
          return;
        }

        //reset the selectedSector's id to 0 in order to create new Sectors
        this.selectedActor.id = 0;
        this.actorService.saveActor(this.selectedActor).subscribe(
          (response: Actor) => {
            console.log(response);
            // Success message and navigation
            this.showSuccess()
            this.ngOnInit();
          },
          (error) => {
            console.error(error);
            this.showError();
          }
        );
        // Save the updated sector information
        console.log('Saved actor:', this.selectedActor);
        this.visible = false;
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });
  }


  private updateActor() {

    this.confirmationService.confirm({
      header: 'Are you sure you want to update this actor?',
      message: 'Please confirm to proceed.',
      accept: () => {
        if (!this.selectedActor.username || !this.selectedActor.email || !this.selectedActor.phoneNumber) {
          // Handle empty fields
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'Please enter all required fields: Username, Email, Phone number'});
          return;
        }

        if (this.selectedActor.id && this.selectedActor.id !=0) { // Check if ID exists for update
          this.actorService.updateSector(this.selectedActor)
            .subscribe(
              (updatedActor) => {
                console.log('Actor updated successfully:', updatedActor);
                // Success message and navigation
                this.showInfo();
                this.ngOnInit();
              },
              (error) => {
                console.error('Error updating Actor:', error);
                this.showError()
              }
            );

          // Save the updated sector information
          console.log('Updated actor:', this.selectedActor);
          this.visible = false;

        } else {//case where id is null
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'There is no actor with this id. Please enter a valid id!'});
        }
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });
  }

  deleteActor() {
    this.confirmationService.confirm({
      header: 'Are you sure you want to delete this actor?',
      message: 'Please confirm to proceed.',
      accept: () => {
        if (this.selectedActor.id && this.selectedActor.id!=0) { // Check if ID exists for update
          this.actorService.deleteActor(this.selectedActor.id)
            .subscribe(
              () => {
                console.log('Actor deleted successfully');
                // Handle successful deletion
                this.showWarn();
                this.ngOnInit();
              },
              (error) => {
                console.error('Error deleting actor:', error);
                // Handle error
                this.showError()
              }
            );
          // Save the updated sector information
          console.log('Deleted sector:', this.selectedActor);
          this.visible = false;
        } else {//if the id is null or zero
          console.error('PLease provide a valid actor\'s id');
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'There is no actor with this id. Please enter a valid id!'});
          /*alert('Please provide a valid actor\'s id');*/
          this.showError();
        }
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });
  }

  private clearField() {
    this.selectedActor = { username: '', email: '', phoneNumber: '', id: 0 , password:'', sector:new Sector()}; // Reset sector object to empty values
  }

  showSuccess() {
    this.messageService.add({ severity: 'success', summary: 'Created', detail: 'Actor created successfully!' });
  }
  showInfo() {
    this.messageService.add({ severity: 'info', summary: 'Updated', detail: 'Actor updated successfully!' });
  }
  showWarn() {
    this.messageService.add({ severity: 'warn', summary: 'Deleted', detail: 'Actor deleted successfully!' });
  }
  showError() {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can\'t perform this action'  });
  }

}
