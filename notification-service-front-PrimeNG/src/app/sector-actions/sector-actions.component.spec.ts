import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SectorActionsComponent } from './sector-actions.component';

describe('SectorActionsComponent', () => {
  let component: SectorActionsComponent;
  let fixture: ComponentFixture<SectorActionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SectorActionsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SectorActionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
