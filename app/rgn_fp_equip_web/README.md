Equipamento - Web
=================

Features
--------

* JPA
* Flyway
* Spring Data
* h2
* WebJars
    * bootstrap
    * angular-ui-bootstrap
    * socksjs
    * stomp


Esboço de Classes
-----------------

```java
public class Equipamento {

    private Cidade cidade;
}

@Entity
public class Cidade{

    private Uf uf;
}

@Entity
public class Uf{
}
```
