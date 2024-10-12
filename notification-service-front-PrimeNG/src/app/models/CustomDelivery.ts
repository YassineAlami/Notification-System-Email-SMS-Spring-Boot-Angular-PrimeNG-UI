import {DeliveryMethod} from "./DeliveryMethod";
import {CustomNotification} from "./CustomNotification";

export interface CustomDelivery {
    id: number;
    name: string;
    state: string;
    deliveryMethod: DeliveryMethod;
    creationDate: Date;
    deliveryDate?: Date;
    estimatedDeliveryDate?: Date;
    notifications: CustomNotification[];
}
