Как настроить источник данных на сервере WildFly
При запущенном сервере WildFly открываем новую консоль cmd и запускаем:

строка для создания модуля подключения JDBC на сервере WildFly
jboss-cli.bat --connect --file="c:\Farida\geekbrains\Study java\Quarter IV\EE\Homework\jsf\mysql-module.cli"

строка для создания источника данных на сервере WildFly
jboss-cli.bat --connect --file="c:\Farida\geekbrains\Study java\Quarter IV\EE\Homework\jsf\datasource.cli"