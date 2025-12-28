# Política de Gestión de Secretos (ISO 27001 A.10.1)

Se prohíbe el almacenamiento de credenciales en texto plano dentro del código fuente. Se utiliza GitHub Secrets como bóveda cifrada y se inyectan como variables de entorno `${VAR}` en tiempo de ejecución, cumpliendo con el estándar de segregación de funciones.
