# Отчет по модулю №1 курса 'Системы искусственного интеллекта'

---

- Студент: `Терновский Илья Евгеньевич`
- Группа: `P3332`
- ИСУ: `334597`
- Выбранная предметная область: `Оружие из контер страйк 2`
- Репозиторий со всеми файлами и примерами и данным отчетом: [github.com](https://github.com/lolmaxlevel/itmo_labs/tree/master/AISystems)
---

## Описание целей проекта и его значимости

Данный проект разделяется на две основные цели:

- Создание базы знаний на языке Prolog и онтологии в Protege на основе выбранной предметной области
- Используя готовую базу знаний на языке Prolog, реализовать CLI-приложение, предоставляющее из себя систему
поддержки принятия решений на основе выбранной предметной области

Значимостью данного проекта заключается во __введении в курс искусственного интеллекта__, а также в __базовом
представлении о логических языках программирования__ (относительно императивных и функциональных языков
программирования).

--- 

## Анализ требований

Требования к системе принятия решений:

1) Система должна предоставить пользователю выбрать сторону, стоимость оружия и предпочитаемый вид оружия.
2) Система должна на основе проведенного с пользователем диалога предоставить ему список подходящего оружия.
3) Система должна уметь работать с разными базами знаний, созданными на логическом языке Prolog

Требования к базе знаний и онтологии:

1) Должны быть представлены названия оружия
2) Должны быть представлены названия типов оружия, сторон конфликта.
3) У каждого оружия должно быть:
   - Тип
   - Цена
   - Сторона за которую есть возможность преобретения.

Примечание: здесь представлены требования по отношению конкретно к самому проекту - основные требования 
можно посмотреть в 
[описании лабораторных работ](https://sunnysubmarines.notion.site/AI-System-a559a46cddc44363bdf27b77e10b7d85). 

--- 

## Изучение основных концепций и инструментов

Сперва стоит разобраться с двумя близкими, но разными понятиями: базой знаний и онтологией.

В информационных источниках много разных мнений насчет того, как отличать базу знаний от онтологии. Мне нравится 
следующая позиция: `Отличие базы знаний и онтологии заключается в исчерпываемости информации о представленной 
предметной области и структурированности данной информации: требования полноте информации к онтологий 
более строгие по сравнению с базой знаний; к тому же онтологии предоставляют структурированную информацию 
(например, в виде классов и соответствующих им свойств), когда базы знаний оперируют лишь множеством фактов и правил;
знаний`

--- 

В качестве одного из инструментов был применен логический язык программирования `Prolog`.
Данный язык оперирует следующими понятиями:

- Факт - в рамках Prolog можно привести аналогию с логическим предикатом (например: факт `use(developer, prolog).`, что 
на математическом языке будет выглядеть `Разработчик использует Prolog`)
- Правило - расширение фактов, которое позволяет нам получать новые знания на основе имеющихся фактов
(например: `hard_skilled_dev(Developer) :- use(Developer, prolog, Ages), Ages > 3.` - Разработчик считается продвинутым 
в случае, если он программирует на Prolog более 3 лет)

К тому же в Prolog константы (они пишутся с малой буквы) и переменные (они, логично, пишутся с большой буквы).

Prolog для поиска решения применяет механизм унификации (если максимально просто говорить - сопоставление с 
базой знаний), на основе которого Prolog подбирает решение и проходится по дереву фактов и правил.

--- 

Основные инструменты для работы с Prolog:

- SWI-Prolog - учебная версия Prolog (содержит консольный интерфейс)
- pyswip - библиотека на Python для работы c Prolog

--- 

## Пример работы системы

```
   
    Добро пожаловать в лучший в мире помощник по выбору оружия в контер страйк 2!
    
    Сейчас мы у вас спросим несколько вопросов, отвечайте честно и в формате который просят, спасибо!
    
    Приятного использования!
    
Введите сторону (`terrorists`, `counter_terrorists`, `ct`, `t` и подобное): 
>t

We will make them cry!
Введите в какой бюджет мы выбираем оружие `eco` если эко раунд, `cheap` если пистолетный раунд или совсем нет денег. Или просто целое число: 
>eco

Введите какое оружие предпочитаете, возможные варианты - rifle, sniper, pistol, machine_gun, any:
>any

Отличный выбор!

Сейчас посмотрим что мы можем предложить...
Рекомендованное оружие:
- Glock-18
- P250
- Tec-9
- Desert Eagle
- Dual Berettas
- CZ75-Auto
- Galil AR
- SSG 08
- Negev
- Nova
- XM1014
- Sawed-Off
- Flashbang
- HE Grenade
- Smoke Grenade
- Molotov
- Decoy Grenade
```

--- 

## Оценка и интерпретация результатов

Примеры запросов непосредственно к базе знаний в Prolog:

```
Найти все пистолеты.
?-weapon_type(X, pistol).
X = "Glock-18"

Найти все снайперские винтовки для КТ.
?- weapon_type(X, sniper), weapon_side(X, counter_terrorists).
X = "SCAR-20" .

Найти альтернативу AK-47 для КТ.
?- alternative_weapon("AK-47", X).
X = "M4A4" .

Найти все оружие для эко раундов у террористов.
?- eco_weapon(X), weapon_side(X, terrorists).
X = "Glock-18" .

% Найти все пары оружия одного типа с разницей в цене более 1000$
W1 = "Galil AR",
Type = rifle,
W2 = "M4A4",
P1 = 1800,
P2 = 3100 .
```
Все примеры можно посмотреть в файле [requests](requests)

Оценка соответствия проекта поставленным требованиям: реализованная система соответствует всем пунктам.

---

## Заключение

Prolog как логический язык программирования предоставляет хорошие возможности реализации систем искусственного проекта, 
а также язык имеет довольно приемлемый порог вхождения (первичная сложность заключается лишь в понимании алгоритма
поиска решения самим Prolog).
В тоже время Protege является чуть более сложным инструментом, но который представляет больше возможностей и имеет большее число различных инструментов и статистик внутри себя.
