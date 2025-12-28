import { TestBed } from '@angular/core/testing';
import { ResourceInterceptor } from './resource.interceptor';
import { TokenService } from '../services/token.service';

describe('ResourceInterceptor', () => {
  let interceptor: ResourceInterceptor;

  beforeEach(() => {
    const tokenServiceSpy = jasmine.createSpyObj('TokenService', ['getAccessToken']);

    TestBed.configureTestingModule({
      providers: [
        ResourceInterceptor,
        { provide: TokenService, useValue: tokenServiceSpy }
      ]
    });
    interceptor = TestBed.inject(ResourceInterceptor);
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });
});
