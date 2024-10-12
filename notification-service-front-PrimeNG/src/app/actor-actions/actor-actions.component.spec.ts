import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActorActionsComponent } from './actor-actions.component';

describe('ActorActionsComponent', () => {
  let component: ActorActionsComponent;
  let fixture: ComponentFixture<ActorActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ActorActionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ActorActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
