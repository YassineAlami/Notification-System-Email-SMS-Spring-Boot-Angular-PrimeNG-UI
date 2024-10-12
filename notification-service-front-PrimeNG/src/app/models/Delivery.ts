import {DeliveryMethod} from "./DeliveryMethod";
import {Notification} from "./Notification";

export interface Delivery {
  id: number
  name: string
  state: string
  deliveryMethod: DeliveryMethod

  creationDate:Date;
  deliveryDate?:Date | null;
  estimatedDeliveryDate?:Date | null;
  notifications: Notification[]
}
