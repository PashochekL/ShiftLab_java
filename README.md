# ShiftLab_java

## Описание проекта

ShiftCRMSystem - это система управления продажами, разработанная на Java с использованием Spring Boot. 
Проект включает функциональность для работы с продавцами (Seller) и транзакциями (Transaction), 
а также предоставляет RESTful API для взаимодействия с этими сущностями.

## Функциональность

- **Seller**: Управление информацией о продавцах, включая создание, обновление и получение данных о продавцах.
- **Transaction**: Управление транзакциями, связанными с продавцами, включая создание и получение информации о транзакциях.
- **API**: RESTful API для взаимодействия с сущностями `Seller` и `Transaction`.
  
## Инструкции по сборке и запуску

### Для начала работы необходимо:

1) Скачайте файлы, которые лежат на ветке master (либо скачайте zip архив).
   
2) Загрузите их в IntelliJ IDEA.
   
3) Запустите проект

## База данных H2

Для использования базы данных H2 в файловом виде создайте файл /data/ShiftDB в папке проекта.
Это позволит системе использовать файл базы данных на диске.
Для тестирования использована встроенная база данных H2 в памяти.

Вы можете получить доступ к базе данных H2 по следующему адресу: http://localhost:8080/h2-console
Для доступа к консоли H2 используйте следующие данные:

jdbc:h2:mem:ShiftDB

Стандартный порт: 8080

Логин: sa

Пароль:(отсутствует)

## Тестирование:
Тесты запускаются через консоль командой:

./gradlew test

Для генерации отчета нужно использовать команду:

./gradlew jacocoTestReport

Отчет будет находиться в папке: 

build/reports/tests/test/index.html

## Swagger:

После запуска проекта, вы можете увидеть визуальное представление моего REST API

Откройте его по ссылке: http://localhost:8080/swagger-ui/index.html

### POST-запросы:

1. Добавить продавца: http://localhost:8080/swagger-ui/index.html#/Sellers/addSeller

2. Добавить транзакцию: http://localhost:8080/swagger-ui/index.html#/Transactions/addTransaction

3. Получить самого продуктивного продавца: http://localhost:8080/swagger-ui/index.html#/Sellers/getMostProductiveSeller

4. Получить продавцов с мин. продажей: http://localhost:8080/swagger-ui/index.html#/Sellers/getSellersWithMinAmount

### GET-запросы:

1. Получить продавца: http://localhost:8080/swagger-ui/index.html#/Sellers/getSeller

2. Получить всех продавцов: http://localhost:8080/swagger-ui/index.html#/Sellers/getAllSeller

3. Получить транзакцию: http://localhost:8080/swagger-ui/index.html#/Transactions/getTransaction

4. Получить все транзакции конкретного продавца: http://localhost:8080/swagger-ui/index.html#/Transactions/getAllSellerTransactions

5. Получить все транзакции: http://localhost:8080/swagger-ui/index.html#/Transactions/getAllTransaction

### PUT-запросы:

1. Обновить информацию о продавце: http://localhost:8080/swagger-ui/index.html#/Sellers/updateSeller

### DELETE-запросы:

1. Удалить продавца: http://localhost:8080/swagger-ui/index.html#/Sellers/deleteSeller
