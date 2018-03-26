Las siguientes lineas son un sencillo instructivo para poder ejecutar la aplicacion, la cual esta separada en varios proyectos.

1.- En el proyecto se usan dependencias externas, que deberan ser instaladas manualmente en Maven, las siguientes lineas permiten la instalacion de estas dependencias.
Unicamente es necesario sustituir la palabra {path} por la ruta en donde se haya descargado el proyecto.

mvn install:install-file -Dfile={path}\lib\num-translator-1.0.0.jar -DgroupId=com.lankorlab -DartifactId=num-translator -Dversion=1.0.0 -Dpackaging=jar -DgeneratedPom=true
mvn install:install-file -Dfile={path}\lib\profact-ws-test-3.3.jar -DgroupId=mx.com.profact -DartifactId=profact-ws-test -Dversion=3.3 -Dpackaging=jar -DgeneratedPom=true
mvn install:install-file -Dfile={path}\lib\cfdi-3.3.jar -DgroupId=mx.gob.sat -DartifactId=cfdi -Dversion=3.3 -Dpackaging=jar -DgeneratedPom=true
mvn install:install-file -Dfile={path}\lib\timbre-cfdi-3.3.jar -DgroupId=mx.gob.sat -DartifactId=timbre-cfdi -Dversion=3.3 -Dpackaging=jar -DgeneratedPom=true


2.- Los proyectos que integran la solucion deberan deberan instalarse en el repositorio de Maven, para lo cual se ejecuta el siguiente comando desde la carpeta raiz del proyecto:

mvn install -DskipTests

Con esto instalamos todas las dependencias, ignorando la ejecucion de pruebas unitarias.

3.- El siguiente paso es configurar la base de datos.
3.1.- Crear una base de datos con el nombre ezfact, con encoding ut8 y collation utf8-general
3.2.- Crear un usuario con nombre ezfact y password Ezf4ct-P4$s
3.3.- Asignar todos los privilegios al nuevo usuario sobre la base de datos creada;

4.- Cargar el script ezfactv1.1.sql ubicado en la carpeta database.