import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {CustomDelivery} from "../models/CustomDelivery";
import {Delivery} from "../models/Delivery";
import {environment} from "../app.config";

@Injectable({
  providedIn: 'root'
})
export class CustomDeliveryService {
  private deliveriesUrl = 'http://localhost:8080/api/deliveries'; // Adjust the URL as needed

  constructor(private http: HttpClient) {}


  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  getDeliveries(): Observable<CustomDelivery[]> {
    return this.http.get<CustomDelivery[]>(this.deliveriesUrl);
  }



  // returns the length of the delivery's notifications list
  getNumberOfNotifications(delivery: CustomDelivery): number {
    if (!delivery || !delivery.notifications) {
      return 0; // Handle cases where delivery or notifications might be null or undefined
    }
    return delivery.notifications.length;
  }



/*  // Returns a specific Delivery given its id
  getDeliveryById(deliveryId: number): Observable<CustomDelivery> {
    const url = this.getApiUrl() + '/deliveries/';
    // Use the constructed URL for making API calls or other operations
    return this.http.get<CustomDelivery>(url, deliveryId);
  }*/

  getDeliveryById(deliveryId: number): Observable<CustomDelivery> {
    return this.http.get<CustomDelivery>(`${this.deliveriesUrl}/${deliveryId}`);
  }

  // Get delivery state by ID
/*  getDeliveryState(deliveryId: number): Observable<string> {
    return this.http.get(`${this.deliveriesUrl}/${deliveryId}/state`, { responseType: 'text' }).pipe(
      tap(response => console.log( response))
    );
  }*/

  async getDeliveryState(deliveryId: number): Promise<string> {
    try {
      const state = await this.http.get(`${this.deliveriesUrl}/${deliveryId}/state`, { responseType: 'text' }).toPromise();
      console.log('Delivery state:', state);
      return state as string;
    } catch (error) {
      console.error('Error fetching delivery state:', error);
      return '-'; // Return a default value or handle the error as needed
    }
  }

  updateDeliveryState(deliveryId: number, newState: string): Observable<CustomDelivery> {
    return this.http.patch<CustomDelivery>(`${this.deliveriesUrl}/${deliveryId}/state?newState=${newState}`, {});
  }

}
