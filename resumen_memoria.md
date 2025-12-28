# Resumen del trabajo realizado

En estos días nos hemos centrado en poner a punto el proyecto, arreglando el entorno de desarrollo y mejorando la calidad del código con nuevas pruebas.

Lo primero fue pelearse con las versiones de Java. Teníamos un lío con Java 11 y 17, así que pasamos todo a **JDK 21** y configuramos bien las rutas para que Maven encontrara el compilador sin problemas. Con el entorno ya estable, configuramos **JaCoCo** para poder ver qué partes del código estaban sin probar y cuáles no.

En el **frontend (Angular)**, arreglamos varios tests que fallaban por errores de dependencias e inyección en los servicios y el interceptor de seguridad. Ahora las pruebas de la parte visual vuelven a pasar.

En el **backend**, le hemos dado un buen empujón a la cobertura. Hemos creado tests unitarios nuevos para los servicios y controladores más importantes (como el de usuarios, clientes y consentimiento). Gracias a esto, hemos pasado de tener casi un 0% a alcanzar el **100% de cobertura** en las piezas clave de ambos servidores.

En resumen: ahora el proyecto no solo compila y funciona bien, sino que es mucho más robusto y fácil de mantener gracias a que las partes críticas están bien testeadas.
