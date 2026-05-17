import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicienForm } from './technicien-form';

describe('TechnicienForm', () => {
  let component: TechnicienForm;
  let fixture: ComponentFixture<TechnicienForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicienForm],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicienForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
