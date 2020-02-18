# GameSquare

Web dedicada a la facilitación del desarrollo de videojuegos y la creación de contenido adicional para estos.
En ella participan tanto los desarrolladores de los videojuegos originales, como los creadores de contenido adicional/mods, conocidos como modders.

+ Funcionalidad pública: Buscar videojuegos y mods para descargar
+ Funcionalidad privada: Publicar un videojuego, subir un mod, comentar sobre un mod/videojuego
+ Servicio interno: Notificar al desarrollador con un correo cuando se publique un mod de su juego, notificar a los usuarios a través de un tablón de noticias cuando se publique un juego nuevo.

## Entidades principales

+ Usuario: Posee nombre de usuario, correo, contraseña, fecha de registro y número de comentarios. Puede ser a su vez un desarrollador y/o modder. Entidad básica que interactúa con la página, puede escribir comentarios y descargar juegos y mods.
  + Desarrollador: Es una extensión del usuario, que tiene la opción de publicar juegos. Posee, por tanto, los juegos que tiene publicados. 
  + Modder: Igual que la entidad anterior, pero en lugar de juegos posee mods publicados.
+ Software: Posee nombre, fecha de publicación, género, descripción, autor/es, desarrollador y lista de comentarios.
  + Videojuego: extiende sofware.
  + Mod: extiende software, posee el nombre del videojuego al que pertenece.
+ Comentario : Posee autor, cuerpo, fecha de publicación y nombre de videojuego/mod al que pertenece.

## Base de datos relacional

![image](Esquema_relacional_DAD.jpg)

## Esquema de plantillas de la web

![image](esquema_plantillas_DAD.jpg)

## Capturas de las patallas de la web
![image](screenshots/main.png)
Pantalla de inicio. Permite rigistrarse o logearse, hacer una búsqueda, consultar los últimos juegos y mods además de acceder a la lista completa de cada uno de ellos



![image](screenshots/login.png)
Pantalla de login. Permite acceder a una cuenta que esté en la base de datos



![image](screenshots/register.png)
Pantalla de registro. Permite crear una nueva cuenta en la base de datos



![image](screenshots/profile.png)
Pantalla de perfil personal. Permite consultar los datos del perfil, acceder a la pantalla de modificación de perfil y publicar tanto juegos como mods y acceder a ellos



![image](screenshots/modify_profile.png)
Pantalla de modificación de datos del perfil. Permite modificar informoación personal del perfil



![image](screenshots/search.png)
Pantalla de búsqueda. Muestra los resultados introducidos en la barra de búsqueda



![image](screenshots/mod.png)
Pantalla de datos y comentarios de un mod. Muestra todos los datos relacionados con el mod en cuestión y hacer comentarios si el usuario está logeado en la página.



![image](screenshots/game.png)
Pantalla de datos, mods y comentarios de un juego. Muestra todos los datos relacionados con el mod en cuestión junto con una lista con sus mods y hacer comentarios si el usuario está logeado en la página.

## Integrantes

+ Alejandro	Hernández Pérez -> a.hernandezp.2016@alumnos.urjc.es , Darkc0m
+ Mario	Márquez Balduque -> m.marquezb.2016@alumnos.urjc.es , Rinkashiki
+ Javier Bravo Bolívar ->	j.bravo.2016@alumnos.urjc.es , javierb1984
