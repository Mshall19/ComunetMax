# ComunetMax


## Iniciar Base de Datos

```bash
docker-compose up
```

> Si es la primera vez que se ejecuta el proyecto, usar:
>
> ```bash
> docker-compose up --build
> ```

Detener servicios

>```bash
>docker-compose down
>```


También puedes iniciar los contenedores manualmente:

```bash
docker start db_usuarios
docker start db_peliculas
```

> En Linux, si hay errores por PostgreSQL local:
>
> ```bash
> sudo systemctl stop postgresql
> ```
> 
> Compilacion de contenedores
> 
> ```bash
> cd Microservicios && \
> cd ms-usuarios && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-planes && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-empresas && ./mvnw clean package -DskipTests && cd .. && \
> cd ms-config-server && ./mvnw clean package -DskipTests && cd ../..
> ```

---

## Limpieza y reconstrucción completa del entorno

Dado el caso de que de error al iniciar el docker **(esto se debe a que se hayan quedado contenedores o volúmenes colgados)**, usar este script para limpiar y reconstruir todo desde cero:
> ```bash
> # Limpieza completa
> docker-compose down -v
> docker system prune -a --volumes -f
> 
> # Si tienes PostgreSQL local corriendo, detenerlo
> sudo systemctl stop postgresql
> 
> # Verificar que no queden contenedores
> docker ps -a
> docker volume ls
> 
> # Reconstruir
> docker-compose up --build
> 
> docker-compose restart ms-gateway
> ```

## Verificar contenedores

```bash
docker ps
```

---

## Ver logs

```bash
docker logs servicio-usuarios
docker logs servicio-empresas
docker logs servicio-planes
```

---

## Comprobar APIs

**A través del Gateway (puerto 8080):**
- Usuarios →  http://localhost:8080/ms-usuarios/api/usuarios
- Empresas → http://localhost:8080/ms-empresas/api/empresas
- Planes →  http://localhost:8080/ms-planes/api/planes
- Formularios(no es posible visualizarlo en el navegador) → http://localhost:8080/ms-contacto/api/contacto


**Acceso directo a los microservicios:**
- Usuarios → [http://localhost:8083/api/usuarios](http://localhost:8083/api/usuarios)
- Empresas → [http://localhost:8082/api/empresas](http://localhost:8082/api/empresas)
- Planes → [http://localhost:8081/api/planes](http://localhost:8081/api/planes)
- Formularios(no es posible visualizarlo en el navegador) → [http://localhost:8086/api/contacto](http://localhost:8086/api/contacto)



