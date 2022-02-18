import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MuseumListCardComponent } from './museum-list-card.component';

describe('MuseumListCardComponent', () => {
  let component: MuseumListCardComponent;
  let fixture: ComponentFixture<MuseumListCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MuseumListCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MuseumListCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
