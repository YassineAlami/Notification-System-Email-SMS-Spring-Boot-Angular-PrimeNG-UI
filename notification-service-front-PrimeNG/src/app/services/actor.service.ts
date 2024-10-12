import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../app.config";
import {Observable} from "rxjs";
import {Actor} from "../models/Actor";

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  constructor(private http: HttpClient) { }

  //bringing the API URL from 'environment' of the config file
  getApiUrl() {
    return environment.apiUrl;
  }

  //returns all the sectors of the app
  getActors(): Observable<Actor[]> {
    const url = this.getApiUrl() + '/users';
    // Use the constructed URL for making API calls or other operations
    return this.http.get<Actor[]>(url);
  }


  //adds a new Sector to the list and returns it
  public saveActor (actor : Actor):Observable<Actor>{
    const url = this.getApiUrl() + '/users';
    return this.http.post<Actor>(url, actor);
  }


  //updates a Sector given its id
  updateSector(actor: Actor): Observable<Actor> {
    const url = this.getApiUrl() + '/users/' + actor.id;
    return this.http.put<Actor>(url, actor);
  }


  deleteActor(id: number): Observable<any> {
    const url = this.getApiUrl() + '/users/' + id;
    return this.http.delete(url); // Use http.delete for DELETE requests
  }



}
