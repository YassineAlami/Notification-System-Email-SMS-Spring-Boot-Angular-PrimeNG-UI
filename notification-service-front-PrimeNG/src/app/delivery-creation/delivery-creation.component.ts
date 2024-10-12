import {Component, OnInit} from '@angular/core';
import {Delivery} from "../models/Delivery";
import {DeliveryMethod} from "../models/DeliveryMethod";
import {Notification} from "../models/Notification";
import {DeliveryMethodService} from "../services/delivery-method.service";
import {ConfirmationService, MessageService} from "primeng/api";
import {DeliveryService} from "../services/delivery.service";
import {NotificationService} from "../services/notification.service";

@Component({
  selector: 'app-delivery-creation',
  templateUrl: './delivery-creation.component.html',
  styleUrl: './delivery-creation.component.css'
})
export class DeliveryCreationComponent implements OnInit{
  activeIndex: number = 0;

  deliveryMethod : DeliveryMethod = {id:0,name:"",description:""};
  notifications : Notification [] = [];
  targetedNotifications : Notification [] = [];

  delivery: Delivery= {name:"",id:0,state:"",deliveryMethod: this.deliveryMethod,deliveryDate: null,estimatedDeliveryDate:null,creationDate:new Date(),notifications: []};
  formattedDate!: string;

  deliveryMethods: DeliveryMethod[] = [];


  constructor(private deliveryMethodService : DeliveryMethodService, private messageService : MessageService,
              private deliveryService: DeliveryService, private confirmationService: ConfirmationService,
              private notificationServie : NotificationService) {
  }

  ngOnInit() {
    this.delivery.state = "-";
    this.formattedDate = this.formatDate(this.delivery.creationDate);

    this.loadDeliveryMethods();
    this.loadFilteredNotifications();

  }


  loadDeliveryMethods() {
    this.deliveryMethodService.getDeliveryMethods().subscribe(deliveryMethods => {
      this.deliveryMethods = deliveryMethods;
      console.log(deliveryMethods);
    });
  }

  loadFilteredNotifications() {
    this.notificationServie.getFilteredNotifications().subscribe(notifications => {
      this.notifications = notifications;
      console.log(notifications);
    });
  }

  formatDate(date: Date): string {
    const month = date.getMonth() + 1; // Months are zero-indexed
    const day = date.getDate();
    const year = date.getFullYear();
    const hours = date.getHours() % 12 || 12; // Convert to 12-hour format (e.g., 1 PM instead of 13:00)
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const ampm = date.getHours() >= 12 ? 'PM' : 'AM';

    return `${day}/${month}/${year}, ${hours}:${minutes} ${ampm}`;
  }


  saveDelivery() {
    this.confirmationService.confirm({
      header: 'Are you sure you want to create this Delivery?',
      message: 'Please confirm to proceed.',
      accept: () => {

        if(!this.delivery.name ) {
          // Handle empty fields
          this.messageService.add({severity: 'error', summary: 'Validation Error', detail: 'Please enter all required fields!'});
          return;
        }
        if(this.delivery.estimatedDeliveryDate){
          this.delivery.state = "pending";
        }

        this.deliveryService.saveDelivery(this.delivery).subscribe(
          (createdDelivery: Delivery) => {
            // Assign notifications to the newly created delivery
            const notificationIds = this.targetedNotifications.map(notification => notification.id);
            this.deliveryService.assignNotificationsToDelivery(createdDelivery.id, notificationIds).subscribe(
              (updatedDelivery: Delivery) => {
                console.log('Notifications assigned successfully', updatedDelivery);
                // Success message and navigation
                this.showSuccess()
              },
              error => {
                console.error('Error assigning notifications', error);
                this.showError();
              }
            );
          },
          error => {
            console.error('Error creating delivery', error);
          }
        );

      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
      }
    });


  }


  getUsersCount(notification : Notification) {
    return this.notificationServie.getNumberOfUsers(notification);
  }


  showSuccess() {
    this.messageService.add({ severity: 'success', summary: 'Created', detail: 'Notification created successfully!' });
  }

  showError() {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can\'t perform this action'  });
  }

}
