import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MenuItem} from "primeng/api";
import {AuthService} from "../services/auth.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-menubar',
  templateUrl: './menubar.component.html',
  styleUrl: './menubar.component.css'
})
export class MenubarComponent implements OnInit {
  items: MenuItem[] | undefined;

  constructor() {
  }

  ngOnInit() {
    this.items = [
      {label: 'Home', routerLink: '/home'},
      {
        label: 'Sectors',
        items: [
          {label: 'Display', routerLink: '/sectors'},
          {label: 'Actions', routerLink: '/create-sectors'}
        ]
      },
      {
        label: 'Actors',
        items: [
          {label: 'Display', routerLink: '/actors'},
          {label: 'Actions', routerLink: '/actors-actions'}
        ]
      },
      {
        label: 'Notifications',
        items: [
          {label: 'Display', routerLink: '/notifications'},
          {label: 'Create', routerLink: '/create-notifications'},
          {label: 'Actions', routerLink: '/notification-actions'}
        ]
      },
      {
        label: 'Delivery',
        items: [
          {label: 'Display', routerLink: '/deliveries'},
          {label: 'Creation', routerLink: '/create-deliveries'},
          {label: 'Actions', routerLink: '/deliveries-actions'},
        ]
      }
    ];
  }
}
