# KtistaServer

## Как подключить базу данных

1. Создай файл .\src\main\resources\application.properties
2. Этот файл должен содержать вот такие данные:

```properties
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.url=jdbc:postgresql://address:port/database-name
spring.datasource.username=******
spring.datasource.password=******
```

3. Настрой PostgreSQL
4. Впиши в этот конфиг адрес сервера, логин и пароль. Эти данные ищи в настройках сервера базы данных
