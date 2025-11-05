import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from '../../services/api.service';

interface UserProfile {
  id: number;
  name: string;
  lastName: string;
  email: string;
  rol: string;
}

interface ClientInfo {
  id: number;
  name: string;
  lastName: string;
  telephone: string;
  address: string;
}

interface Car {
  idCar: number;
  brand: string;
  model: string;
  yearCar: number;
  plate: string;
  idCliente: number;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './Dashboard.html',
  styleUrls: ['./Dashboard.css']
})
export class DashboardComponent implements OnInit {
  
  profile: UserProfile | null = null;
  clientInfo: ClientInfo | null = null;
  cars: Car[] = [];
  totalCars: number = 0;
  repairs: any[] = [];
  loading: boolean = true;
  error: string | null = null;
  activeSection: string = 'profile';
  isAdmin: boolean = false;
  isReceptionist: boolean = false;
  isClient: boolean = false;
  isMechanic: boolean = false;
  
  // Gestión de citas (admin/recepcionista)
  showNewAppointmentForm: boolean = false;
  allClients: any[] = [];
  availableCars: any[] = [];
  clientCars: any[] = []; // Carros del cliente seleccionado
  showNewCarForm: boolean = false;
  newAppointment: any = {
    clientId: null,
    carId: null,
    description: '',
    entryDate: '',
    estimatedDate: '',
    useExistingCar: true
  };
  newCar: any = {
    brand: '',
    model: '',
    yearCar: null,
    plate: ''
  };
  
  // Gestión de registro de usuarios (admin/recepcionista/mecanico)
  showNewUserForm: boolean = false;
  newUser: any = {
    name: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    rol: 'mecanico'
  };
  
  constructor(
    private apiService: ApiService,
    private router: Router
  ) {}
  
  ngOnInit() {
    // Obtener userId del localStorage (se guarda después del login)
    const userId = localStorage.getItem('userId');
    
    if (!userId) {
      // Si no hay userId, redirigir al login
      this.router.navigate(['/']);
      return;
    }
    
    this.loadDashboard(parseInt(userId));
  }
  
  loadDashboard(userId: number) {
    this.loading = true;
    this.apiService.getUserDashboard(userId).subscribe({
      next: (data) => {
        this.profile = data.profile;
        this.clientInfo = data.clientInfo;
        this.cars = data.cars || [];
        this.totalCars = data.totalCars || 0;
        
        // Determinar rol del usuario
        const rol = this.profile?.rol?.toLowerCase() || '';
        this.isAdmin = rol === 'administrador' || rol === 'admin';
        this.isReceptionist = rol === 'recepcionista' || rol === 'recepcion';
        this.isMechanic = rol === 'mecanico' || rol === 'mecánico';
        this.isClient = rol === 'cliente' || rol === 'client';
        
        // Cargar reparaciones si es cliente
        if (this.isClient && this.clientInfo) {
          this.loadRepairs();
        }
        
        // Cargar datos para admin/recepcionista
        if (this.isAdmin || this.isReceptionist) {
          this.loadAllClients();
          this.loadAllCars();
        }
        
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar el dashboard:', error);
        this.error = 'Error al cargar los datos del dashboard';
        this.loading = false;
      }
    });
  }
  
  setActiveSection(section: string) {
    this.activeSection = section;
  }
  
  logout() {
    localStorage.removeItem('userId');
    localStorage.removeItem('userEmail');
    this.router.navigate(['/']);
  }
  
  formatPlate(plate: string): string {
    return plate || 'Sin placa';
  }
  
  loadRepairs() {
    if (!this.clientInfo) return;
    
    this.apiService.getRepairsByClient(this.clientInfo.id).subscribe({
      next: (repairs) => {
        this.repairs = repairs || [];
      },
      error: (error) => {
        console.error('Error al cargar reparaciones:', error);
      }
    });
  }
  
