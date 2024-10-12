import {NotificationType} from "./NotificationType";
import {Actor} from "./Actor";
import {CustomDelivery} from "./CustomDelivery";


export class CustomNotification {
    id!: number;
    type!: NotificationType;
    content!: string;
    users!: Actor[];
    delivery!: CustomDelivery;
    creationDate!: Date;
}
