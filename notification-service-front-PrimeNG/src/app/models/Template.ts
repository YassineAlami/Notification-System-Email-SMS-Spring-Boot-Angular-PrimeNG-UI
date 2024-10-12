import {NotificationType} from "./NotificationType";

export interface Template {
  id: number;
  title: string;
  body: string;
  creationDate: string;
  notificationType: NotificationType[];
}
