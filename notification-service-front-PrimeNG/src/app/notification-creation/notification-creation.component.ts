import {ChangeDetectorRef, Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Notification} from "../models/Notification";
import {NotificationType} from "../models/NotificationType";
import {Template} from "../models/Template";
import {DeliveryMethod} from "../models/DeliveryMethod";
import {Delivery} from "../models/Delivery";
import {Sector} from "../models/Sector";
import {Actor} from "../models/Actor";
import {NotificationService} from "../services/notification.service";
import {Router} from "@angular/router";
import {SectorService} from "../services/sector.service";
import {ActorService} from "../services/actor.service";
import {NotificationTypeService} from "../services/notification-type.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {DeliveryService} from "../services/delivery.service";

@Component({
  selector: 'app-notification-creation',
  templateUrl: './notification-creation.component.html',
  styleUrl: './notification-creation.component.css'
})
export class NotificationCreationComponent implements OnInit{

  //Panel I: Notification


  // Delivery related fields
  deliveryMethod: DeliveryMethod = {name:"", id:0, description:""}
  delivery!: Delivery;
  deliveries!: Delivery[];
  selectedDelivery!: Delivery;

  // Template related fields
  template: Template ={body: "", creationDate: "", id: 0, notificationType: [], title: ""}
  templates!: Template[]; // Array to store templates
  selectedTemplate!: Template;
  private templateBody!: string;
  customizedTemplateBody!: string; // Variable to hold customized template body

  // Notification related fields
  notificationType: NotificationType = {templates: [], id: 0, name: ""};
  notificationTypes!: NotificationType [];
  selectedNotificationType!: NotificationType;
  notification: Notification= {content: "", id: 0, type:this.notificationType, users:[],delivery:this.delivery,creationDate:new Date()};

  // Boolean flags to control the visibility of the Templates input fields
  showDateInput: boolean = false;
  showTimeInput: boolean = false;
  showNameInput: boolean = false;
  showLocationInput: boolean = false;
  showReasonInput: boolean = false;
  showContactInfoInput: boolean = false;
  showSalaryInput: boolean = false;
  showTopicInput: boolean = false;
  showDescriptionInput: boolean = false;

  // Variables where to store dynamic values
  public date: string = '';
  public time: string = '';
  public location: string = '';
  public name: string = '';
  public reason: string = '';
  public contact: string = '';
  public salary: string = '';
  public topic: string = '';
  public description: string = '';


  //Panel II: Audience


  // Actor related fields
  actors!: Actor[];
  filteredActors!: Actor[];
  filterText: string = '';

  filteredNotifications!: Notification[]; // Assuming this is your type for notifications
  targetFilterText: string = '';

  /*  // Sector related fields
  sectors!: Sector[];
  selectedSector!: Sector;*/



  constructor(private notificationService : NotificationService, private actorService:ActorService,private router:Router,
              private sectorService:SectorService, private notificationTypeService: NotificationTypeService,
              private messageService : MessageService, private confirmationService: ConfirmationService,
              private deliveryService: DeliveryService) {
  }

  ngOnInit(){

    this.loadNotificationTypes();
    this.loadActors();
    this.loadDeliveries();


  }



  loadNotificationTypes() {
    this.notificationTypeService.getNotificationTypes().subscribe(notificationTypes => {
      this.notificationTypes = notificationTypes;
    });
  }


  loadActors() {
    this.actorService.getActors().subscribe(actors => {
      this.actors = actors;
    });
  }

  loadDeliveries() {
    this.deliveryService.getDeliveries().subscribe(deliveries => {
      this.deliveries = deliveries.map(delivery => ({
        ...delivery,
        deliveryDate: delivery.deliveryDate ? new Date(delivery.deliveryDate) : undefined
      }));
    });
  }


  filterTemplates(selectedType: NotificationType): void {
    this.selectedNotificationType = selectedType;
    this.templates = []; // Clear the templates array
    this.notification.content=""; // Clear the notification content
    if (this.selectedNotificationType && this.selectedNotificationType.templates) {
      // If the selected notification type and its templates exist
      this.templates = this.selectedNotificationType.templates;
      this.hideTemplateFields();
    }
  }


