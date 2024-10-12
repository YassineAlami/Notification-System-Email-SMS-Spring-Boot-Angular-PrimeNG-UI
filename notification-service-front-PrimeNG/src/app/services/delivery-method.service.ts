import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../app.config";
import {Observable} from "rxjs";
import {Notification} from "../models/Notification";
import {DeliveryMethod} from "../models/DeliveryMethod";

@Injectable({
  providedIn: 'root'
})
export class DeliveryMethodService {

  constructor(private http: HttpClient) { }

  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  // Returns all the notifications from the DB
  getDeliveryMethods(): Observable<DeliveryMethod[]> {
    const url = this.getApiUrl() + '/delivery-methods';
    // Using the constructed URL for making API calls or other operations
    return this.http.get<DeliveryMethod[]>(url);
  }




}
