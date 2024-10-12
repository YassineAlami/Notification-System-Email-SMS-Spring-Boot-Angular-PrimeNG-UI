import {Component, OnInit} from '@angular/core';
import {DeliveryService} from "../services/delivery.service";
import {NotificationService} from "../services/notification.service";
import {NavigationEnd, Router} from "@angular/router";
import {ViewportScroller} from "@angular/common";
import {NotificationStatistics} from "../models/NotificationStatistics";

class EventItem {
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',

})
export class HomeComponent implements OnInit{

  deliveryStats!: any;

  notificationStats: any;

  events: EventItem[];





  data: any;

  options: any;



  statistics: NotificationStatistics | undefined;
  monthYear: string = '05-2024';



  valueDeliveries = [
    /*{ label: 'No Date', color1: '#34d399', color2: '#fbbf24', value: 0, count: 0, icon: 'fa-solid fa-border-all' },*/
    { label: 'Delivered', color1: '#27d227', color2: '#efff5a', value: 25, count: 15, icon: 'fa-solid fa-paper-plane' },
    { label: 'Pending', color1: '#efff5a', color2: '#ee2502', value: 20, count: 17, icon: 'fa-solid fa-hourglass-half' },
    { label: 'Other', color1: '#ee2502', color2: '#c084fc', value: 10, count: 37, icon: 'fa-solid fa-ellipsis' }
  ];

  valueNotifications = [
    { label: 'Salary Change', color: '#34d399', value: 32, count:10 },
    { label: 'Meeting', color: '#fbbf24', value: 8 , count:10},
    { label: 'New Policy', color: '#60a5fa', value: 24, count:10 },
    { label: 'Event', color: '#c084fc', value: 10, count:10 },
    { label: 'Changement de Pension', color: '#ee5202', value: 20, count:10 }
  ];



