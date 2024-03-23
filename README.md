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