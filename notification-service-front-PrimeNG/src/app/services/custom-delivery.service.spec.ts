import { TestBed } from '@angular/core/testing';

import { CustomDeliveryService } from './custom-delivery.service';

describe('CustomDeliveryService', () => {
  let service: CustomDeliveryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomDeliveryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