  constructor(private deliveryService : DeliveryService, private notificationService: NotificationService,
              private router: Router, private viewportScroller: ViewportScroller) {
    this.events = [
      { status: 'Create', date: '15/10/2020 10:30', icon: 'fa-solid fa-star', color: '#21d5bc', image: 'game-controller.jpg' },
      { status: 'Aim', date: '15/10/2020 14:00', icon: 'fa-solid fa-crosshairs', color: '#de3546', image: 'baidada.jpg' },
      { status: 'Deliver', date: '15/10/2020 16:15', icon: 'fa-solid fa-check', color: '#7bda4a', image: 'boy.jpg' },
      /*{ status: 'Delivered', date: '16/10/2020 10:00', icon: 'pi pi-check', color: '#607D8B' }*/
    ];
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.viewportScroller.scrollToPosition([0, 0]);
      }
    });

    this.getDeliveryStatistics();

    this.getNotificationStatistics();


    this.loadStatistics();










    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');

    /*this.data = {
      labels: ['Total', 'Sent', 'Pending', 'No State', 'No Delivery', 'No User', 'Users No Delivery'],
      datasets: [
        {
          label: 'Current Month',
          borderColor: documentStyle.getPropertyValue('--bluegray-400'),
          pointBackgroundColor: documentStyle.getPropertyValue('--bluegray-400'),
          pointBorderColor: documentStyle.getPropertyValue('--bluegray-400'),
          pointHoverBackgroundColor: textColor,
          pointHoverBorderColor: documentStyle.getPropertyValue('--bluegray-400'),
          data: [65, 59, 90, 81, 56, 55, 40]
        },
        {
          label: 'Last Month',
          borderColor: documentStyle.getPropertyValue('--pink-400'),
          pointBackgroundColor: documentStyle.getPropertyValue('--pink-400'),
          pointBorderColor: documentStyle.getPropertyValue('--pink-400'),
          pointHoverBackgroundColor: textColor,
          pointHoverBorderColor: documentStyle.getPropertyValue('--pink-400'),
          data: [28, 48, 40, 19, 96, 27, 100]
        }
      ]
    };

    this.options = {
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        r: {
          grid: {
            color: textColorSecondary
          },
          pointLabels: {
            color: textColorSecondary
          }
        }
      }
    };*/

  }


  getDeliveryStatistics(): void {

    this.deliveryService.getDeliveryStatistics()
      .subscribe(data => {
        this.deliveryStats = data;
        this.updateValueDeliveriesArray();
      });
  }

  updateValueDeliveriesArray(): void {
    this.valueDeliveries[0].value = this.deliveryStats.sentDeliveriesPercentage;
    this.valueDeliveries[0].count = this.deliveryStats.sentDeliveries;
    this.valueDeliveries[1].value = this.deliveryStats.pendingDeliveriesPercentage;
    this.valueDeliveries[1].count = this.deliveryStats.pendingDeliveries;
    this.valueDeliveries[2].value = this.deliveryStats.otherDeliveriesPercentage;
    this.valueDeliveries[2].count = this.deliveryStats.otherDeliveries;
  }







  getNotificationStatistics(): void {
    this.notificationService.getNotificationStatistics()
      .subscribe(data => {
        this.notificationStats = data;
        this.updateValueNotificationsArray();
      });
  }

  updateValueNotificationsArray(): void {
    this.valueNotifications[0].value = this.notificationStats['Salary ChangeNotificationsPercentage'];
    this.valueNotifications[0].count = this.notificationStats['Salary ChangeNotificationsCount'];
    this.valueNotifications[1].value = this.notificationStats['MeetingNotificationsPercentage'];
    this.valueNotifications[1].count = this.notificationStats['MeetingNotificationsCount'];
    this.valueNotifications[2].value = this.notificationStats['New PolicyNotificationsPercentage'];
    this.valueNotifications[2].count = this.notificationStats['New PolicyNotificationsCount'];
    this.valueNotifications[3].value = this.notificationStats['EventNotificationsPercentage'];
    this.valueNotifications[3].count = this.notificationStats['EventNotificationsCount'];
    this.valueNotifications[4].value = this.notificationStats['Changement de PensionNotificationsPercentage'];
    this.valueNotifications[4].count = this.notificationStats['Changement de PensionNotificationsCount'];
  }



  loadStatistics(): void {
    this.notificationService.getNotificationGlobalStatistics(this.monthYear).subscribe(
      (stats: NotificationStatistics) => {
        this.statistics = stats;
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');

        this.data = {
          labels: ['Total', 'Sent', 'Pending', 'No State', 'No Delivery', 'No User', 'Users No Delivery'],
          datasets: [
            {
              label: 'Current Month',
              borderColor: documentStyle.getPropertyValue('--bluegray-400'),
              pointBackgroundColor: documentStyle.getPropertyValue('--bluegray-400'),
              pointBorderColor: documentStyle.getPropertyValue('--bluegray-400'),
              pointHoverBackgroundColor: textColor,
              pointHoverBorderColor: documentStyle.getPropertyValue('--bluegray-400'),
              data: [stats.totalNotifications, stats.notificationsWithSentDelivery, stats.notificationsWithPendingDelivery,
                stats.notificationsWithNoDeliveryState, stats.notificationsWithNoDelivery,
                stats.notificationsWithNoUser, stats.notificationsWithUsersNoDelivery]
            },
            {
              label: 'Last Month',
              borderColor: documentStyle.getPropertyValue('--pink-400'),
              pointBackgroundColor: documentStyle.getPropertyValue('--pink-400'),
              pointBorderColor: documentStyle.getPropertyValue('--pink-400'),
              pointHoverBackgroundColor: textColor,
              pointHoverBorderColor: documentStyle.getPropertyValue('--pink-400'),
              data: [10, 10, 10, 5, 7, 3, 3]
            }
          ]
        };

        this.options = {
          plugins: {
            legend: {
              labels: {
                color: textColor
              }
            }
          },
          scales: {
            r: {
              grid: {
                color: textColorSecondary
              },
              pointLabels: {
                color: textColorSecondary
              }
            }
          }
        };
      },
      (error) => {
        console.error('Error fetching notification statistics:', error);
      }
    );
  }

}
