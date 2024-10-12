import {Component, OnInit} from '@angular/core';
/*import {Delivery} from "../models/Delivery";*/

import {DeliveryService} from "../services/delivery.service";
import {ConfirmationService, MessageService, TreeNode} from "primeng/api";
import {Actor} from "../models/Actor";
import {Notification} from "../models/Notification";
import {NotificationType} from "../models/NotificationType";
import {ActorService} from "../services/actor.service";
import {DeliveryMethod} from "../models/DeliveryMethod";
import {CustomDelivery} from "../models/CustomDelivery";
import {CustomDeliveryService} from "../services/custom-delivery.service";
import {CustomNotification} from "../models/CustomNotification";
import {DatePipe} from "@angular/common";
import {Delivery} from "../models/Delivery";
import {EmailService} from "../services/email.service";
import {EmailRequest} from "../models/EmailRequest";
import {forkJoin, Observable} from "rxjs";

export interface Car {
  id: number;
  brand: string;
  model: string;
}

export interface User {
  id: number;
  name: string;
  age: number;
  cars: Car[];
}


@Component({
  selector: 'app-delivery',
  templateUrl: './delivery.component.html',
  styleUrl: './delivery.component.css'
})
export class DeliveryComponent implements OnInit{
    deliveries: TreeNode[] = [];
    deliveriesFull: CustomDelivery[] = [];

    datePipe!: DatePipe // Inject DatePipe

    filterMode: string = 'lenient'; // Default filter mode
    filterModes: any[] = [
        { label: 'Lenient', value: 'lenient' },
        { label: 'Strict', value: 'strict' }
    ];
    cols: any[] = [
        { field: 'id', header: 'ID' },
        { field: 'name', header: 'Name' },
        { field: 'state', header: 'State' },
        { field: 'deliveryMethod', header: 'Delivery Method' },
        /*{ field: 'creationDate', header: 'Creation Date' },*/
        { field: 'deliveryDate', header: 'Delivery Date' },
        { field: 'notificationCount', header: 'Notifications' },
        /*{ field: 'estimatedDeliveryDate', header: 'Estimated Delivery Date' },*/
        { field: 'content', header: 'Contents' },
        { field: 'type', header: 'Types' },
        { field: 'users', header: 'Users' },

    ];


  deliveryState$!: Observable<string>;
  private deliveryState!: string;

    constructor(private deliveryService: CustomDeliveryService, private emailService: EmailService,
                private messageService : MessageService,private confirmationService: ConfirmationService) {}

    ngOnInit() {
        this.loadDeliveries();
    }

    loadDeliveries() {
        this.deliveryService.getDeliveries().subscribe((deliveriesBE: CustomDelivery[]) => {
            this.deliveriesFull = deliveriesBE;

            this.deliveries = this.deliveriesFull.map((delivery: CustomDelivery) => {
                const notifications: TreeNode[] = (delivery.notifications as CustomNotification[]).map((notification: CustomNotification) => ({
                    data: {
                        /*id: notification.id,*/
                        users: notification.users.length,
                        content: notification.content,
                        type: notification.type.name,
                        kind: 'Notification'
                    }
                }));

                return {
                    data: {
                        id: delivery.id,
                        name: delivery.name,
                        state: delivery.state,
                        deliveryMethod: delivery.deliveryMethod.name,
                        creationDate: delivery.creationDate,
                        notificationCount: delivery.notifications.length,

                        deliveryDate: delivery.deliveryDate,
                        estimatedDeliveryDate: delivery.estimatedDeliveryDate,

                        kind: 'Delivery'
                    },
                    children: notifications
                };
            });

            console.log("deliveries: ", this.deliveries);
        });
    }

    formatDate(date: Date | undefined): string | null {
        return date ? this.datePipe.transform(date, 'd/M/yy, h:mm a') : null;
    }

    onGlobalFilter(event: Event, treeTable: any) {
        const inputElement = event.target as HTMLInputElement;
        treeTable.filterGlobal(inputElement.value, 'contains');
    }

    onColumnFilter(event: Event, colField: string, colFilterMatchMode: string, treeTable: any) {
        const inputElement = event.target as HTMLInputElement;
        treeTable.filter(inputElement.value, colField, colFilterMatchMode);
    }


