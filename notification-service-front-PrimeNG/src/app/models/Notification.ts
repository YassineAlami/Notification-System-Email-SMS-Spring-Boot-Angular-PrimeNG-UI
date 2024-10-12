import {Actor} from "./Actor";
import {NotificationType} from "./NotificationType";
import {Delivery} from "./Delivery";

export class Notification {
  id!: number;
  type!: NotificationType;
  content!: string;
  users!: Actor[];
  delivery!: Delivery;
  creationDate!: Date;
}