  updateTextareaContent(): void {
    // Assigning the selected template to the notification content once a template is selected
    if (this.selectedTemplate) {
      this.notification.content = this.selectedTemplate.body;
      this.templateBody =  this.notification.content;
      //Hide all inputs
      this.hideTemplateFields();
      //Show the hidden inputs for dynamic values
      if (this.selectedTemplate.title=="Upcoming Event Notification"){
        //this.toggleTemplateInput();
        this.showUpcomingEventInputs();
      }else if(this.selectedTemplate.title=="Event Cancellation Notice"){
        this.showEventCancellationInputs();
      }else if(this.selectedTemplate.title=="Salary Adjustment Notification"){
        this.showSalaryAdjustmentInputs();
      }else if(this.selectedTemplate.title=="Upcoming Meeting Notification"){
        this.showUpcomingMeetingInputs();
      }
    }
  }

  showUpcomingEventInputs(){
    this.date=this.time=this.location="";
    this.showDateInput=this.showLocationInput=this.showTimeInput=true;
  }
  showEventCancellationInputs(){
    this.date=this.name=this.location=this.reason=this.contact="";
    this.showDateInput=this.showLocationInput=this.showNameInput=this.showReasonInput=this.showContactInfoInput=true;
  }
  showSalaryAdjustmentInputs(){
    this.date=this.salary=this.reason=this.contact="";
    this.showDateInput=this.showSalaryInput=this.showReasonInput=true;
  }
  showUpcomingMeetingInputs(){
    this.date=this.topic=this.time="";
    this.showDateInput=this.showTopicInput=this.showTimeInput=true;
  }




  saveNotification() {
    this.confirmationService.confirm({
      header: 'Are you sure you want to create this Notification?',
      message: 'Please confirm to proceed.',
      accept: () => {

        if(!this.notification.type || !this.notification.content ) {
          // Handle empty fields
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'Please enter all required fields!'});
          return;
        }

        //reset the selectedSector's id to 0 in order to create new Sectors
        this.notificationService.saveNotification(this.notification).subscribe(
          (response: Notification) => {
            console.log(response);
            // Success message and navigation
            this.showSuccess()

          },
          (error) => {
            console.error(error);
            this.showError();
          }
        );

      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });

  }


  showSuccess() {
    this.messageService.add({ severity: 'success', summary: 'Created', detail: 'Notification created successfully!' });
  }

  showError() {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can\'t perform this action'  });
  }



  hideTemplateFields (){
    this.showDateInput=this.showTimeInput=this.showLocationInput=this.showNameInput=this.showReasonInput=this.showSalaryInput=this.showContactInfoInput=this.showTopicInput= this.showDescriptionInput=false;
  }


  generateCustomizedTemplate(): void {
    // Get the template body from the textarea
    const templateBody: string = this.templateBody;

    // Replace the placeholder with the dynamic date value
    const customizedTemplateBody: string = templateBody
      .replace('[$date]', this.date)
      .replace('[$location]', this.location) // Replace location placeholder with location value);
      .replace('[$time]', this.time) // Replace location placeholder with location value);
      .replace('[$name]', this.name)
      .replace('[$reason]', this.reason)
      .replace('[$contact]', this.contact)
      .replace('[$salary]', this.salary)

      .replace('[$company]', this.time)
      .replace('[$description of new policy]', this.time)
      .replace('[$impact of new policy]', this.time);
    // Display the customized template body
    console.log(customizedTemplateBody);
    this.customizedTemplateBody = customizedTemplateBody;
    this.notification.content = this.customizedTemplateBody;


    /*this.printable=true;*/
    this.applyFilter();
  }



  getSeverity(delivery: Delivery): "success" | "info" | "warning" | "danger" | "secondary" | "contrast" {
    switch (delivery.state) {
      case 'sent':
        return 'success';

      case 'pending':
        return 'warning';

      case '-':
        return 'danger';

      default:
        // You can either return a default severity value (e.g., 'info')
        // or throw an error if the sector name is unexpected.
        return 'danger'; // Example default value
      // throw new Error('Unexpected sector name: ' + actor.sector.name);
    }
  }

  getnotificationCount(delivery : Delivery) {
    return this.deliveryService.getNumberOfNotifications(delivery);
  }

  selectDelivery(delivery: Delivery) {
    this.notification.delivery=delivery;
    console.log("this notification delivery : "+ this.notification.delivery.name);
    console.log("delivery : "+ delivery.name);
  }




  applyFilter(): void {
    const filterText = this.filterText.toLowerCase();
    this.filteredActors = this.actors.filter(actor =>
      actor.username.toLowerCase().includes(filterText) ||
      actor.email.toLowerCase().includes(filterText) ||
      actor.phoneNumber.toLowerCase().includes(filterText) ||
      actor.sector.name.toLowerCase().includes(filterText)
    );
  }



}
