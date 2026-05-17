import { TestBed } from '@angular/core/testing';

import { Ordre } from './ordre';

describe('Ordre', () => {
  let service: Ordre;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Ordre);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
