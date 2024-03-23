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
2. Частичное обновление - необязательно заполнять все поля для обновления сущности. При передаче поля со значением null - оно будет подставлено. Если же поле не передается, оно будет проигнорировано.<br>
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