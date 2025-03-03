# Sistema de Reservas de Hotel

## Descripción
Esta aplicación JavaFX permite a los usuarios visualizar y reservar habitaciones disponibles en un hotel. Implementa una interfaz gráfica moderna con estilos CSS personalizados y sigue el patrón de diseño Modelo-Vista-Controlador (MVC).

## Características
- Visualización de diferentes tipos de habitaciones (Single, Double, Twin, Suite)
- Filtrado de habitaciones por categoría
- Detalles completos de cada habitación con imágenes
- Formulario de reserva con validaciones
- Confirmación de reservas mediante alertas
- Interfaz de usuario moderna con estilos CSS personalizados

## Estructura del Proyecto
El proyecto sigue el patrón MVC:

### Modelo
- `Room`: Representa una habitación de hotel
- `RoomType`: Enum con los diferentes tipos de habitaciones
- `Reservation`: Representa una reserva de habitación
- `HotelService`: Servicio que gestiona las habitaciones y reservas

### Vista
- `hotel-view.fxml`: Vista principal de la aplicación
- `hotel-styles.css`: Estilos CSS para la interfaz

### Controlador
- `HotelController`: Controlador principal que maneja la lógica de la aplicación

## Requisitos
- Java 11 o superior
- JavaFX 11 o superior

## Ejecución
Para ejecutar la aplicación, utiliza el siguiente comando:

```
mvn javafx:run
```

O ejecuta la clase `HotelApplication` desde tu IDE.

## Imágenes
Para una experiencia completa, reemplaza los archivos de marcador de posición en `src/main/resources/com/example/hotelv2/images/` con imágenes reales de habitaciones.

## Autor
Desarrollado como proyecto de ejemplo para demostrar el uso de JavaFX con CSS y el patrón MVC. 