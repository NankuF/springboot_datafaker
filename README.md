# springboot_datafaker

Сущность - обычно подразумевается класс, отражающий таблицу из БД.
DTO - data transfer object. Паттерн, подразумевающий создание классов, инкапсулирующих в себя поля, которые необходимо получить, создать, обновить (create, read, update)
Mapper - класс, задающий правила автоматического преобразования сущности в DTO и обратно. Использует библиотеку mapstruct.

1. Создание 100 юзеров при запуске приложения.<br>
   Необходимо:
   - model: `User` - структура для записи в БД
   - repository: `UserRepository` - содержит CRUD для записи в БД
   - dto: `UserDTO`, `UserCreateDTO` - указывают, какие поля необходимо заполнить для выполнения create, read, update.
   - mapper: `UserMapper` - инкапсулирует код преобразования сущности в DTO и обратно.
   - component: опционально. Класс `DataInitializerProperties` нужен для получения переменных из переменных окружения. `DataInitializerProperties.getCount()` Переменная из application.yml, позволет гибко задавать кол-во юзеров для генерации во время запуска приложения.
   - component: `DataInitializer` - использует faker для создания юзеров.
2. Частичное обновление [PUT вместо PATCH](https://kdrozd-pl.translate.goog/how-to-perform-a-partial-update-patch-with-explicit-null/?_x_tr_sl=auto&_x_tr_tl=ru&_x_tr_hl=ru) - необязательно заполнять все поля для обновления сущности. При передаче поля со значением null - оно будет подставлено. Если же поле не передается, оно будет проигнорировано.
<br>
    Необходимо:
    - install dependency: `implementation("org.openapitools:jackson-databind-nullable:0.2.6")`
    - create `UserUpdateDTO` with `JsonNullable` fields.
    - create config `JacksonConfig` and write rules for mapping (`UserMapper`, etc).
    - create `JsonNullableMapper` and added him to `UserMapper`.
    - create simple controllers `UserController` for update `User` model using API.
    - create service `UserService` for encapsulation of business logic by separating it from http requests.
3. Подключен JPA Audit - автоматическая запись времени создания и обновления записи в БД.<br>
    Необходимо:
    - set `@EnableJpaAuditing` in class `Application`.
    - modify `UserDTO`.
    - modify `User` model, added fields `createdAt` and `updatedAt` with `@CreatedDate` and `@LastModifiedDate`.
    - set @EntityListeners(AuditingEntityListener.class) on `User` model.
4. Перехват ошибок с помощью Advice.<br>
    Необходимо:
    - create handler `GlobalExceptionHandler`.
    - create exception `ResourceNotFoundException`.
    - use this exception in controllers or service.
5. Поиск юзера по емайлу.<br>
   Необходимо:
   - add `email` field with `@Email` in `User` model.
   - add `findByEmail` method in `UserRepository`.
   - add `getUserByEmail` method in `UserService`.
   - add email in all user DTO's.
   - add new controller.
6. Фильтрация и пагинация.<br>
By default `?page=1`<br>
Пример: http://localhost:8080/api/users?emailCont=is&&updatedAtGt=2024-03-23 20:18:56<br>
    Необходимо:
    - update `getUsers` in `UserService`.
    - update `getUsers` in `UserController`.
    - add `JpaSpecificationExecutor<User>` in `UserRepository`.
    - implements filter in `UserSpecification`.
    - create `UserParamsDTO` as filter params.
