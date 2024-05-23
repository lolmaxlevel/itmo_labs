import pandas as pd
import numpy as np
from scipy import stats
from scipy.optimize import minimize

# Загрузка данных
data = pd.read_csv('MEN_SHOES.csv')

# Удаление символов '₹' и ',' из столбцов 'Current_Price' и 'How_Many_Sold'
data['Current_Price'] = data['Current_Price'].str.replace('₹', '').str.replace(',', '')
data['How_Many_Sold'] = data['How_Many_Sold'].str.replace(',', '')

data['Current_Price'].fillna(0, inplace=True)

# Преобразование столбцов 'Current_Price' и 'How_Many_Sold' в числовые значения
data['Current_Price'] = data['Current_Price'].astype(int)
data['How_Many_Sold'] = data['How_Many_Sold'].astype(int)
# Получение среднего значения без учета пропущенных значений
mean = data['Current_Price'].mean()
print(mean)
# или замена пропущенных значений на среднее значение
# data['Current_Price'].fillna(data['Current_Price'].mean(), inplace=True)
#
# # или удаление строк с пропущенными значениями
# data.dropna(subset=['Current_Price'], inplace=True)

# Преобразование столбца 'Current_Price' в числовые значения
data['Current_Price'] = data['Current_Price'].astype(int)
# Преобразование столбцов 'Current_Price' и 'How_Many_Sold' в числовые значения
data['Current_Price'] = data['Current_Price'].astype(int)
data['How_Many_Sold'] = data['How_Many_Sold'].astype(int)
print(data['How_Many_Sold'])
# Построение линейной модели
X = data[['Current_Price', 'How_Many_Sold']].values
y = data['RATING'].values

#
# # Добавление свободного коэффициента к X
# X = np.hstack([np.ones([X.shape[0], 1]), X])
#
#
# # Функция потерь для минимизации (сумма квадратов остатков)
# def loss_function(coef, X, y):
#     return np.sum((y - np.dot(X, coef)) ** 2)
#
#
# # Начальное приближение коэффициентов
# initial_coef = np.zeros(X.shape[1])
#
# # Оптимизация
# res = minimize(loss_function, initial_coef, args=(X, y))
#
# # Оценки коэффициентов
# coef = res.x
#
# # Вычисление остаточной дисперсии
# residuals = y - np.dot(X, coef)
# residual_variance = np.var(residuals, ddof=X.shape[1])
#
# # Построение доверительных интервалов для коэффициентов
# confidence_intervals = []
# for i in range(X.shape[1]):
#     se = np.sqrt(residual_variance / np.sum((X[:, i] - np.mean(X[:, i])) ** 2))
#     ci = coef[i] - 1.96 * se, coef[i] + 1.96 * se
#     confidence_intervals.append(ci)
#
# # Вычисление коэффициента детерминации
# y_hat = np.dot(X, coef)
# ssr = np.sum((y_hat - np.mean(y)) ** 2)
# sst = np.sum((y - np.mean(y)) ** 2)
# r_squared = ssr / sst
#
# # Проверка гипотез
# t_statistic = coef / np.sqrt(residual_variance / np.sum((X - np.mean(X, axis=0)) ** 2, axis=0))
# p_values = 2 * (1 - stats.t.cdf(np.abs(t_statistic), df=X.shape[0] - X.shape[1]))
#
# print(f'Оценки коэффициентов: {coef}')
# print(f'Остаточная дисперсия: {residual_variance}')
# print(f'Доверительные интервалы для коэффициентов: {confidence_intervals}')
# print(f'Коэффициент детерминации: {r_squared}')
# print(f'P-values: {p_values}')
