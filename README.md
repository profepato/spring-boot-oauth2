# Spring boot oauth2 demo

# Database
La base de datos se encuentra en archivo ```schema.sql``` en recursos.


## Permission
- id
- name

Values:
- Create
- Read
- Update
- Delete

## Role
- id
- name

Values:
- Admin
- User

## User
- id
- username
- password (bcrypt VARCHAR(1024))
- enabled
- accountNonExpired
- credentialsNonExpired
- accountNonLocked

## Relation
Permission many to many Role
Role many to many User

# Steps

## pom.xml
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-oauth2</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

## Clase principal
Añadir ```@EnableAuthorizationServer```

## AuthUserDetails.java
Esta clase se encarga de transformar un usuario ```User``` a ```UserDetail```, que es el objeto que reconoce spring.

Es por esto que la clase ```User``` debe tener asignado uno o varios roles ```Role```, y cada rol tiene asignado varios permisos ```Permission```.


## UserRepository.java
Interfaz JPA encargada de rescatar a un usuario ```User``` por su nombre.

## UserDetailsServiceImpl
Servicio que se encarga de cargar un usuario por nombre de usuario (```UserDetails loadUserByUsername(String username)```).

Se obtiene el usuario por username con la ayuda del repositorio. Si no se encuentra, se lanza una excepción ```UserNotfoundException```. Si se encuentra al usuario, se debe transformar de ```User``` a ```UserDetails``` con la ayuda de la clase ```AuthUserDetails```. 

## BeanConfig.java
Esta clase se encarga de dejar ciertos beans a la vista de todas las demás clases. Los beans dejados son:
- PasswordEncoder
- TokenStore

## WebSecurityConfiguration.java
En esta clase se establece el ```PasswordEncoder``` y el ```UserDetailsService```.

Además se deja visible el ```@Bean AuthenticationManager``` para ser utilizado por la clase ```AuthorizationServerConfiguration```

## AuthorizationServerConfiguration.java
Clase utilizada para configurar la seguridad, el ```DataSource```, el ```PasswordEncoder```, el ```TokenStore``` y el ```AuthenticationManager```.


