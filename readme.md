# INSTALACIÓN Y USO DE SWAGGER/OpenAPI

## Introducción

  Swagger es un framework para documentar y describir las API REST y otros servicios web que programemos. De esta forma los desarrolladores que vayan a implementar nuestros APIs/servicios web en sus programas sepan su correcto uso y funcionamiento. Swagger dispone de un interfaz que permite revisar dicha documentación de forma muy visual e interactiva lo que facilita la comprensión para los desarrolladores.

## Instalación

  Para empezar, debemos cerciorarnos de tener las siguientes características en la aplicación para asegurar el correcto funcionamiento de la API. Debemos hacer uso de la especificación de paquetes y secciones dentro de la aplicación para que spring sepa localizar cada componente. Estas anotaciones se colocan por encima de las declaraciones de clases.

  Para implementar Swagger debemos añadir las dependencias adecuadas al fichero de dependencias de nuestro proyecto. En nuestro caso estamos usando Maven por lo que añadiremos las dependencias simplemente al pom.xml.

  ```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>3.0.0</version>
  </dependency>
  ```
  Tras esta edición del pom.xml debemos actualizar el proyecto para que se descarguen y manifiesten realmente las dependencias de Swagger en el proyecto. En el explorador de proyecto hacemos click derecho en el proyecto → Maven → Update Project… → OK.

### Applicaction Properties

  Para que la aplicación logre arrancar sin errores, es necesario añadir una línea al fichero application.properties, el cual cambia la elección de estrategia a usar para hacer coincidir las rutas de solicitud con las asignaciones registradas.

  ```console
  spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER
  ```

### Comprobamos el correcto funcionamiento

  Arrancamos el proyecto y comprobamos el correcto funcionamiento de Swagger. Para ello accedemos a nuestra API por medio de un explorador de internet (en este caso usaremos Firefox). Accedemos a la ruta principal de la API (ej. http://localhost:8080) e introducimos /v2/api-docs. Esta ruta deberá devolver un JSON proporcionado por Swagger. Este fichero no es muy legible ya que su principal uso no es para humanos, si no para software de gestión de documentación API como SwaggerUI.

### SwaggerUI

  Tras comprobar el funcionamiento de Swagger es hora de implementar SwaggerUI. Para ello volvemos al pom.xml y añadiremos la siguiente dependencia y actualizamos el proyecto con Maven como hemos hecho anteriormente.

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.5.2</version>
</dependency>
```
Aunque el proyecto ya tiene la dependencia de SwaggerUI, hará falta implementar unas líneas de código de configuración.

### Configuración Swagger UI

  Dentro de la clase WebSecurityConfig añadiremos las siguientes líneas de código que serán esenciales para hacer funcionar SwaggerUI.

```java
  @Bean
  public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2).select().apis(Predicates.not(RequestHandlerSelectors.basePackage("com.example.mainpackage"))).build();
  }

  @Bean
  public LinkDiscoverers discoverers() {
      List<LinkDiscoverer> plugins = new ArrayList<>();
      plugins.add(new CollectionJsonLinkDiscoverer());
      return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
  }

  public void addResourceHandlers(ResourceHandlerRegistry registry) {      
      registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
  }
```

  Esta configuración nos permitirá ahora acceder a la interfaz de usuario de Swagger que generará la documentación del proyecto entero. Crea documentaciones por defecto para todos los endpoints indicados, aunque nosotros podemos añadir más información como veremos más adelante.

  Para acceder a dicha interfaz introducimos la siguiente URL en un explorador de internet como por ejemplo Firefox que nos dará como resultado una página como la siguiente.

## Documentar nuestra API

A continuación explicamos cómo documentar con Swagger nuestro proyecto API REST usando Eclipse.

### Anotaciones Swagger

  Para documentar nuestro proyecto debemos usar las anotaciones que nos trae Swagger para ello. Dichas anotaciones son las siguientes:

  ```java
  @Api
  ```

  * Esta anotación se escribe antes de la declaración de una clase REST.
  * Indica que dicha clase será documentada por swagger.

  ```java
  @ApiOperation(value="Descripción del método/función", response=Iterable.class, tags="tags que queremos añadir")
  ```
  * Esta anotación se escribe antes de la declaración de un método o una función de una clase REST.
  * Se usa para mostrar el uso que tiene dicho método/función.
  * value se usa para introducir una pequeña descripción.
  * response debe ser Iterable.class para indicar que el método/función puede devolver varios resultados (dichos resultados se indican con otra anotación)
  * tags se usan para agrupar las funciones y de esta forma ordenar

  Asimismo, una manera de documentar exclusivamente un resumen de dicha función, podremos usar la siguiente anotación:
  ```ruby
  @Operation(summary = "Obtener todos los alumnos")
  ```

  ```ruby
  @ApiResponse(code = 200, message = "OK")
  ```
  * Esta anotación se escribe antes de la declaración de un método o una función de una clase REST.
  * Se usa para indicar un resultado que puede dar un método/función
  * code indica el código de mensaje que devuelve
  * message el mensaje específico de este código

  ```ruby
  @ApiResponses -- Contiene una lista de anotaciones de tipo @ApiResponse
  @ApiResponses(value = {
              @ApiResponse(code = 200, message = "OK"),
              @ApiResponse(code = 401, message = "No esta autorizado"),
              @ApiResponse(code = 403, message = "Prohibido"),
              @ApiResponse(code = 404, message = "No encontrado")})
  ```
  * Esta anotación se escribe antes de la declaración de un método o una función de una clase REST.
  * Se usa para crear una lista de posibles resultados que puede dar un método/función e indicar su significado específico.
  * value contendrá dicha lista de anotaciones @ApiResponse

  ```java
  @ApiModelProperty(notes = "Nombre de una persona", name="name", required=true, value="Jon")
  private String name;
  ```

  * Esta anotación se escribe antes de la declaración de una variable dentro de una entidad del modelo. Por ejemplo persona.java
  * Se usa para especificar el valor que debe recibir una variable en una entidad del modelo. Al igual se puede usar también en los DTOs.
  * notes Contiene la descripción de la variable y lo que debe contener.
  * name El nombre de la variable.
  * required Indica si es obligatoria o no.
  * value Pone un ejemplo por defecto de un valor que puede ser insertado.

  Veamos el ejemplo de una entidad y su controlador REST documentado.

  ```java
  @Operation(summary = "Obtener todos los alumnos")
  @ApiResponses(value = {
              @ApiResponse(code = 200, message = "OK"),
              @ApiResponse(code = 401, message = "No esta autorizado"),
              @ApiResponse(code = 403, message = "Prohibido"),
              @ApiResponse(code = 404, message = "No encontrado")})
      @GetMapping("")
      public Collection<Alumno> getAll(@RequestParam(required = false, name = "nombre") String strNombre) {
          return strNombre!=null?alumnoService.findByNombre(strNombre):(Collection<Alumno>) alumnoService.findAll();
      }
  ```

## Conclusión

  Swagger es sin duda una rápida y poderosa herramienta tanto para documentar como para probar nuestras APIs, pudiendo añadir incluso los parámetros necesarios para probar cada endpoint, y veremos el resultado de la llamada en pantalla, funcionando así como una herramienta Postman.

  Concluyentemente podemos decir que SwaggerUI da paso a que cualquier persona, ya sea del equipo de desarrolladores o sus consumidores, visualicen e interactúen con los endpoints de la API sin necesidad de tener conocimientos de implementación del mismo (ya que esta se genera automáticamente a partir de las anotaciones de OpenAPI), colocando así a prueba el consumo del lado del cliente desde una interfaz intuitiva.
