# 12 вариант
import pandas as pd
import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split

import numpy as np

# читаем данные из файла
data = pd.read_csv('california_housing_train.csv')

# Шаг 1
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

# Шаг 2

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

# Разделение данных на обучающий и тестовый наборы, вместо него можно просто итерироваться по data и
# случайно выбирать в какой набор попадет следующее значение
train_data, test_data = train_test_split(scaled_data, test_size=0.2, random_state=42)


# Шаг 3
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


# Шаг 4
# Выбор признаков для моделей
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