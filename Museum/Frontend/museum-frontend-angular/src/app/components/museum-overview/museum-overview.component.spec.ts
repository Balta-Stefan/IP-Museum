import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MuseumOverviewComponent } from './museum-overview.component';

describe('MuseumOverviewComponent', () => {
  let component: MuseumOverviewComponent;
  let fixture: ComponentFixture<MuseumOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MuseumOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MuseumOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
