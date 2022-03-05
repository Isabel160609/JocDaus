# Juego de dados

Este es el proyecto final del bootcamp de Java de la IT-Academy de Barcelona activa.

## Background

El juego de dados se juega con dos dados. En caso de que el resultado de la suma de ambos dados sea 7, la partida es ganada, sino es perdida. Un jugador puede ver un listado

de todas las tiradas que ha realizado y el porcentaje de éxito.

Para poder jugar al juego y realizar un tirón, un usuario debe registrarse con un nombre no repetido. Al crearse, se le asigna un identificador numérico único y una fecha de
registro. Si el usuario así lo desea, puedes no añadir ningún nombre y se llamará “ANÓNIMO”. Puede haber más de un jugador "ANÓNIMO".

Cada jugador puede ver un listado de todas las tiradas que ha realizado, con el valor de cada dado y si se ha ganado o no la partida. Además, puede saber su porcentaje de 
éxito por todas las tiradas que ha realizado.

No se puede eliminar una partida en concreto, pero sí que se puede eliminar todo el listado de tiradas por un jugador.

El software debe permitir listar a todos los jugadores que hay en el sistema, el porcentaje de éxito de cada jugador y el porcentaje de éxito medio de todos los jugadores 
en el sistema.

El software debe respetar los principales patrones de diseño. 

Los objetivos son:

✅ Task 1 → POST: /players : Crea un jugador

✅ Task 2 → PUT /players : Modifica el nombre del jugador

✅ Task 3 → POST /players/{id}/games/ : Un jugador específico realiza una tirada con los dados.

✅ Task 4 → DELETE /players/{id}/games: Elimina todas las tiradas del jugador.

✅ Task 5 → GET /players/: Devuelve el listado de todos los jugadores del sistema con su porcentaje medio de éxito.
        
✅ Task 6 → GET /players/{id}/games: Devuelve el listado de jugadas de un jugador.
        
✅ Task 7 → GET /players/ranking: Devuelve el ranking medio de todos los jugadores del sistema . Es decir, el porcentaje medio de exito.
        
✅ Task 8 → GET /players/ranking/loser: devuelve al jugador con peor porcentaje de éxito

✅ Task 9 → GET /players/ranking/winner: devuelve al jugador con mejor porcentaje de éxito

Esto hay que realizarlo guardando los datos en MySQL, en mongoDB y guardando jugadores y tiradas en MySQL y mongoDB respectivamente.

Cada uno de los proyectos tiene diferentes requisitos técnicos segun requerimientos  con JWT y test tanto unitarios como de integracion.

## Installation

### Los requisitos para poner en marcha este proyecto son:

  - Tener JavaSE-16  En el caso de no tenerlo instalado podemos instalarlo pulsando el siguiente link: [JavaSE-16](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)

  - Tener instalado el driver de JDBC. En el caso de no tenerlo instalado podemos instalarlo pulsando el siguiente link: [JDBC](https://docs.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver15)

  - Tener instalado MySql En el caso de no tenerlo instalado podemos instalarlo pulsando el siguiente link: [MySql](https://dev.mysql.com/). Y como en cualquier otra instalación siguiente>siguiente… Seleccionamos la configuración por defecto y escribimos un user y una contraseña (opcional).

  -Tener instalado mongoDB En el caso de no tenerlo instalado podemos instalarlo pulsando el siguiente link: [mongoDB](https://docs.mongodb.com/manual/installation/) y siguiendolas instrucciones.
  
  -Tener instalado Eclipse En el caso de no tenerlo instalado podemos instalarlo pulsando el siguiente link:[Eclipse](https://www.eclipse.org/downloads/) y seguir las instrucciones de la página.

 ### Puesta en marcha
  
    Poner en marcha la base de datos MySql .
    
      
         usuario = "root";

         contraseña = "";
         
    O mongoDB segun corresponda:

    Si se quiere usar postman en cada proyecto hay un json para poder importar con las diferentes llamadas.
  
    Poner en marcha el proyecto en eclipse en los proyectos con MySql hay un import que rellenara automaicamente parte de la base de datos.
      
        
  ## Roadmap and visuals
  
     Se han añadido archivos json con las llamadas al api y ejemplos implementados para facilitar las llamadas
     
     
   ![Captura](https://user-images.githubusercontent.com/77856098/156894992-fb9733ae-49a0-48d8-923a-fc2245d793a4.PNG)

     Se han realizado test unitarios y de integracion en el api con MySql
     
  ## Contact info
  
  [Linkedin Isabel Domènech](https://www.linkedin.com/in/isabel-domenech-de-mena-157103124/)
  
  ## License 

  [MIT](https://opensource.org/licenses/MIT)
  

