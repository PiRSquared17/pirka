Pirka is Java XHTML template engine designed for web designer easy using.

Most futures of Pirka are based on Genshi.
Pirka is developed with Java 1.6, NOT depended other libraries.
You can use this template engine with JavaEE application, Google App Engine, JRuby on Rails and so on.
  * Document(Japanese Only): http://docs.pirkaengine.org/
  * Jenkins CI: http://ci.deathmarch.jp/

## Release notes ##
  * 2011/02/27 slim3-ext 0.2.0 release!
    * support for AppEngine 1.4.2 / Slim3 1.0.7 / Scenic3 0.4.0 / Pirka-0.3.0
  * 2011/02/27 0.3.0 release!
    * support charset to render
    * add wiki-engine (beta)
    * add mobile-engine(beta)
  * 2010/12/30 slim3-ext 0.1.0 release!
  * 2010/12/23 0.2.0 release!
    * string in expression
    * support user configured charset
    * fixed minor bugs
  * 2010/11/24 0.1.1 release!
    * add validator
  * 2010/11/21 0.1.0 release!

## Sample ##
### Template file ###
Variables(Default is html escape)
```
<p>Welcome, ${user_name}</p>
```
If you dont escape,
```
<p>$_{contents_body}</p>
```
Simple conditions:
```
<p prk:if="is_login">Logged in</p>
```
Loop:
```
<ul>
    <li prk:for="item in items">${item.name} : ${item.price}</li>
</ul>
```

### Model/Rendering ###
```
public class Item {
   public String name;
   public int price;
   // or you can use getter/setter
}
```
```
Template template = load("sample.html");
Map<String, Object> viewModel = new HashMap<String, Object>();
viewModel.put("user_name", "shuji.w6e");
viewModel.put("items", new ArrayList<Item>());
return template.renderTo(writer, viewModel);
```

## Maven2 ##
Repository:
```
<repository>
  <id>maven.deathmarch.jp</id>
  <name>The Deathmarch Maven2 Repository</name>
  <url>http://maven.deathmarch.jp/maven2</url>
</repository>
```

Dependency:
```
<dependency>
  <groupId>org.pirkaengine</groupId> 
  <artifactId>pirka</artifactId> 
  <version>0.3.0</version> 
</dependency>
<!-- Options -->
<dependency>
  <groupId>org.pirkaengine</groupId> 
  <artifactId>pirka-form</artifactId> 
  <version>0.3.0</version> 
</dependency>
<dependency>
  <groupId>org.pirkaengine</groupId> 
  <artifactId>pirka-wiki</artifactId> 
  <version>0.3.0</version> 
</dependency>
<dependency>
  <groupId>org.pirkaengine</groupId> 
  <artifactId>pirka-mobile</artifactId> 
  <version>0.3.0</version> 
</dependency>
```
