# messenger

---

#### Описание:
Мотивационное приложение для отслеживания своих задач и активностей друзей.

---

#### Функционал:
+ Регистрация пользователя
+ Вход
+ Добавление друзей
+ Создание задач
+ Отметка задач выполненными
+ Просмотр всех своих задач и публичных задач других пользователей
+ Создание общих задач для нескольких пользователей
+ Чат с другими пользователями

---

#### Пока что можно:
+ Зарегистрироваться (/api/v1/user/sign-up)
+ Создать задачу (/api/v1/task/create)

---

###### TODO
+ Добавить полей для регистрации
+ Добавить валидацию (какую-то нормальную, еще хз как сделать)
+ Steps в Task
+ Обработать случай, если пустой пользователь регистрируется (пока что там 400, но описание ошибки ...)
+ Сделать новую сущность, которую возвращать пользователю вместо User (без id!)