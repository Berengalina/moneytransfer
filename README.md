[![Build status](https://ci.appveyor.com/api/projects/status/sol2wpx5h8sg4jka/branch/master?svg=true)](https://ci.appveyor.com/project/Berengalina/moneytransfer/branch/master)

## Домашнее задание к занятию «Behaviour Driven Development»

### Задача №1 - Page Object's


1. Очень спорный метод getCardInfo. Зачем массив? Как Вы будете различать валидные и невалидные карты, по индексу? Это удобно? Ну и кроме этого в практике написания тестов очень не приветствуется примение условных операторров и циклов, это достаточно сложно описывать в кейсах. Лучше реализовать несколько атомарных методов и назвать их понятными именами.        
***Исправлено, сделала 3 разных метода для получения каждой карты***

2. Класс DashboardPage необходимо разделить на два класса: первый для описания страницы "Ваши карты" и "Пополнение карты".          
***Исправлено, добавлен класс TransferPage***

3. Сумму перевода я бы задавал на уровне теста в поле метода, в разных тестах ведь сумма перевода может быть разная. Это поле необходимо будет использовать в расчётах.           
***Исправлено***

4. В метод validPayCard я бы передавал номер карты строкой, а не числом.
***Метод переделан с учетом п.1***

5. Не совсем понял кейс реализуемый в тесте shouldTransferMoneyBetweenOwnCardsV3. Вы считаете нормальным возможность перевода с карты на эту же карту?      
***Переписан тест, исправлен ожидаемый результат***

6. Комментировать тест, направленный на поиск бага точно не стоит. Он будет падать до тех пор, пока баг не исправят разработчики, это нормально.
***Падающие тесты раскомментированы***

7. Эту проверку и эту проверку необходимо выполнять в методах page объекта.
***Проверки data-test-id=error-notification вынесены в класс TransferPage***