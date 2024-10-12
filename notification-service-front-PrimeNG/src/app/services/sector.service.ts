import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Notification} from "../models/Notification";
import {Sector} from "../models/Sector";
import {environment} from "../app.config";

@Injectable({
  providedIn: 'root'
})
export class SectorService {

  constructor(private http: HttpClient) { }

  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  //returns all the sectors of the app
  getSectors(): Observable<Sector[]> {
    const url = this.getApiUrl() + '/sectors';
    // Use the constructed URL for making API calls or other operations
    return this.http.get<Sector[]>(url);
  }


  //adds a new Sector to the list and returns it
  public saveSector (sector : Sector):Observable<Sector>{
    const url = this.getApiUrl() + '/sectors';
    return this.http.post<Sector>(url, sector);
  }


  //updates a Sector given its id
  updateSector(sector: Sector): Observable<Sector> {
    const url = this.getApiUrl() + '/sectors/' + sector.id;
    return this.http.put<Sector>(url, sector);
  }


  deleteSector(id: number): Observable<any> {
    const url = this.getApiUrl() + '/sectors/' + id;
    return this.http.delete(url); // Use http.delete for DELETE requests
  }
}