  sendDeliveryNotifications(deliveryId: number) {
    // Method to fetch the delivery object by its ID from your backend
    this.deliveryService.getDeliveryById(deliveryId).subscribe(
      (delivery: CustomDelivery) => {

        delivery.notifications.forEach(notification => {
          const emailAddresses = notification.users.map(user => user.email).join(', ');

          // Replace placeholder with recipient's username
          const bodyWithRecipient = this.replaceRecipientPlaceholder(notification.content, notification.users[0]);

          const emailData = {
            to: emailAddresses,
            subject: notification.type.name,
            body: bodyWithRecipient // Assigning notification content with replaced placeholder to the body attribute
          };

          this.emailService.sendEmail(emailData).subscribe(
            response => {
              console.log('Email sent successfully to:', emailAddresses);
              // Optionally, handle success response here
            },
            error => {
              console.error('Failed to send email to:', emailAddresses, error);
              // Optionally, handle error here
            }
          );
        });
      },
      error => {
        console.error('Failed to fetch delivery:', error);
        // Optionally, handle error here
      }
    );
  }


  replaceRecipientPlaceholder(template: string, user: Actor): string {
    // Regular expression to match the placeholder
    const placeholderRegex = new RegExp('\\[\\$Recipient Name\\]', 'g');
    // Replace the placeholder with user's username
    return template.replace(placeholderRegex, user.username);
  }


  async sendDeliveryNotifications2(deliveryId: number) {
    try{
      const deliveryState = await this.getDeliveryState(deliveryId);
      if(deliveryState==='sent'){
        this.messageService.add({ severity: 'error', summary: 'Delivery Already sent', detail: 'Operation Cancelled', life: 3000 });
      }else {
        this.confirmationService.confirm({
          header: 'Are you sure you want to send the Notifications of this Delivery?',
          message: 'Please confirm to proceed.',
          accept: () => {

            // Update the state of the delivery
            /*delivery.state='sent';*/
            this.updateDeliveryState(deliveryId,'sent');

            // Method to fetch the delivery object by its ID from your backend
            this.deliveryService.getDeliveryById(deliveryId).subscribe(
              (delivery: CustomDelivery) => {
                const emailRequests: Observable<any>[] = [];

                delivery.notifications.forEach(notification => {
                  // Loop through users in the current notification
                  notification.users.forEach((user: Actor) => {
                    // Create a copy of the email request for each user
                    const emailData: EmailRequest = {
                      to: user.email, // Set recipient email for each user
                      subject: notification.type.name, // Subject from NotificationType
                      body: this.replaceRecipientPlaceholder(notification.content, user) // Replace placeholder in content
                    };



                    // Add email request to the array
                    emailRequests.push(this.emailService.sendEmail(emailData));
                    this.showSuccess();
                  });
                });



                // Send all email requests concurrently
                forkJoin(emailRequests).subscribe(
                  responses => {
                    console.log('All emails sent successfully:', responses);
                    // Optionally, handle success response here
                  },
                  error => {
                    console.error('Failed to send one or more emails:', error);
                    // Optionally, handle error here
                  }
                );
              },
              error => {
                console.error('Failed to fetch delivery:', error);
                // Optionally, handle error here
                this.showError();
              }
            );

          },
          reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Operation Cancelled', detail: 'You have declined', life: 3000 });
          }
        });

      }

    }catch (error) {
      console.error('Error sending delivery notifications:', error);
    }
  }

  showSuccess() {
    this.messageService.add({ severity: 'success', summary: 'Created', detail: 'Delivery sent successfully!' });
  }

  showError() {
    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Can\'t perform this action'  });
  }



  async getDeliveryState(deliveryId: number): Promise<string> {
    try {
      this.deliveryState = await this.deliveryService.getDeliveryState(deliveryId);
    } catch (error) {
      console.error('Error fetching delivery state:', error);
    }

    console.error('Salam: ',this.deliveryState);
    return this.deliveryState;
  }


  updateDeliveryState(deliveryId: number, newState: string): void {
    this.deliveryService.updateDeliveryState(deliveryId, newState)
      .subscribe(
        updatedDelivery => {
          console.log('Delivery state updated:', updatedDelivery);
          // Handle any further logic here
        },
        error => {
          console.error('Error updating delivery state:', error);
          // Handle error
        }
      );
  }

}

