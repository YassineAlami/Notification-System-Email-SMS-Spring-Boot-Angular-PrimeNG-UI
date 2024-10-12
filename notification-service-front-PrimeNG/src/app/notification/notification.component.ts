import {Component, OnInit, ViewChild} from '@angular/core';
import {NotificationService} from "../services/notification.service";
import {Notification} from "../models/Notification";

import { Table } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { HttpClientModule } from '@angular/common/http';
import { InputTextModule } from 'primeng/inputtext';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import {CommonModule, DatePipe, formatDate} from '@angular/common';
import {Sector} from "../models/Sector";


@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.css',
  providers: [DatePipe]
})
export class NotificationComponent implements OnInit {



  sectors: Sector[] = [];
  cols: any[] = [
    {header: 'Id', field: 'id'},
    {header: 'Content', field: 'content'},
    {header: 'Creation Date', field: 'creationDate'},
    {header: 'Type', field: 'type'},
    {header: 'Delivery', field: 'delivery'},
    {header: 'Action', field: "action"}
  ];
  visible: boolean = false;
  selectedSector: Sector = {id: 0, name: '', description: ''};


  searchValue: string | undefined;
  statuses!: any[];
  value!: string;


  loading: boolean = true;
  activityValues: number[] = [0, 100];


  notifications!:Notification[];
  userCount: number = 0;

  @ViewChild('dt') dt!: Table | undefined;


  constructor(private notificationService: NotificationService, private datePipe: DatePipe) {}

  ngOnInit() {
    this.notificationService.getNotifications().subscribe(
      (data: Notification[]) => {
        console.log(data);
        this.notifications = data;
        this.loading = false;
      },
      (error) => {
        console.log(error);
      }
    );
  }


  clear(table: Table) {
    table.clear();
  }

  getSeverity(notification: Notification): "success" | "info" | "warning" | "danger" {
    switch (notification.delivery.state) {
      case 'pending':
        return 'warning';

      case 'sent':
        return 'success';

      case '':
        return 'danger';

      case 'negotiation':
        return 'warning';

      case 'renewal':
        // Handle renewal state if needed (e.g., console.log or custom logic)
        console.log('Notification is in renewal state');
        return 'info'; // Default to info (adjust if desired)

      default:
        return 'info';
    }
  }

  applyFilterGlobal($event: any, stringVal: any) {
    if (this.dt){
      this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
    }
  }


  NotificationUserCount(notification : Notification){
    return this.userCount= notification.users.length;
  }

  /*protected readonly formatDate = formatDate;
  protected readonly formatDate = formatDate;*/

  formatDate(date: Date): string {
    return this.datePipe.transform(date, 'dd/MM/yy, HH:mm') || '';
  }
}