  formatDate(dateString: string): string {
    if (!dateString) return 'N/A';
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    } catch {
      return dateString;
    }
  }
  
  getStatusBadgeClass(status: string): string {
    const statusLower = status?.toLowerCase() || '';
    if (statusLower.includes('completada')) return 'badge-success';
    if (statusLower.includes('proceso')) return 'badge-warning';
    if (statusLower.includes('pendiente')) return 'badge-info';
    if (statusLower.includes('cancelada')) return 'badge-danger';
    return 'badge-default';
  }
  
  loadAllClients() {
    console.log('Cargando clientes...');
    this.apiService.getAllClients().subscribe({
      next: (clients) => {
        this.allClients = clients || [];
        console.log('Clientes cargados:', this.allClients.length);
        console.log('Clientes:', this.allClients);
      },
      error: (error) => {
        console.error('Error al cargar clientes:', error);
        console.error('Error completo:', error);
        this.allClients = [];
      }
    });
  }
  
  loadAllCars() {
    this.apiService.getAllCars().subscribe({
      next: (cars) => {
        this.availableCars = cars || [];
        console.log('Carros cargados:', this.availableCars.length);
      },
      error: (error) => {
        console.error('Error al cargar carros:', error);
      }
    });
  }
  
  onClientSelected() {
    if (this.newAppointment.clientId) {
      // Cargar carros del cliente seleccionado
      this.apiService.getCarsByClient(parseInt(this.newAppointment.clientId)).subscribe({
        next: (cars) => {
          this.clientCars = cars || [];
          console.log('Carros del cliente:', this.clientCars.length);
        },
        error: (error) => {
          console.error('Error al cargar carros del cliente:', error);
          this.clientCars = [];
        }
      });
    } else {
      this.clientCars = [];
      this.newAppointment.carId = null;
    }
    this.newAppointment.useExistingCar = true;
    this.showNewCarForm = false;
  }
  
  toggleCarOption() {
    if (!this.newAppointment.useExistingCar) {
      this.newAppointment.carId = null;
      this.showNewCarForm = true;
    } else {
      this.showNewCarForm = false;
      this.newCar = {
        brand: '',
        model: '',
        yearCar: null,
        plate: ''
      };
    }
  }
  
  createCarAndAppointment() {
    if (!this.newAppointment.clientId) {
      alert('Por favor seleccione un cliente primero');
      return;
    }
    
    // Validar campos del carro nuevo
    if (!this.newCar.brand || !this.newCar.model) {
      alert('Por favor complete la marca y modelo del carro');
      return;
    }
    
    if (!this.newAppointment.description) {
      alert('Por favor complete la descripción del problema');
      return;
    }
    
    const carData = {
      brand: this.newCar.brand,
      model: this.newCar.model,
      yearCar: this.newCar.yearCar,
      plate: this.newCar.plate || '',
      idCliente: parseInt(this.newAppointment.clientId)
    };
    
    // Crear el carro primero
    this.apiService.createCar(carData).subscribe({
      next: (response) => {
        if (response.success) {
          // Usar el carro recién creado para la reparación
          const newCarId = response.car?.idCar || response.id;
          this.newAppointment.carId = newCarId;
          // Crear la reparación después de crear el carro
          setTimeout(() => {
            this.createRepair();
          }, 100);
        }
      },
      error: (error) => {
        console.error('Error al crear carro:', error);
        alert('Error al crear el carro: ' + (error.error?.message || 'Error desconocido'));
      }
    });
  }
  
  createRepair() {
    if (!this.newAppointment.carId || !this.newAppointment.description) {
      alert('Por favor complete todos los campos requeridos');
      return;
    }
    
    const userId = parseInt(localStorage.getItem('userId') || '0');
    
    const repairData = {
      carId: this.newAppointment.carId,
      description: this.newAppointment.description,
      entryDate: this.newAppointment.entryDate || new Date().toISOString(),
      estimatedDate: this.newAppointment.estimatedDate || null,
      status: 'Pendiente',
      createdBy: userId
    };
    
    this.apiService.createRepair(repairData).subscribe({
      next: (response) => {
        if (response.success) {
          alert('Cita/Reparación creada exitosamente');
          this.cancelNewAppointment();
        }
      },
      error: (error) => {
        console.error('Error al crear cita:', error);
        alert('Error al crear la cita. Por favor, intente nuevamente.');
      }
    });
  }
  
  createNewAppointment() {
    // Si está creando un carro nuevo, usar esa función
    if (!this.newAppointment.useExistingCar) {
      this.createCarAndAppointment();
      return;
    }
    
    // Si usa un carro existente, crear la reparación directamente
    this.createRepair();
  }
  
  cancelNewAppointment() {
    this.showNewAppointmentForm = false;
    this.showNewCarForm = false;
    this.newAppointment = {
      clientId: null,
      carId: null,
      description: '',
      entryDate: '',
      estimatedDate: '',
      useExistingCar: true
    };
    this.newCar = {
      brand: '',
      model: '',
      yearCar: null,
      plate: ''
    };
    this.clientCars = [];
  }
  
  // Gestión de registro de usuarios
  showRegisterUserForm() {
    this.showNewUserForm = true;
    this.newUser = {
      name: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      rol: 'mecanico'
    };
  }
  
  cancelNewUser() {
    this.showNewUserForm = false;
    this.newUser = {
      name: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      rol: 'mecanico'
    };
  }
  
  createNewUser() {
    // Validar campos
    if (!this.newUser.name || !this.newUser.lastName || !this.newUser.email || 
        !this.newUser.password || !this.newUser.confirmPassword || !this.newUser.rol) {
      alert('Por favor, complete todos los campos');
      return;
    }
    
    if (this.newUser.password !== this.newUser.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }
    
    if (this.newUser.password.length < 6) {
      alert('La contraseña debe tener al menos 6 caracteres');
      return;
    }
    
    // Preparar datos para el registro
    const userData = {
      name: this.newUser.name.trim(),
      lastName: this.newUser.lastName.trim(),
      email: this.newUser.email.trim(),
      password: this.newUser.password,
      rol: this.newUser.rol
    };
    
    this.apiService.registerUser(userData).subscribe({
      next: (response) => {
        if (response.success) {
          alert('Usuario registrado exitosamente');
          this.cancelNewUser();
        } else {
          alert(response.message || 'Error al registrar usuario');
        }
      },
      error: (error) => {
        console.error('Error al registrar usuario:', error);
        alert(error.error?.message || 'Error al registrar usuario. Por favor, intente nuevamente.');
      }
    });
  }
}

