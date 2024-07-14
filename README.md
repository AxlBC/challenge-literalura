# Proyecto "LiterAlura" 

## Descripción
Este es el 3.º desafío propuesto por el equipo de Alura Latam.

Este proyecto consiste en la creación de un catálogo de libros, lo cual es posible gracias al consumo de la API https://gutendex.com y el uso de una base de datos en PostgresSQL para la persistencia de datos.

## Características
- Esta aplicación realiza un consumo de una API y posteriormente maneja los datos recibidos para poder trabajar con ellos.
- Todos los datos se guardan en una base de datos llamada **catalogo_challenge** para permitir la persistencia de datos.
- La base de datos se conforma de dos tablas:
  - Autores
  - Libros
- Permite la busqueda de libros específicos según el título, y si, en el caso de no conocer el título completo, la aplicación muestra al usuario una lista de libros que concuerden con el termino buscado por el usuario.

## Imágenes de uso

### Menú
![Menú](https://github.com/user-attachments/assets/ea646ed6-a760-4c0c-b5dd-badf6a8ec266)
- El menú muestra las diferentes opcines que ofrece la aplicación al usuario

### Buscar libro por título
![librosDisponibles](https://github.com/user-attachments/assets/7b053d48-7a48-4b7e-b376-3d492a5527f8)
- Al seleccionar la opción uno al usuario se le pregunta por el título del libro, y en caso de no ingresar un título completo, ó, si la aplicación encuentra más de un resultado que concuerde con ese mismo título, la aplicación imprimirá un listado con todos los resultados obtenidos para que el usuario pueda seleccionar luego cuál de los resultados mostrados es el libro en específico que se está buscando.

![libroSeleccionado](https://github.com/user-attachments/assets/46b2cadd-70e3-4343-a358-f24f45edc4d9)
- Una vez se ingresa el índice del libro deseado, la aplicación muestra los datos de dicho libro y guarda la información en la base de datos.

### Libros registrados
![opcion2(librosEnLaBaseDeDatos)](https://github.com/user-attachments/assets/40f7d70f-4bad-46ad-8b40-087007c0a896)
- La opción 2 permite observar una lista con todos los libros (y la información de cada uno) registrados en la base de datos.

### Autores registrados
![opcion3(listadoAutores)](https://github.com/user-attachments/assets/badd19d0-cc4b-4975-8d41-778d8e6437f2)
- De la misma forma, la opción 3 permite visualizar una lista de los autores de los libros registrados en la base de datos.

### Autores vivos en determinado año
![opcion4(listadoAutoresVivoEnAño)](https://github.com/user-attachments/assets/30425489-2274-44ae-add0-c2c7086a3806)
- La opción cuatro permite conocer qué autores estaban vivos en un determinado año, la aplicación primero pide al usuario que ingrese el año del que quiera saber y posteriormente muestra qué autores estaba vivos en el año ingresado.

### Lista de libros por idiomas
![opcion5(listadoPorIdioma)](https://github.com/user-attachments/assets/a09b48fb-4fc6-49b3-9b66-a624debee8b0)
- La aplicación también cuenta con la opción de poder mostrar los idiomas de origenes de los libros registrados, preguntando primero al usuario en qué idioma de los disponibles desea organizar los libros y mostrando posteriormente los libros que concuerden con ese idioma de origen.

### Estadísticas de descarga
![opcion6(estadisticaDeDescargas)](https://github.com/user-attachments/assets/8a1fd701-95ca-49c9-8cf6-7b06daf90937)
- También existe la opción de ver las estadísticas de las descargas de los libros, visualizando así el número de descargas totales, el máximo y mínimo número de descargas que ha tenido un libro y el promedio total de descargas.

### Top 10 libros más descargados
![opcion7(top10MasDescargados)](https://github.com/user-attachments/assets/09396a88-28ff-4d87-9b92-57586541596d)
- Por último, también permite mostrar un top 10 de los libros más descargados de los registrados en la base de datos.

## Estado del proyecto
Finalmente, se podría decir que este proyecto se encuentra finalizado, dando así paso a la creación de futuros proyectos en los que aplicar todos los conocimientos adquiridos hasta este punto, y, más conocimientos que se irán adquiriendo en el camino, haciendo así que los proyectos que están por venir tengan un mayor nivel.
