# Отчет по лабораторной работе 3 курса 'Системы искусственного интеллекта'

---

- Студент: `Терновский Илья Евгеньевич`
- Группа: `P3332`
- ИСУ: `334597`
- Вариант задания: `1` (дома в Калифорнии)
---

## Задание
- Выбор датасетов:
    - Студенты с **четным** порядковым номером в группе должны использовать набор данных о [жилье в Калифорнии](https://developers.google.com/machine-learning/crash-course/california-housing-data-description?hl=ru) Скачать [тут](https://download.mlcc.google.com/mledu-datasets/california_housing_train.csv)
    - Студенты с **нечетным** порядковым номером в группе должны использовать [про обучение студентов](https://www.kaggle.com/datasets/nikhil7280/student-performance-multiple-linear-regression)
- Получите и визуализируйте (графически) статистику по датасету (включая количество, среднее значение, стандартное отклонение, минимум, максимум и различные квантили).
- Проведите предварительную обработку данных, включая обработку отсутствующих значений, кодирование категориальных признаков и нормировка.
- Разделите данные на обучающий и тестовый наборы данных.
- Реализуйте линейную регрессию с использованием метода наименьших квадратов без использования сторонних библиотек, кроме NumPy и Pandas (для использования коэффициентов использовать библиотеки тоже нельзя). Использовать минимизацию суммы квадратов разностей между фактическими и предсказанными значениями для нахождения оптимальных коэффициентов.
- Постройте **три модели** с различными наборами признаков.
- Для каждой модели проведите оценку производительности, используя метрику коэффициент детерминации, чтобы измерить, насколько хорошо модель соответствует данным.
- Сравните результаты трех моделей и сделайте выводы о том, какие признаки работают лучше всего для каждой модели.
- Бонусное задание
    - Ввести синтетический признак при построении модели
---

## Выполнение
### Визуализация статистики по датасету
```python
# Указываем максимальное количество столбцов для отображения в консоли
pd.set_option('display.max_columns', None)
# Визуализация статистики, заменяет data.mean(), data.std() и тд
statistics = data.describe(include="all")
print(statistics)

# Построение графиков
fig, axes = plt.subplots(nrows=3, ncols=3, figsize=(15, 15))
axes = axes.flatten()

for i, col in enumerate(data.columns):
    # Построение гистограммы для каждого столбца
    data[col].hist(ax=axes[i], bins=30)
    axes[i].set_title(col)

plt.tight_layout()
plt.show() 
```

### Предварительная обработка данных
```python
# Обработка отсутствующих значений, удаление строк с пропущенными значениями
for row in data.iterrows():
    if row[1].isnull().sum() > 0:
        data = data.drop(row[0])

# Нормировка данных
scaled_data = data.copy()
for col in scaled_data.columns:
    mean = scaled_data[col].mean()
    std = scaled_data[col].std()
    scaled_data[col] = (scaled_data[col] - mean) / std
```
Специально для данного датасета не требуется кодирование категориальных признаков, так как все признаки являются числовыми.
Так же нормировку и обработку отсутствующих значений возможно проводить специальными функциями, но мне было интересней сделать это самому.

### Разделение данных на обучающий и тестовый наборы данных
```python
train_data, test_data = train_test_split(scaled_data, test_size=0.2, random_state=42)
```
Здесь все просто, мы разделяем данные на обучающую и тестовую выборки в соотношении 80/20, при этом используем сид для воспроизводимости результатов.

### Реализация линейной регрессии
```python
class LinearRegression:
    def __init__(self):
        self.coefficients = None

    def fit(self, X, y):
        X_b = np.c_[np.ones((X.shape[0], 1)), X]  # добавление столбца единиц для свободного члена, X_b = [1, X]
        # вычисление коэффициентов регрессии по формуле (X^T * X)^-1 * X^T * y (где X^T - транспонированная матрица X)
        self.coefficients = np.linalg.inv(X_b.T.dot(X_b)).dot(X_b.T).dot(y)

    def predict(self, X):
        X_b = np.c_[np.ones((X.shape[0], 1)), X]  # добавление столбца единиц для свободного члена
        # предсказание значений по формуле X * W, где W - вектор коэффициентов регрессии
        return X_b.dot(self.coefficients)

    def score(self, X, y):
        y_pred = self.predict(X)
        ss_total = np.sum((y - np.mean(y)) ** 2)
        ss_residual = np.sum((y - y_pred) ** 2)
        return 1 - (ss_residual / ss_total)
```
Здесь все просто, мы создаем класс `LinearRegression`, в котором реализуем методы `fit`, `predict` и `score`, 
по большей части математика здесь из википедии, функции из библиотек использованы просто для читаемости 
и уменьшения количества кода.

### Построение трех моделей с различными наборами признаков и оценка производительности
```python
features_1 = ["median_income"]
features_2 = ["median_income", "total_rooms"]
features_3 = ["median_income", "total_rooms", "housing_median_age"]

# Обучение и оценка моделей
models = [features_1, features_2, features_3]
results = []

for features in models:
    X_train = train_data[features].values
    y_train = train_data["median_house_value"].values
    X_test = test_data[features].values
    y_test = test_data["median_house_value"].values

    model = LinearRegression()
    model.fit(X_train, y_train)
    score = model.score(X_test, y_test)
    results.append((features, score))

# Вывод результатов
for features, score in results:
    print(f"Features: {features}, R^2 Score: {score}")
```
Здесь мы создаем три модели с различными наборами признаков и оцениваем их производительность с помощью метрики коэффициент детерминации.

Первый набор самый простой, который сам по себе должен неплохо работать, так как `median_income` является одним из самых важных признаков.

Второй набор добавляет признак `total_rooms`, который тоже должен быть важным, так как количество комнат в доме влияет на его стоимость.

Третий набор добавляет признак `housing_median_age`, который тоже должен влиять на стоимость дома, так как новые дома стоят дороже.

### Вывод результатов
```
Features: ['median_income'], R^2 Score: 0.4977788854008406
Features: ['median_income', 'total_rooms'], R^2 Score: 0.4977860930489184
Features: ['median_income', 'total_rooms', 'housing_median_age'], R^2 Score: 0.5422510309140072
```
По результатам трех моделей можно сделать вывод о том, что мои предположения были частично верны, так как признак `median_income` сам по себе неплохо работает (так как 0.497 очень хороший результат), но добавление признаков `total_rooms` и `housing_median_age` улучшает результаты, в особенности при добавлении `housing_median_age`.

### Бонусное задание
```python
# Введение синтетического признака
data["rooms_per_household"] = data["total_rooms"] / data["households"]

# Нормировка данных с новым признаком
scaled_data = data.copy()
for col in scaled_data.columns:
    mean = scaled_data[col].mean()
    std = scaled_data[col].std()
    scaled_data[col] = (scaled_data[col] - mean) / std

# Разделение данных на обучающий и тестовый наборы
train_data, test_data = train_test_split(scaled_data, test_size=0.2, random_state=42)

# Обучение и оценка модели с новым признаком
features_4 = ["median_income", "total_rooms", "housing_median_age", "rooms_per_household"]
X_train = train_data[features_4].values
y_train = train_data["median_house_value"].values
X_test = test_data[features_4].values
y_test = test_data["median_house_value"].values

model = LinearRegression()
model.fit(X_train, y_train)
score = model.score(X_test, y_test)

print(f"Features: {features_4}, R^2 Score: {score}")
```
Так как для данного датасета достаточно сложно придумать какой либо синтетический признак, я решил добавить признак `rooms_per_household`, который показывает количество комнат на дом, так как это тоже влияет на стоимость дома.
## Заключение
В ходе выполнения данной лабораторной работы я научился работать с линейной регрессией, посмотрел на то, какие признаки влияют на стоимость дома в Калифорнии, а так же научился вводить синтетические признаки для улучшения модели.


