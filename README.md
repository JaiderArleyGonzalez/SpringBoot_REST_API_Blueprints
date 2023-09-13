# Escuela Colombiana de Ingeniería
# Arquitecturas de Software - ARSW
### Taller – Principio de Inversión de dependencias, Contenedores Livianos e Inyección de dependencias.

Parte I. Ejercicio básico.

Para ilustrar el uso del framework Spring, y el ambiente de desarrollo para el uso del mismo a través de Maven (y NetBeans), se hará la configuración de una aplicación de análisis de textos, que hace uso de un verificador gramatical que requiere de un corrector ortográfico. A dicho verificador gramatical se le inyectará, en tiempo de ejecución, el corrector ortográfico que se requiera (por ahora, hay dos disponibles: inglés y español).

1. Abra el los fuentes del proyecto en NetBeans.

2. Revise el archivo de configuración de Spring ya incluido en el proyecto (src/main/resources). El mismo indica que Spring buscará automáticamente los 'Beans' disponibles en el paquete indicado.
	![](img/xml.png)
	`<?xml version="1.0" encoding="UTF-8"?>`: Esta línea especifica la versión del XML y la codificación utilizada. En este caso, se utiliza la versión 1.0 y la codificación UTF-8.

	`<beans xmlns="http://www.springframework.org/schema/beans" ...>`: Este elemento raíz `<beans>` define un contenedor de configuración de Spring. Los atributos `xmlns` y `xmlns:xsi` definen los espacios de nombres XML que se utilizan en el archivo. El atributo `xmlns` se utiliza para el espacio de nombres de beans de Spring, y el atributo `xmlns:xsi` se utiliza para el espacio de nombres del esquema XML.

	`xsi:schemaLocation="http://www.springframework.org/schema/beans ..."`: Este atributo `xsi:schemaLocation` especifica la ubicación de los esquemas XML que se utilizarán para validar el archivo. Define las ubicaciones de los esquemas XML para los espacios de nombres `http://www.springframework.org/schema/beans` y `http://www.springframework.org/schema/context`. Esto es importante para validar que el archivo de configuración cumple con las reglas definidas en los esquemas de Spring.

	`<context:component-scan base-package="edu.eci.arsw" />`: Este es el elemento principal de configuración en este fragmento. Utiliza el espacio de nombres `context` y la etiqueta `component-scan` para configurar el escaneo de componentes en el proyecto. El atributo `base-package` especifica el paquete base (`edu.eci.arsw`) en el que Spring buscará componentes anotados, como clases de servicio, controladores y otros componentes relacionados con la aplicación. Spring escaneará este paquete y sus subpaquetes en busca de clases anotadas con anotaciones como `@Component`, `@Service`, `@Repository`, etc.
3. Haciendo uso de la [configuración de Spring basada en anotaciones](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-spring-beans-and-dependency-injection.html) marque con las anotaciones @Autowired y @Service las dependencias que deben inyectarse, y los 'beans' candidatos a ser inyectadas -respectivamente-:

	* GrammarChecker será un bean, que tiene como dependencia algo de tipo 'SpellChecker'.
		![](img/3p1.png)
	* EnglishSpellChecker y SpanishSpellChecker son los dos posibles candidatos a ser inyectados. Se debe seleccionar uno, u otro, mas NO ambos (habría conflicto de resolución de dependencias). Por ahora haga que se use EnglishSpellChecker.
		![](img/english.png)
		![](img/spanish.png)
		![](img/grammar.png)
5.	Haga un programa de prueba, donde se cree una instancia de GrammarChecker mediante Spring, y se haga uso de la misma:

	```java
	public static void main(String[] args) {
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
		GrammarChecker gc=ac.getBean(GrammarChecker.class);
		System.out.println(gc.check("la la la "));
	}
	```
	<b>prueba:</b>
	![](img/5.png)
6.	Modifique la configuración con anotaciones para que el Bean ‘GrammarChecker‘ ahora haga uso del  la clase SpanishSpellChecker (para que a GrammarChecker se le inyecte SpanishSpellChecker en lugar de EnglishSpellChecker. Verifique el nuevo resultado.
	![](img/6.png)
	![](img/6p.png)

# Componentes y conectores - Parte I.

El ejercicio se debe traer terminado para el siguiente laboratorio (Parte II).

#### Middleware- gestión de planos.


## Antes de hacer este ejercicio, realice [el ejercicio introductorio al manejo de Spring y la configuración basada en anotaciones](https://github.com/ARSW-ECI/Spring_LightweightCont_Annotation-DI_Example).

En este ejercicio se va a construír un modelo de clases para la capa lógica de una aplicación que permita gestionar planos arquitectónicos de una prestigiosa compañia de diseño. 

![](img/ClassDiagram1.png)

1. Configure la aplicación para que funcione bajo un esquema de inyección de dependencias, tal como se muestra en el diagrama anterior.


	Lo anterior requiere:

	* Agregar las dependencias de Spring.
	* Agregar la configuración de Spring.
	* Configurar la aplicación -mediante anotaciones- para que el esquema de persistencia sea inyectado al momento de ser creado el bean 'BlueprintServices'.


2. Complete los operaciones getBluePrint() y getBlueprintsByAuthor(). Implemente todo lo requerido de las capas inferiores (por ahora, el esquema de persistencia disponible 'InMemoryBlueprintPersistence') agregando las pruebas correspondientes en 'InMemoryPersistenceTest'.

3. Haga un programa en el que cree (mediante Spring) una instancia de BlueprintServices, y rectifique la funcionalidad del mismo: registrar planos, consultar planos, registrar planos específicos, etc.

4. Se quiere que las operaciones de consulta de planos realicen un proceso de filtrado, antes de retornar los planos consultados. Dichos filtros lo que buscan es reducir el tamaño de los planos, removiendo datos redundantes o simplemente submuestrando, antes de retornarlos. Ajuste la aplicación (agregando las abstracciones e implementaciones que considere) para que a la clase BlueprintServices se le inyecte uno de dos posibles 'filtros' (o eventuales futuros filtros). No se contempla el uso de más de uno a la vez:
	* (A) Filtrado de redundancias: suprime del plano los puntos consecutivos que sean repetidos.
	* (B) Filtrado de submuestreo: suprime 1 de cada 2 puntos del plano, de manera intercalada.

5. Agrege las pruebas correspondientes a cada uno de estos filtros, y pruebe su funcionamiento en el programa de prueba, comprobando que sólo cambiando la posición de las anotaciones -sin cambiar nada más-, el programa retorne los planos filtrados de la manera (A) o de la manera (B). 
