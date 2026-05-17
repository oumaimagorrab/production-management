import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdreForm } from './ordre-form';

describe('OrdreForm', () => {
  let component: OrdreForm;
  let fixture: ComponentFixture<OrdreForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdreForm],
    }).compileComponents();

    fixture = TestBed.createComponent(OrdreForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
