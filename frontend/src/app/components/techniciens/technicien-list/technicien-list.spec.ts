import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TechnicienList } from './technicien-list';

describe('TechnicienList', () => {
  let component: TechnicienList;
  let fixture: ComponentFixture<TechnicienList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TechnicienList],
    }).compileComponents();

    fixture = TestBed.createComponent(TechnicienList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
