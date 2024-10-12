import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../app.config";
import {Observable} from "rxjs";
import {Notification} from "../models/Notification";
import {Delivery} from "../models/Delivery";

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  constructor(private http: HttpClient) {
  }

  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  // Returns all the Deliveries
  getDeliveries(): Observable<Delivery[]> {
    const url = this.getApiUrl() + '/deliveries';
    // Using the constructed URL for making API calls or other operations
    return this.http.get<Delivery[]>(url);
  }

  // returns the length of the delivery's notifications list
  getNumberOfNotifications(delivery: Delivery): number {
    if (!delivery || !delivery.notifications) {
      return 0; // Handle cases where delivery or notifications might be null or undefined
    }
    return delivery.notifications.length;
  }


  public saveDelivery(delivery: Delivery): Observable<Delivery> {
    const url = this.getApiUrl() + '/deliveries';
    return this.http.post<Delivery>(url, delivery);
  }


  public assignNotificationsToDelivery(deliveryId: number, notificationIds: number[]): Observable<Delivery> {
    const url = `${this.getApiUrl()}/deliveries/${deliveryId}/notifications`;
    return this.http.post<Delivery>(url, notificationIds);
  }


  // Get delivery state by ID
  getDeliveryState(deliveryId: number): Observable<string> {
    const url = `${this.getApiUrl()}/deliveries/${deliveryId}/state`;
    return this.http.get<string>(url);
  }


  getDeliveryStatistics(): Observable<any> {
    const url = this.getApiUrl() + '/deliveries/statistics';
    return this.http.get<any>(url);
  }
}
