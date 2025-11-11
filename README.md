# Sistema de Gestión de Taller Mecánico

Sistema web full stack desarrollado con **Angular** (frontend), **Spring Boot** (backend) y **PostgreSQL** (base de datos) para administrar vehículos y mantenimientos en un taller mecánico con control de acceso basado en roles.

---

## 📋 Tabla de Contenidos

1. [Características Principales](#características-principales)
2. [Tecnologías y Librerías](#tecnologías-y-librerías)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Arquitectura del Backend](#arquitectura-del-backend)
5. [Arquitectura del Frontend](#arquitectura-del-frontend)
6. [Base de Datos](#base-de-datos)
7. [Autenticación y Autorización](#autenticación-y-autorización)
8. [API Endpoints](#api-endpoints)
9. [Instalación y Configuración](#instalación-y-configuración)
10. [Ejecución del Proyecto](#ejecución-del-proyecto)

---

## 🎯 Características Principales

### Módulo de Vehículos
- ✅ Registrar nuevos vehículos (placa, marca, modelo, año, propietario)
- ✅ Consultar lista de vehículos registrados
- ✅ Editar información de vehículos
- ✅ Eliminar vehículos del sistema
- ✅ Ver historial de mantenimientos asociados por vehículo
- ✅ Selector de usuarios para asignar propietarios

### Módulo de Mantenimientos
- ✅ Registrar mantenimientos para vehículos existentes
- ✅ Consultar mantenimientos por vehículo
- ✅ Editar datos de mantenimientos (tipo de servicio, fecha, costo, observaciones)
- ✅ Eliminar mantenimientos
- ✅ Selector de usuarios para asignar responsables

### Sistema de Usuarios y Roles
- ✅ **Administrador**: Acceso completo, puede registrar clientes
- ✅ **Empleado**: Puede gestionar vehículos y mantenimientos
- ✅ **Cliente**: Solo lectura de sus vehículos y mantenimientos

### Autenticación y Seguridad
- ✅ Login con validaciones
- ✅ Hasheo de contraseñas con BCrypt
- ✅ Autenticación JWT (JSON Web Tokens)
- ✅ Protección de rutas con guards
- ✅ Control de acceso basado en roles (RBAC)
- ✅ Interceptores HTTP para agregar tokens automáticamente

---

## 🛠️ Tecnologías y Librerías

### Backend (Spring Boot)

#### Framework y Core
- **Spring Boot 3.5.7**: Framework principal de Java
- **Java 21**: Lenguaje de programación
- **Maven**: Gestor de dependencias y construcción

#### Persistencia de Datos
- **Spring Data JPA**: Abstracción para acceso a datos
- **Hibernate**: ORM (Object-Relational Mapping)
- **PostgreSQL Driver**: Conector para base de datos PostgreSQL

#### Seguridad
- **Spring Security**: Framework de seguridad
- **JWT (JSON Web Tokens)**: 
  - `jjwt-api` (v0.12.3)
  - `jjwt-impl` (v0.12.3)
  - `jjwt-jackson` (v0.12.3)
- **BCrypt**: Algoritmo de hasheo de contraseñas (incluido en Spring Security)

#### Desarrollo
- **Spring Boot DevTools**: Herramientas de desarrollo (hot reload)

#### Testing
- **Spring Boot Test**: Framework de testing
- **Spring Security Test**: Testing de seguridad

### Frontend (Angular)

#### Framework Core
- **Angular 20.3.0**: Framework principal
- **TypeScript 5.9.2**: Lenguaje de programación
- **RxJS 7.8.0**: Programación reactiva

#### Módulos Principales
- **@angular/common**: Utilidades comunes
- **@angular/forms**: Formularios reactivos
- **@angular/router**: Enrutamiento
- **@angular/platform-browser**: Renderizado en navegador
- **@angular/platform-server**: Server-Side Rendering (SSR)

#### SSR (Server-Side Rendering)
- **@angular/ssr 20.3.7**: Soporte para SSR
- **Express 5.1.0**: Servidor Node.js para SSR

#### Desarrollo
- **Angular CLI 20.3.7**: Herramientas de línea de comandos
- **Angular Build 20.3.7**: Sistema de construcción

#### Testing
- **Jasmine**: Framework de testing
- **Karma**: Test runner

---

## 📁 Estructura del Proyecto

```
crud-taller/
├── src/
│   ├── app/                          # Aplicación Angular (Frontend)
│   │   ├── components/               # Componentes de la aplicación
│   │   │   ├── welcome/             # Pantalla de bienvenida
│   │   │   ├── login/               # Componente de login
│   │   │   ├── register/            # Registro de empleados/admin
│   │   │   ├── register-cliente/    # Registro de clientes (solo admin)
│   │   │   ├── vehiculos/           # Componentes CRUD de vehículos
│   │   │   │   ├── vehiculos-list/  # Lista de vehículos
│   │   │   │   └── vehiculo-form/   # Formulario crear/editar
│   │   │   ├── mantenimientos/      # Componentes CRUD de mantenimientos
│   │   │   │   ├── mantenimientos-list/  # Lista de mantenimientos
│   │   │   │   └── mantenimiento-form/  # Formulario crear/editar
│   │   │   └── cliente/             # Componentes para clientes
│   │   │       └── cliente-vehiculos/   # Vista de vehículos (solo lectura)
│   │   ├── services/                # Servicios Angular
│   │   │   ├── auth.service.ts      # Servicio de autenticación
│   │   │   ├── vehiculo.service.ts  # Servicio de vehículos
│   │   │   ├── mantenimiento.service.ts  # Servicio de mantenimientos
│   │   │   └── usuario.service.ts   # Servicio de usuarios
│   │   ├── guards/                  # Guards de rutas
│   │   │   ├── auth.guard.ts        # Guard de autenticación
│   │   │   └── role.guard.ts        # Guard de roles
│   │   ├── interceptors/            # Interceptores HTTP
│   │   │   └── auth.interceptor.ts  # Interceptor para JWT
│   │   ├── app.routes.ts            # Configuración de rutas
│   │   ├── app.config.ts            # Configuración de la app
│   │   └── app.config.server.ts    # Configuración SSR
│   │
│   └── backend/                      # Aplicación Spring Boot (Backend)
│       └── src/main/java/com/example/backend/
│           ├── model/               # Entidades JPA
│           │   ├── Usuario.java     # Entidad Usuario
│           │   ├── Vehiculo.java    # Entidad Vehiculo
│           │   └── Mantenimiento.java  # Entidad Mantenimiento
│           ├── repository/          # Repositorios JPA
│           │   ├── UsuarioRepository.java
│           │   ├── VehiculoRepository.java
│           │   └── MantenimientoRepository.java
│           ├── service/             # Lógica de negocio
│           │   ├── AuthService.java
│           │   ├── VehiculoService.java
│           │   └── MantenimientoService.java
│           ├── controller/          # Controladores REST
│           │   ├── AuthController.java
│           │   ├── VehiculoController.java
│           │   └── MantenimientoController.java
│           ├── dto/                  # Data Transfer Objects
│           │   ├── LoginRequest.java
│           │   ├── LoginResponse.java
│           │   ├── RegisterRequest.java
│           │   ├── UsuarioDTO.java
│           │   ├── VehiculoDTO.java
│           │   └── MantenimientoDTO.java
│           ├── security/            # Configuración de seguridad
│           │   ├── SecurityConfig.java
│           │   ├── JwtUtil.java
│           │   └── JwtAuthenticationFilter.java
│           ├── BackendApplication.java
│           └── ServletInitializer.java
│       └── src/main/resources/
│           ├── application.properties  # Configuración de Spring Boot
│           └── init.sql              # Script SQL inicial (opcional)
│
├── package.json                      # Dependencias Node.js
├── pom.xml                           # Dependencias Maven
└── README.md                         # Este archivo
```

---

## 🏗️ Arquitectura del Backend

### Patrón de Arquitectura: **MVC (Model-View-Controller)**

El backend sigue el patrón MVC con las siguientes capas:

#### 1. **Model (Modelo) - Entidades JPA**

Las entidades representan las tablas de la base de datos:

**Usuario.java**
```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    private String nombre;
    private String correo;
    private String passwordHash;
    private String rol;
    private Boolean activo;
    
    @OneToMany(mappedBy = "usuario")
    private List<Vehiculo> vehiculos;
}
```

**Características:**
- Anotaciones JPA: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`
- Relaciones: `@OneToMany`, `@ManyToOne`
- Validaciones: `@Column(nullable = false)`

#### 2. **Repository (Repositorio) - Acceso a Datos**

Interfaces que extienden `JpaRepository`:

```java
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByUsuarioIdUsuario(Long idUsuario);
}
```

**Métodos disponibles automáticamente:**
- `save()`, `findById()`, `findAll()`, `delete()`, `deleteById()`
- Métodos personalizados con convención de nombres

#### 3. **Service (Servicio) - Lógica de Negocio**

Contiene la lógica de negocio y transformaciones:

**VehiculoService.java**
```java
@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;
    
    public List<VehiculoDTO> findAll() {
        return vehiculoRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private VehiculoDTO convertToDTO(Vehiculo vehiculo) {
        // Transformación de entidad a DTO
    }
}
```

**Métodos principales:**
- `findAll()`: Obtener todos los registros
- `findById()`: Buscar por ID
- `save()`: Crear nuevo registro
- `update()`: Actualizar registro existente
- `delete()`: Eliminar registro

#### 4. **Controller (Controlador) - API REST**

Expone endpoints HTTP:

**VehiculoController.java**
```java
@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;
    
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> getAll() {
        return ResponseEntity.ok(vehiculoService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody VehiculoDTO dto) {
        // Validación y creación
    }
}
```

**Anotaciones utilizadas:**
- `@RestController`: Controlador REST
- `@RequestMapping`: Mapeo de rutas base
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Métodos HTTP
- `@PathVariable`: Parámetros de ruta
- `@RequestBody`: Cuerpo de la petición
- `@CrossOrigin`: Configuración CORS

#### 5. **DTO (Data Transfer Objects)**

Objetos para transferencia de datos entre capas:

```java
public class VehiculoDTO {
    @JsonProperty("idVehiculo")
    private Long idVehiculo;
    
    private String placa;
    private String marca;
    private String modelo;
    
    @JsonProperty("anio")
    private Integer año;
}
```

**Ventajas:**
- Desacoplamiento entre entidades y API
- Control de qué datos se exponen
- Transformación de nombres de campos

#### 6. **Security (Seguridad)**

**SecurityConfig.java**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

**JwtUtil.java**
- `generateToken()`: Genera token JWT
- `extractUsername()`: Extrae usuario del token
- `validateToken()`: Valida token

**JwtAuthenticationFilter.java**
- Filtro que intercepta peticiones
- Extrae token del header `Authorization`
- Valida y establece contexto de autenticación

---

## 🎨 Arquitectura del Frontend

### Patrón de Arquitectura: **Component-Based Architecture**

El frontend sigue una arquitectura basada en componentes con las siguientes capas:

#### 1. **Components (Componentes)**

Componentes standalone de Angular:

**Estructura de un componente:**
```typescript
@Component({
  selector: 'app-vehiculos-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './vehiculos-list.component.html',
  styleUrl: './vehiculos-list.component.css'
})
export class VehiculosListComponent implements OnInit {
  vehiculos: Vehiculo[] = [];
  loading = false;
  
  constructor(
    private vehiculoService: VehiculoService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}
  
  ngOnInit(): void {
    this.loadVehiculos();
  }
  
  loadVehiculos(): void {
    this.loading = true;
    this.vehiculoService.getAll().subscribe({
      next: (data) => {
        this.vehiculos = data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar vehículos';
        this.loading = false;
      }
    });
  }
}
```

**Características:**
- **Standalone Components**: Componentes independientes (Angular 14+)
- **Lifecycle Hooks**: `ngOnInit()`, `ngOnDestroy()`
- **Change Detection**: `ChangeDetectorRef` para detección manual
- **Reactive Forms**: Formularios reactivos con validaciones

#### 2. **Services (Servicios)**

Servicios para comunicación con API:

**vehiculo.service.ts**
```typescript
@Injectable({
  providedIn: 'root'
})
export class VehiculoService {
  private apiUrl = 'http://localhost:8080/api/vehiculos';
  private http = inject(HttpClient);
  
  getAll(): Observable<Vehiculo[]> {
    return this.http.get<Vehiculo[]>(`${this.apiUrl}`);
  }
  
  getById(id: number): Observable<Vehiculo> {
    return this.http.get<Vehiculo>(`${this.apiUrl}/${id}`);
  }
  
  create(vehiculo: Vehiculo): Observable<Vehiculo> {
    return this.http.post<Vehiculo>(`${this.apiUrl}`, vehiculo);
  }
  
  update(id: number, vehiculo: Vehiculo): Observable<Vehiculo> {
    return this.http.put<Vehiculo>(`${this.apiUrl}/${id}`, vehiculo);
  }
  
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

**Características:**
- `@Injectable`: Decorador para inyección de dependencias
- `providedIn: 'root'`: Singleton a nivel de aplicación
- `Observable`: Programación reactiva con RxJS
- `HttpClient`: Cliente HTTP de Angular

#### 3. **Guards (Guardias de Rutas)**

Protección de rutas:

**auth.guard.ts**
```typescript
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  if (!authService.isAuthenticated()) {
    router.navigate(['/login']);
    return false;
  }
  
  return true;
};
```

**role.guard.ts**
```typescript
export const roleGuard = (allowedRoles: string[]): CanActivateFn => {
  return (route, state) => {
    const authService = inject(AuthService);
    const user = authService.getUserData();
    
    if (!user || !allowedRoles.includes(user.rol)) {
      router.navigate(['/login']);
      return false;
    }
    
    return true;
  };
};
```

**Uso en rutas:**
```typescript
{
  path: 'admin/vehiculos',
  component: VehiculosListComponent,
  canActivate: [authGuard, roleGuard(['admin'])]
}
```

#### 4. **Interceptors (Interceptores)**

Interceptan peticiones HTTP:

**auth.interceptor.ts**
```typescript
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();
  
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        authService.logout();
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};
```

**Configuración:**
```typescript
export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withFetch(),
      withInterceptors([authInterceptor])
    )
  ]
};
```

#### 5. **Routing (Enrutamiento)**

Configuración de rutas:

**app.routes.ts**
```typescript
export const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register/:role', component: RegisterComponent },
  
  // Rutas protegidas
  {
    path: 'admin/vehiculos',
    component: VehiculosListComponent,
    canActivate: [authGuard, roleGuard(['admin'])]
  },
  // ...
];
```

**Características:**
- Rutas parametrizadas: `:id`, `:role`
- Guards de protección
- Lazy loading (opcional)

#### 6. **Reactive Forms (Formularios Reactivos)**

Formularios con validaciones:

```typescript
this.vehiculoForm = this.fb.group({
  placa: ['', [Validators.required, Validators.maxLength(20)]],
  marca: ['', [Validators.required, Validators.maxLength(50)]],
  modelo: ['', [Validators.required, Validators.maxLength(50)]],
  anio: [null],
  idUsuario: [null]
});
```

**Validadores:**
- `Validators.required`: Campo obligatorio
- `Validators.email`: Validación de email
- `Validators.minLength()`: Longitud mínima
- `Validators.maxLength()`: Longitud máxima
- Validadores personalizados

#### 7. **Server-Side Rendering (SSR)**

Configuración SSR:

**app.routes.server.ts**
```typescript
export const serverRoutes: ServerRoute[] = [
  {
    path: '',
    renderMode: RenderMode.Prerender
  },
  {
    path: 'vehiculos/editar/:id',
    renderMode: RenderMode.Server
  }
];
```

**Render Modes:**
- `Prerender`: Pre-renderizado estático
- `Server`: Renderizado en servidor (dinámico)

---

## 🗄️ Base de Datos

### Esquema de Base de Datos

#### Tabla: `usuarios`
```sql
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50) DEFAULT 'empleado',
    activo BOOLEAN DEFAULT TRUE
);
```

**Roles disponibles:**
- `admin`: Administrador con acceso completo
- `empleado`: Empleado del taller
- `cliente`: Cliente (solo lectura)

#### Tabla: `vehiculos`
```sql
CREATE TABLE vehiculos (
    id_vehiculo SERIAL PRIMARY KEY,
    placa VARCHAR(20) UNIQUE NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    año INT,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE SET NULL
);
```

#### Tabla: `mantenimientos`
```sql
CREATE TABLE mantenimientos (
    id_mantenimiento SERIAL PRIMARY KEY,
    id_vehiculo INT REFERENCES vehiculos(id_vehiculo) ON DELETE CASCADE,
    tipo_servicio VARCHAR(100) NOT NULL,
    fecha_servicio DATE NOT NULL,
    costo NUMERIC(10,2),
    notas TEXT,
    id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE SET NULL
);
```

### Configuración JPA

**application.properties**
```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/taller_db
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

**ddl-auto options:**
- `update`: Actualiza el esquema automáticamente
- `create`: Crea el esquema al iniciar
- `create-drop`: Crea y elimina al cerrar
- `validate`: Solo valida el esquema

---

## 🔐 Autenticación y Autorización

### Flujo de Autenticación

1. **Login:**
   ```
   Usuario → Frontend → POST /api/auth/login
   → Backend valida credenciales
   → Genera JWT token
   → Retorna token + datos usuario
   → Frontend guarda token en localStorage
   ```

2. **Peticiones Autenticadas:**
   ```
   Frontend → Interceptor agrega token
   → Header: Authorization: Bearer <token>
   → Backend valida token
   → Procesa petición
   ```

3. **Protección de Rutas:**
   ```
   Usuario intenta acceder a ruta protegida
   → authGuard verifica autenticación
   → roleGuard verifica rol
   → Permite o redirige
   ```

### JWT (JSON Web Tokens)

**Estructura del Token:**
```
Header.Payload.Signature
```

**Payload contiene:**
- `sub`: Correo del usuario
- `rol`: Rol del usuario
- `exp`: Fecha de expiración (24 horas)
- `iat`: Fecha de emisión

**Configuración:**
```java
private static final String SECRET_KEY = "tu_clave_secreta_muy_larga_y_segura";
private static final long EXPIRATION_TIME = 86400000; // 24 horas
```

### BCrypt para Contraseñas

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

// Al registrar
usuario.setPasswordHash(passwordEncoder.encode(password));

// Al validar
passwordEncoder.matches(password, passwordHash);
```

**Características:**
- Salt automático
- Resistente a ataques de fuerza bruta
- Cost factor configurable

---

## 📡 API Endpoints

### Autenticación

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| POST | `/api/auth/login` | Iniciar sesión | No requerida |
| POST | `/api/auth/register` | Registrar usuario | No requerida |
| GET | `/api/auth/usuarios` | Listar usuarios | Requerida |

### Vehículos

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| GET | `/api/vehiculos` | Listar todos | Requerida |
| GET | `/api/vehiculos/{id}` | Obtener por ID | Requerida |
| GET | `/api/vehiculos/usuario/{idUsuario}` | Por usuario | Requerida |
| POST | `/api/vehiculos` | Crear | Requerida |
| PUT | `/api/vehiculos/{id}` | Actualizar | Requerida |
| DELETE | `/api/vehiculos/{id}` | Eliminar | Requerida |

### Mantenimientos

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| GET | `/api/mantenimientos` | Listar todos | Requerida |
| GET | `/api/mantenimientos/{id}` | Obtener por ID | Requerida |
| GET | `/api/mantenimientos/vehiculo/{idVehiculo}` | Por vehículo | Requerida |
| GET | `/api/mantenimientos/usuario/{idUsuario}` | Por usuario | Requerida |
| POST | `/api/mantenimientos` | Crear | Requerida |
| PUT | `/api/mantenimientos/{id}` | Actualizar | Requerida |
| DELETE | `/api/mantenimientos/{id}` | Eliminar | Requerida |

---

## ⚙️ Instalación y Configuración

### Requisitos Previos

- **Node.js** v18 o superior
- **Angular CLI** v20 o superior
- **Java** 21
- **Maven** 3.6+
- **PostgreSQL** 12+

### 1. Configuración de Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE taller_db;

-- (Opcional) Ejecutar script inicial
-- Ver: src/backend/src/main/resources/init.sql
```

### 2. Configuración del Backend

Editar `src/backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taller_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Instalación de Dependencias

**Backend:**
```bash
cd src/backend
mvn clean install
```

**Frontend:**
```bash
cd crud-taller
npm install
```

---

## 🚀 Ejecución del Proyecto

### Backend (Spring Boot)

```bash
cd src/backend
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080`

### Frontend (Angular)

```bash
cd crud-taller
npm start
# o
ng serve
```

El frontend estará disponible en: `http://localhost:4200`

### Usuarios de Prueba

Si ejecutaste `init.sql`:

- **Admin:**
  - Correo: `admin@taller.com`
  - Contraseña: `password123`

- **Empleado:**
  - Correo: `empleado@taller.com`
  - Contraseña: `password123`

---

## 📝 Métodos y Patrones Utilizados

### Backend

#### Patrones de Diseño:
- **Repository Pattern**: Abstracción de acceso a datos
- **DTO Pattern**: Transferencia de datos
- **Service Layer Pattern**: Separación de lógica de negocio
- **Dependency Injection**: Inyección de dependencias con `@Autowired`

#### Métodos Principales:

**AuthService:**
- `login(LoginRequest)`: Autenticación y generación de JWT
- `register(RegisterRequest)`: Registro de usuarios
- `findAllUsuarios()`: Listar usuarios activos

**VehiculoService:**
- `findAll()`: Obtener todos los vehículos
- `findById(Long)`: Buscar por ID
- `findByUsuario(Long)`: Buscar por usuario
- `save(VehiculoDTO)`: Crear vehículo
- `update(Long, VehiculoDTO)`: Actualizar vehículo
- `delete(Long)`: Eliminar vehículo
- `convertToDTO(Vehiculo)`: Transformación a DTO

**MantenimientoService:**
- Métodos similares a VehiculoService
- `findByVehiculo(Long)`: Buscar por vehículo

### Frontend

#### Patrones de Diseño:
- **Component-Based Architecture**: Arquitectura basada en componentes
- **Service Pattern**: Servicios para lógica compartida
- **Observer Pattern**: RxJS Observables
- **Dependency Injection**: Inyección con `inject()` o constructor

#### Métodos Principales:

**AuthService:**
- `login(credentials)`: Iniciar sesión
- `register(data)`: Registrar usuario
- `logout()`: Cerrar sesión
- `getToken()`: Obtener token
- `getUserData()`: Obtener datos del usuario
- `isAuthenticated()`: Verificar autenticación

**VehiculoService:**
- `getAll()`: Observable<Vehiculo[]>
- `getById(id)`: Observable<Vehiculo>
- `getByUsuario(idUsuario)`: Observable<Vehiculo[]>
- `create(vehiculo)`: Observable<Vehiculo>
- `update(id, vehiculo)`: Observable<Vehiculo>
- `delete(id)`: Observable<void>

**Componentes:**
- `ngOnInit()`: Inicialización
- `loadVehiculos()`: Cargar datos
- `onSubmit()`: Envío de formularios
- `cancel()`: Cancelar operación

---

## 🔒 Seguridad

### Medidas Implementadas:

1. **Hasheo de Contraseñas**: BCrypt con salt automático
2. **JWT Tokens**: Tokens firmados con expiración
3. **CORS**: Configurado para permitir solo origen específico
4. **CSRF**: Deshabilitado para API REST (stateless)
5. **Validación de Entrada**: Validaciones en frontend y backend
6. **Protección de Rutas**: Guards en frontend
7. **Filtros de Seguridad**: JWT filter en backend

### Buenas Prácticas:

- ✅ Tokens almacenados en `localStorage` (considerar `httpOnly` cookies para producción)
- ✅ Validación de roles en backend y frontend
- ✅ Manejo de errores 401 (no autorizado)
- ✅ Logout automático al expirar token

---

## 🎨 Interfaz de Usuario

### Diseño:
- **Estilo Futurista**: Gradientes, glassmorphism, animaciones
- **Responsive**: Adaptable a diferentes tamaños de pantalla
- **UX Moderna**: Transiciones suaves, feedback visual

### Componentes de UI:
- Formularios con validación en tiempo real
- Tablas con paginación (opcional)
- Modales de confirmación
- Mensajes de error/éxito
- Estados de carga (spinners)

---

## 📚 Recursos Adicionales

### Documentación:
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Angular Documentation](https://angular.io/docs)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [JWT.io](https://jwt.io/)

### Herramientas de Desarrollo:
- **Postman**: Para probar endpoints API
- **pgAdmin**: Para gestión de base de datos
- **Angular DevTools**: Extensión de Chrome
- **Spring Boot DevTools**: Hot reload

---

## 📄 Licencia

Este proyecto es de uso educativo.

---

## 👥 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abre un Pull Request

---

## 📞 Soporte

Para preguntas o problemas, por favor abre un issue en el repositorio.

---

**Desarrollado con ❤️ usando Angular y Spring Boot**
