import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {provideAnimations} from "@angular/platform-browser/animations"

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import {RouterLink, RouterLinkActive, RouterModule, RouterOutlet} from '@angular/router';
import {NgIf} from "@angular/common";
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

//Mine
import { HomeComponent } from './home/home.component';
import { SectorComponent } from './sector/sector.component';
import { SectorActionsComponent } from './sector-actions/sector-actions.component';
import { ActorComponent } from './actor/actor.component';
import { ActorActionsComponent } from './actor-actions/actor-actions.component';
import { CreateSectorComponent } from './create-sector/create-sector.component';
import { NotificationCreationComponent } from './notification-creation/notification-creation.component';
import { NotificationActionsComponent } from './notification-actions/notification-actions.component';
import { DeliveryComponent } from './delivery/delivery.component';
import { DeliveryCreationComponent } from './delivery-creation/delivery-creation.component';
import { DeliveryActionComponent } from './delivery-action/delivery-action.component';

//PrimeNG
import { InputTextModule } from 'primeng/inputtext'; // Import InputTextModule
import {ButtonModule} from "primeng/button";
import { MenubarComponent } from './menubar/menubar.component';
import { MenubarModule } from 'primeng/menubar';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { BadgeModule } from 'primeng/badge';
import {ToastModule} from "primeng/toast";
import {MenuModule} from "primeng/menu";
import {CardModule} from "primeng/card";
import {TableModule} from "primeng/table";
import {IconFieldModule} from "primeng/iconfield";
import {InputIconModule} from "primeng/inputicon";
import {SplitButtonModule} from "primeng/splitbutton";
import {DialogModule} from "primeng/dialog";
import {SpeedDialModule} from "primeng/speeddial";
import {InputTextareaModule} from "primeng/inputtextarea";
import {RippleModule} from "primeng/ripple";
import { MessageService, ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import {DropdownModule} from "primeng/dropdown";
import { NotificationComponent } from './notification/notification.component';
import {MultiSelectModule} from "primeng/multiselect";
import {TagModule} from "primeng/tag";
import { TooltipModule } from 'primeng/tooltip';
import {StepperModule} from "primeng/stepper";
import {SplitterModule} from "primeng/splitter";
import {TabMenuModule} from "primeng/tabmenu";
import { PickListModule } from 'primeng/picklist';
import { DragDropModule } from '@angular/cdk/drag-drop';  // Import DragDropModule
import {TreeTableModule} from "primeng/treetable";
import {SelectButtonModule} from "primeng/selectbutton";
import {DataViewModule} from "primeng/dataview";
import {DividerModule} from "primeng/divider";
import {TabViewModule} from "primeng/tabview";
import {CalendarModule} from "primeng/calendar";
import {MeterGroupModule} from "primeng/metergroup";
import { TimelineModule } from 'primeng/timeline';
import { GalleriaModule } from 'primeng/galleria';
import { AnimateOnScrollModule } from 'primeng/animateonscroll';
import {KnobModule} from "primeng/knob";
import {ScrollTopModule} from "primeng/scrolltop";
import {ChartModule} from "primeng/chart";



/*FontAwesome*/
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';









@NgModule({
  declarations: [
    AppComponent, MenubarComponent, HomeComponent, SectorComponent, CreateSectorComponent, ActorComponent,
    ActorActionsComponent, SectorActionsComponent, NotificationComponent, NotificationCreationComponent,
    NotificationActionsComponent, DeliveryComponent, DeliveryCreationComponent, DeliveryActionComponent
  ],
  imports: [PickListModule, DragDropModule,
    BrowserModule, HttpClientModule, RouterOutlet, NgIf, RouterLink, AppRoutingModule, RouterModule, FormsModule,
    RouterLinkActive, ConfirmDialogModule,


    /*FontAwesom*/
    FontAwesomeModule, // Import the module

    /*PrimeNG*/
    InputTextModule, ButtonModule, MenubarModule, RippleModule, SpeedDialModule, InputTextareaModule, DialogModule,
    AvatarModule, AvatarGroupModule, BadgeModule, ToastModule, MenuModule, CardModule, TableModule, IconFieldModule,
    InputIconModule, SplitButtonModule, MessagesModule, DropdownModule, MultiSelectModule, TagModule,
    TooltipModule, TabMenuModule, StepperModule, SplitterModule, DataViewModule, DividerModule, TreeTableModule,
    SelectButtonModule, TabViewModule, CalendarModule, MeterGroupModule, KnobModule, TimelineModule,
    GalleriaModule, AnimateOnScrollModule, ScrollTopModule, ChartModule, /*PickListModule*/
  ],
  providers: [
    /*PrimeNG*/
    provideAnimations(), MessageService, ConfirmationService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
