# ComunetMax

ComunetMax es un sistema integral que incluye una aplicación Front-end y un Back-end basado en una arquitectura de microservicios con Spring Boot y Spring Cloud. El proyecto gestiona diferentes dominios (Usuarios, Planes, Contacto y Cobertura) en bases de datos independientes, coordinados a través de un API Gateway y un Config Server. Implementa comunicación entre servicios mediante OpenFeign y tolerancia a fallos con Circuit Breaker.

## Requisitos Previos

* Docker y Docker Compose instalados en el sistema.
* Disponibilidad en el puerto 8080 para el API Gateway y el puerto correspondiente para la aplicación Front-end.

---

## Iniciar el Sistema (Front-end y Back-end)

Para levantar toda la infraestructura, incluyendo el Front-end, las bases de datos, Config Server, API Gateway y los microservicios, ejecuta desde la raíz del proyecto:

> ```bash
> docker-compose up
> ```

> Si es la primera vez que se ejecuta el proyecto o has realizado cambios recientes, usar:
>
> ```bash
> docker-compose up --build
> ```
>
> El archivo docker-compose.yml incluye healthchecks, por lo que el Gateway y los microservicios esperarán automáticamente a que el Config Server y las bases de datos estén listos antes de arrancar. El Front-end se conectará al Gateway una vez esté disponible.

Para detener todos los servicios:

> ```bash
> docker-compose down
> ```

También puedes iniciar los contenedores de base de datos manualmente:

> ```bash
> docker start db_usuarios
> docker start db_planes
> docker start db_cobertura
> ```

> En Linux, si hay errores por PostgreSQL local:
>
> ```bash
> sudo systemctl stop postgresql
> ```

---

## Compilación de Contenedores (Manual)

El siguiente script es por si se va a realizar una limpieza o compilación manual de los archivos .jar antes de levantar Docker. No es un paso obligatorio para el despliegue normal.

> ```bash
> cd Microservicios && \
> cd ms-usuarios && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-planes && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-contacto && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-cobertura && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-config-server && ./mvnw clean package -DskipTests && cd ../..
> ```

---

## Limpieza y Reconstrucción Completa del Entorno

Dado el caso de que de error al iniciar el docker (esto se debe a que se hayan quedado contenedores o volúmenes colgados), usar este script para limpiar y reconstruir todo desde cero:

> ```bash
> docker-compose down -v
> docker system prune -a --volumes -f
> 
> sudo systemctl stop postgresql
> 
> docker ps -a
> docker volume ls
> 
> docker-compose up --build
> docker-compose restart ms-gateway
> ```

---

## Ver Logs

Para monitorizar el comportamiento de los servicios específicos en tiempo real:

> ```bash
> docker-compose logs -f front-end
> docker-compose logs -f ms-usuarios
> docker-compose logs -f ms-planes
> docker-compose logs -f ms-contacto
> docker-compose logs -f ms-cobertura
> ```

---

## Comprobar Accesos y APIs

### Interfaz de Usuario (Front-end)

* Aplicación Web ->

> ```bash
> http://localhost:PORT
> ```

(Reemplazar PORT con el puerto asignado al frontend, ej. 80, 3000 o 4200)

### Endpoints del Back-end

En esta arquitectura, el API Gateway (puerto 8080) es el único punto de entrada público. Todo el tráfico hacia los microservicios debe enrutarse a través de él.

A través del Gateway (puerto 8080):

* Usuarios ->

> ```bash
> http://localhost:8080/ms-usuarios/api/usuarios
> ```

* Planes ->

> ```bash
> http://localhost:8080/ms-planes/api/planes
> ```

* Formularios (consumido por el front-end) ->

> ```bash
> http://localhost:8080/ms-contacto/api/contacto
> ```

* Cobertura ->

> ```bash
> http://localhost:8080/ms-cobertura/api/cobertura/
> ```

* Verificar estado de los municipios ->

> ```bash
> http://localhost:8080/ms-cobertura/api/cobertura/municipios
> ```

---

### Verificar documentación Swagger UI

La documentación interactiva de la API está disponible a través del Gateway. Puedes visualizarla y probar los endpoints en tu navegador:

> ```bash
> http://localhost:8080/webjars/swagger-ui/index.html#/municipio-controller/listarTodos
> ```

