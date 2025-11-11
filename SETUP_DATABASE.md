# Configuración de la Base de Datos PostgreSQL

## Pasos para Configurar PostgreSQL

### 1. Crear la Base de Datos en pgAdmin4

1. Abre **pgAdmin4**
2. Conecta a tu servidor PostgreSQL (generalmente `localhost:5432`)
3. Haz clic derecho en **Databases** → **Create** → **Database**
4. En el campo **Database name**, ingresa: `taller_db`
5. Haz clic en **Save**

### 2. Verificar/Configurar Credenciales

Abre el archivo `src/backend/src/main/resources/application.properties` y verifica que las credenciales coincidan con tu configuración de PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taller_db
spring.datasource.username=postgres        # Cambia si tu usuario es diferente
spring.datasource.password=postgres        # Cambia por tu contraseña
```

**Si tus credenciales son diferentes**, edita estos valores en `application.properties`.

### 3. Crear las Tablas Automáticamente

Las tablas se crearán **automáticamente** cuando inicies el backend Spring Boot gracias a la configuración:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Esto significa que:
- Si las tablas no existen, se crearán
- Si existen, se actualizarán según las entidades JPA
- **No necesitas ejecutar scripts SQL manualmente**

### 4. (Opcional) Insertar Usuarios de Prueba

Si quieres tener usuarios de prueba desde el inicio, puedes ejecutar el script SQL ubicado en:

`src/backend/src/main/resources/init.sql`

**Para ejecutarlo en pgAdmin4:**

1. Abre pgAdmin4
2. Selecciona la base de datos `taller_db`
3. Haz clic en **Tools** → **Query Tool**
4. Abre el archivo `init.sql` y copia su contenido
5. Pega el contenido en el Query Tool
6. Haz clic en **Execute** (F5)

**Usuarios de prueba creados:**
- **Admin**: 
  - Correo: `admin@taller.com`
  - Contraseña: `password123`
- **Empleado**: 
  - Correo: `empleado@taller.com`
  - Contraseña: `password123`

### 5. Verificar la Conexión

1. Inicia el backend Spring Boot:
   ```bash
   cd src/backend
   mvn spring-boot:run
   ```

2. Si la conexión es exitosa, verás en la consola:
   - Mensajes de Hibernate creando/actualizando tablas
   - El servidor iniciando en `http://localhost:8080`

3. Si hay errores de conexión, verifica:
   - Que PostgreSQL esté ejecutándose
   - Que la base de datos `taller_db` exista
   - Que las credenciales en `application.properties` sean correctas
   - Que el puerto 5432 esté disponible

## Estructura de las Tablas

Las siguientes tablas se crearán automáticamente:

1. **usuarios** - Almacena información de usuarios del sistema
2. **vehiculos** - Almacena información de vehículos
3. **mantenimientos** - Almacena información de mantenimientos realizados

## Solución de Problemas

### Error: "Connection refused"
- Verifica que PostgreSQL esté ejecutándose
- Verifica que el puerto 5432 esté abierto

### Error: "Database does not exist"
- Crea la base de datos `taller_db` en pgAdmin4

### Error: "Password authentication failed"
- Verifica el usuario y contraseña en `application.properties`
- Asegúrate de que coincidan con tu configuración de PostgreSQL

### Error: "Permission denied"
- Verifica que el usuario de PostgreSQL tenga permisos para crear tablas

