# Proyecto de Reducción de Huella de Carbono

Este proyecto es una aplicación JavaFX diseñada para ayudar a los usuarios a reducir su huella de carbono. La aplicación permite a los usuarios registrar sus actividades, calcular su huella de carbono y recibir recomendaciones para reducirla.

## Características

- **Registro de Actividades**: Los usuarios pueden registrar diferentes actividades y su impacto en la huella de carbono.
- **Cálculo de Huella de Carbono**: La aplicación calcula la huella de carbono basada en las actividades registradas.
- **Recomendaciones**: Los usuarios reciben recomendaciones personalizadas para reducir su huella de carbono.
- **Comparación**: Los usuarios pueden comparar su huella de carbono con la de otros usuarios.
- **Perfil de Usuario**: Los usuarios pueden gestionar su perfil y ver un resumen de su impacto.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal.
- **JavaFX**: Framework para la creación de interfaces gráficas.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **Hibernate**: Framework de mapeo objeto-relacional (ORM) para la persistencia de datos.
- **MySQL**: Base de datos utilizada para almacenar la información.

## Estructura del Proyecto

- `src/main/java/com/github/jcapitanmoreno`: Contiene el código fuente de la aplicación.
- `src/main/resources/com/github/jcapitanmoreno/views`: Contiene los archivos FXML para las vistas de la aplicación.
- `src/main/resources/com/github/jcapitanmoreno/views/css`: Contiene los archivos CSS para el estilo de la aplicación.
- `src/main/resources/com/github/jcapitanmoreno/views/img`: Contiene las imágenes utilizadas en la aplicación.

## Instalación y Ejecución

1. **Clonar el repositorio**:
    ```bash
    git clone https://github.com/jcapitanmoreno/reduccion-huella-carbono.git
    cd reduccion-huella-carbono
    ```

2. **Configurar la base de datos**:
    - Crear una base de datos MySQL y actualizar las credenciales en el archivo `src/main/resources/hibernate.cfg.xml`.

3. **Construir el proyecto**:
    ```bash
    mvn clean install
    ```

4. **Ejecutar la aplicación**:
    ```bash
    mvn javafx:run
    ```

## Uso

1. **Iniciar sesión**: Los usuarios pueden iniciar sesión o registrarse.
2. **Registrar Actividades**: Los usuarios pueden añadir nuevas actividades y su impacto.
3. **Ver Huella de Carbono**: Los usuarios pueden ver un resumen de su huella de carbono.
4. **Recibir Recomendaciones**: Los usuarios pueden recibir recomendaciones para reducir su huella de carbono.
5. **Comparar Impacto**: Los usuarios pueden comparar su impacto con el de otros usuarios.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, sigue los siguientes pasos para contribuir:

1. **Fork el repositorio**.
2. **Crea una nueva rama**:
    ```bash
    git checkout -b feature/nueva-funcionalidad
    ```
3. **Realiza tus cambios**.
4. **Haz commit de tus cambios**:
    ```bash
    git commit -m 'Añadir nueva funcionalidad'
    ```
5. **Sube tus cambios**:
    ```bash
    git push origin feature/nueva-funcionalidad
    ```
6. **Abre un Pull Request**.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo `LICENSE` para más detalles.
