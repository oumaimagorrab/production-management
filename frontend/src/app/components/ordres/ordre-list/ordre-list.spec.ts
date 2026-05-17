import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdreList } from './ordre-list';

describe('OrdreList', () => {
  let component: OrdreList;
  let fixture: ComponentFixture<OrdreList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrdreList],
    }).compileComponents();

    fixture = TestBed.createComponent(OrdreList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
