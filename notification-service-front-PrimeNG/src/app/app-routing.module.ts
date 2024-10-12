import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MenubarComponent} from "./menubar/menubar.component";
import {HomeComponent} from "./home/home.component";
import {SectorComponent} from "./sector/sector.component";
import {CreateSectorComponent} from "./create-sector/create-sector.component";
import {ActorComponent} from "./actor/actor.component";
import {ActorActionsComponent} from "./actor-actions/actor-actions.component";
import {NotificationComponent} from "./notification/notification.component";
import {NotificationActionsComponent} from "./notification-actions/notification-actions.component";
import {NotificationCreationComponent} from "./notification-creation/notification-creation.component";
import {DeliveryComponent} from "./delivery/delivery.component";
import {DeliveryCreationComponent} from "./delivery-creation/delivery-creation.component";
import {DeliveryActionComponent} from "./delivery-action/delivery-action.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },

  { path: 'sectors', component: SectorComponent },
  { path: 'create-sectors', component: CreateSectorComponent },

  { path: 'actors', component: ActorComponent },
  { path: 'actors-actions', component: ActorActionsComponent },

  { path: 'notifications', component: NotificationComponent },
  { path: 'create-notifications', component: NotificationCreationComponent },
  { path: 'notification-actions', component: NotificationActionsComponent },

  { path: 'deliveries', component: DeliveryComponent },
  { path: 'create-deliveries', component: DeliveryCreationComponent },
  { path: 'deliveries-actions', component: DeliveryActionComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
