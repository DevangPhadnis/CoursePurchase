import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasedCourseDetailsComponent } from './purchased-course-details.component';

describe('PurchasedCourseDetailsComponent', () => {
  let component: PurchasedCourseDetailsComponent;
  let fixture: ComponentFixture<PurchasedCourseDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchasedCourseDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PurchasedCourseDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
