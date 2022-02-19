import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RssFeedPageComponent } from './rss-feed-page.component';

describe('RssFeedPageComponent', () => {
  let component: RssFeedPageComponent;
  let fixture: ComponentFixture<RssFeedPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RssFeedPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RssFeedPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
