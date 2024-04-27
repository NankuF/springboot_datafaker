# springboot_datafaker
Учебный проект по созданию базового функционала в springboot.<br>
Внимание! в `resources/certs/` лежат ключи `*.pem`. В настоящем проекте они должны быть добавлены в `.gitignore`!<br>


Сущность - обычно подразумевается класс, отражающий таблицу из БД.<br>
DTO - data transfer object. Паттерн, подразумевающий создание классов, инкапсулирующих в себя поля, которые необходимо получить, создать, обновить (create, read, update)<br>
Mapper - класс, задающий правила автоматического преобразования сущности в DTO и обратно. Использует библиотеку mapstruct.<br>

1. Создание 100 юзеров при запуске приложения.<br>
   Необходимо:<br>
   - model: `User` - структура для записи в БД<br>
   - repository: `UserRepository` - содержит CRUD для записи в БД<br>
   - dto: `UserDTO`, `UserCreateDTO` - указывают, какие поля необходимо заполнить для выполнения create, read, update.<br>
   - mapper: `UserMapper` - инкапсулирует код преобразования сущности в DTO и обратно.<br>
   - component: опционально. Класс `DataInitializerProperties` нужен для получения переменных из переменных окружения. `DataInitializerProperties.getCount()` Переменная из application.yml, позволет гибко задавать кол-во юзеров для генерации во время запуска приложения.<br>
   - component: `DataInitializer` - использует faker для создания юзеров.<br>
2. Частичное обновление [PUT вместо PATCH](https://kdrozd-pl.translate.goog/how-to-perform-a-partial-update-patch-with-explicit-null/?_x_tr_sl=auto&_x_tr_tl=ru&_x_tr_hl=ru) - необязательно заполнять все поля для обновления сущности. При передаче поля со значением null - оно будет подставлено. Если же поле не передается, оно будет проигнорировано.<br>
    Необходимо:<br>
    - install dependency: `implementation("org.openapitools:jackson-databind-nullable:0.2.6")`<br>
    - create `UserUpdateDTO` with `JsonNullable` fields.<br>
    - create config `JacksonConfig` and write rules for mapping (`UserMapper`, etc).<br>
    - create `JsonNullableMapper` and added him to `UserMapper`.<br>
    - create simple controllers `UserController` for update `User` model using API.<br>
    - create service `UserService` for encapsulation of business logic by separating it from http requests.<br>
1. Подключен JPA Audit - автоматическая запись времени создания и обновления записи в БД.<br>
    Необходимо:<br>
    - set `@EnableJpaAuditing` in class `Application`.<br>
    - modify `UserDTO`.<br>
    - modify `User` model, added fields `createdAt` and `updatedAt` with `@CreatedDate` and `@LastModifiedDate`.<br>
    - set @EntityListeners(AuditingEntityListener.class) on `User` model.<br>
2. Перехват ошибок с помощью Advice.<br>
    Необходимо:<br>
    - create handler `GlobalExceptionHandler`.<br>
    - create exception `ResourceNotFoundException`.<br>
    - use this exception in controllers or service.<br>
3. Поиск юзера по емайлу.<br>
   Необходимо:<br>
   - add `email` field with `@Email` in `User` model.<br>
   - add `findByEmail` method in `UserRepository`.<br>
   - add `getUserByEmail` method in `UserService`.<br>
   - add email in all user DTO's.<br>
   - add new controller.<br>
4. Фильтрация и пагинация.<br>
By default `?page=1`<br>
Пример: http://localhost:8080/api/users?emailCont=is&&updatedAtGt=2024-03-23 20:18:56<br>
    Необходимо:<br>
    - update `getUsers` in `UserService`.<br>
    - update `getUsers` in `UserController`.<br>
    - add `JpaSpecificationExecutor<User>` in `UserRepository`.<br>
    - implements filter in `UserSpecification`.<br>
    - create `UserParamsDTO` as filter params.<br>
1. Авторизация.<br>
   Необходимо:<br>
   - install dependency.<br>
   - update `User` model, implements `UserDetails`.<br>
   - create `CustomUserDetailsService` as CRUD for users.<br>
   - add `passwordEncoder` bean for hashing password in `EncodersConfig`.<br>
   - create `SecurityConfig` where using `passwordEncoder` and `CustomUserDetailsService`.<br>
   - create `JWTUtils` for generic jwt token.<br>
   - create rsa keys and `RsaKeyProperties`. Dont push `private.pem` to git! This for only example.<br>
    ```bash
    # datafaker/src/main/resources/certs
    openssl genpkey -out private.pem -algorithm RSA -pkeyopt rsa_keygen_bits:2048 &&\
    openssl rsa -in private.pem -pubout -out public.pem
    ```
   - create `AuthRequestDTO`<br>
   - create `AuthenticationController`.<br>
   - add security filter for urls in `SecurityConfig`.<br>
   - create `UserUtils` with `getCurrentUser` method for get authenticated user.<br>
   - add `passwordDigest` in `UserCreateDTO`.<br>
   - add `encryptPassword` method in `UserMapper`.<br>
   - check generate token.<br>
    ```bash
    curl -X POST -H "content-type:application/json" -d '{"username":"admin@admin.ru", "password": "123"}' http://localhost:8080/api/login
    ```
    - check get users with token.<br>
    ```bash
    curl -H "content-type:application/json" -H "authorization:bearer <token>" http://localhost:8080/api/users
    ```


Other commands (before create authentication):<br>
Get all users<br>
```bash
curl http://localhost:8080/api/users
```
Get user by id<br>
```bash
curl http://localhost:8080/api/users/1
```
Create User<br>
```bash
curl -X POST -H "content-type:application/json" -d '{"firstName":"Bob", "lastName":"Marley"}' http://localhost:8080/api/users
```
Partial update user (JsonNullable)<br>
```bash
curl -X PUT -H 'content-type:application/json' -d '{"lastName":"Hello"}' http://localhost:8080/api/users/1
```
Full update user<br>
```bash
curl -X PUT -H 'content-type:application/json' -d '{"firstName":"NewFirstName", "lastName":"NewLastName"}' http://localhost:8080/api/users/1
```