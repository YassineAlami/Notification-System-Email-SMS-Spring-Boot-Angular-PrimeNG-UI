import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeliveryCreationComponent } from './delivery-creation.component';

describe('DeliveryCreationComponent', () => {
  let component: DeliveryCreationComponent;
  let fixture: ComponentFixture<DeliveryCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeliveryCreationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeliveryCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
