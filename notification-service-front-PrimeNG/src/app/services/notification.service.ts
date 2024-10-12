import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../app.config";
import {Observable} from "rxjs";
import {Actor} from "../models/Actor";
import {Notification} from "../models/Notification";
import {Delivery} from "../models/Delivery";
import {NotificationStatistics} from "../models/NotificationStatistics";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) { }

  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  // Returns all the notifications from the DB
  getNotifications(): Observable<Notification[]> {
    const url = this.getApiUrl() + '/notifications';
    // Use the constructed URL for making API calls or other operations
    return this.http.get<Notification[]>(url);
  }

  //adds a new Notifications to the list and returns it
  public saveNotification (notification : Notification):Observable<Notification>{
    const url = this.getApiUrl() + '/notifications';
    return this.http.post<Notification>(url, notification);
  }

  getNumberOfUsers(notification: Notification): number {
    if (!notification || !notification.users) {
      return 0; // Handle cases where notification or users might be null or undefined
    }
    return notification.users.length;
  }


  getFilteredNotifications(): Observable<Notification[]> {
    const url = this.getApiUrl() + '/notifications/filtered';
    // Use the constructed URL for making API calls or other operations
    return this.http.get<Notification[]>(url);
  }



  // Retrieves notification statistics from the backend
  getNotificationStatistics(): Observable<any> {
    const url = this.getApiUrl() + '/notifications/statistics';
    return this.http.get<any>(url);
  }




  // Method to get global notification statistics for a specific month and year
  public getNotificationGlobalStatistics(monthYear: string): Observable<NotificationStatistics> {
    const url = `${this.getApiUrl()}/notifications/global-statistics?monthYear=${monthYear}`;
    return this.http.get<NotificationStatistics>(url);
  }



}
