import pandas as pd
import numpy as np
from math import sqrt
from scipy.special import betainc

# Читаем данные
data = pd.read_csv('sex_bmi_smokers.csv')

# Группируем данные по фактору "курит/не курит"
grouped = data.groupby('smoker')

# Получаем данные для групп "курит" и "не курит"
smokers_bmi = grouped.get_group('yes')['bmi']
non_smokers_bmi = grouped.get_group('no')['bmi']

# Вычисляем средние значения и дисперсии для каждой группы
mean_smokers = np.mean(smokers_bmi)
mean_non_smokers = np.mean(non_smokers_bmi)

var_smokers = np.var(smokers_bmi, ddof=1)
var_non_smokers = np.var(non_smokers_bmi, ddof=1)

# Вычисляем общее среднее значение
mean_total = np.mean(data['bmi'])

# Вычисляем межгрупповую и внутригрупповую суммы квадратов
ssb = len(smokers_bmi) * (mean_smokers - mean_total)**2 + len(non_smokers_bmi) * (mean_non_smokers - mean_total)**2
ssw = (len(smokers_bmi) - 1) * var_smokers + (len(non_smokers_bmi) - 1) * var_non_smokers

# Вычисляем межгрупповую и внутригрупповую дисперсии
dfb = 2 - 1
dfw = len(data['bmi']) - 2
msb = ssb / dfb
msw = ssw / dfw

# Вычисляем значение F-статистики
f_val = msb / msw

# Вычисляем p-значение
p_val = betainc(0.5*dfw, 0.5*dfb, dfw / (dfw + dfb * f_val))

print(f'F-value: {f_val}')
print(f'P-value: {p_val}')