import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationCreationComponent } from './notification-creation.component';

describe('NotificationCreationComponent', () => {
  let component: NotificationCreationComponent;
  let fixture: ComponentFixture<NotificationCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotificationCreationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NotificationCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
