## Job4j_DreamJob  

[![Build Status](https://travis-ci.org/amasterenko/job4j_dreamjob.svg?branch=master)](https://travis-ci.org/amasterenko/job4j_dreamjob)
[![codecov](https://codecov.io/gh/amasterenko/job4j_dreamjob/branch/master/graph/badge.svg?token=0AYJT25D48)](https://codecov.io/gh/amasterenko/job4j_dreamjob)

В системе два типа пользователей: _кандидаты_ и _кадровики_.  
Кандидаты публикуют резюме. Кадровики публикуют _вакансии_ о работе.  
Кандидаты могут откликнуться на вакансию.   
Кадровик может пригласить на вакансию кандидата. 

#### Технологии  
* Java Core
* JDBC(PostgreSQL)
* Java Servlets/JSTL
* HTML(Bootstrap)
* JSON Web Tokens (Java-JWT)
* JUnit
* Mockito
* JaCoCo
* Travis CI
* Codecov

#### Интерфейс приложения 

![ScreenShot](images/registration.png)  
![ScreenShot](images/login.png)  
![ScreenShot](images/vacancies.png)  
![ScreenShot](images/candidates.png)  
![ScreenShot](images/add_vacancy.png)  
![ScreenShot](images/add_candidate.png)  
![ScreenShot](images/add_photo.png)  

#### Установка приложения  
1. Создать базу данных _dreamjob_.  
2. Выполнить на созданной БД скрипт _db/scheme.sql_.  
3. Настроить соединение с БД в файле _db.properties_.  
4. Перейти в директорию проекта и выполнить ```mvn clean install```.  
5. Скопировать war-файл в директорию _webapps_ сервера Tomcat.    