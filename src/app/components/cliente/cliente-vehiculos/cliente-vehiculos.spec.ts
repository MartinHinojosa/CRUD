import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClienteVehiculos } from './cliente-vehiculos';

describe('ClienteVehiculos', () => {
  let component: ClienteVehiculos;
  let fixture: ComponentFixture<ClienteVehiculos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClienteVehiculos]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClienteVehiculos);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
