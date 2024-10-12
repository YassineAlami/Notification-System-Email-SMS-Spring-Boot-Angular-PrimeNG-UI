import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../app.config";
import {Observable} from "rxjs";
import {Sector} from "../models/Sector";
import {NotificationType} from "../models/NotificationType";

@Injectable({
  providedIn: 'root'
})
export class NotificationTypeService {


  constructor(private http: HttpClient) { }

  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  //returns all the sectors of the app
  getNotificationTypes(): Observable<NotificationType[]> {
    const url = this.getApiUrl() + '/notification-types';
    // Use the constructed URL for making API calls or other operations
    return this.http.get<NotificationType[]>(url);
  }
}
