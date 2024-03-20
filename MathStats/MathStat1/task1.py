import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm, gamma, expon

# Количество выборок
num_samples = 10000

# Размер каждой выборки
sample_size = 500

# Массивы для хранения выборочных статистик
sample_means = np.zeros(num_samples)
sample_vars = np.zeros(num_samples)
sample_medians = np.zeros(num_samples)

# Генерация выборок и вычисление статистик
for i in range(num_samples):
    sample = np.random.normal(size=sample_size)
    sample_means[i] = np.mean(sample)
    sample_vars[i] = np.var(sample)
    sample_medians[i] = np.median(sample)


# Построение гистограмм
plt.figure(figsize=(15, 5))

# Для добавления красной линии, представляющей ожидаемые значения нормального распределения,
# мы можем использовать функцию norm.pdf из модуля scipy.stats.
# Эта функция возвращает значения плотности вероятности нормального распределения для заданного набора значений.
# Мы можем использовать эти значения для построения линии на гистограммах.
# В этом коде мы создаем массив x значений, равномерно распределенных между минимальным и максимальным
# значениями каждой выборочной статистики.
# Затем мы вычисляем значения плотности вероятности нормального распределения для этих значений с использованием
# среднего и стандартного отклонения каждой выборочной статистики.
# Эти значения используются для построения красной линии на каждой гистограмме.

x = np.linspace(min(sample_means), max(sample_means), 100)
plt.subplot(1, 3, 1)
plt.hist(sample_means, bins=30, density=True)
plt.plot(x, norm.pdf(x, np.mean(sample_means), np.std(sample_means)), 'r-', label='Theoretical Normal')
plt.title('Sample Means')

x = np.linspace(min(sample_vars), max(sample_vars), 100)
plt.subplot(1, 3, 2)
plt.hist(sample_vars, bins=30, density=True)
plt.plot(x, norm.pdf(x, np.mean(sample_vars), np.std(sample_vars)), 'r-', label='Theoretical Normal')
plt.title('Sample Variances')

x = np.linspace(min(sample_medians), max(sample_medians), 100)
plt.subplot(1, 3, 3)
plt.hist(sample_medians, bins=30, density=True)
plt.plot(x, norm.pdf(x, np.mean(sample_medians), np.std(sample_medians)), 'r-', label='Theoretical Normal')
plt.title('Sample Medians')

# 𝑛𝐹(𝑋(2)) → 𝑈1 ∼ Γ(2, 1)
plt.figure()

# Массив для хранения статистик
F_X2 = np.zeros(num_samples)

# Генерация выборок и вычисление статистик
for i in range(num_samples):
    sample = np.random.uniform(size=sample_size)
    sample.sort()
    F_X2[i] = sample_size * sample[1]  # второй порядковый статистик

# Построение гистограммы
plt.hist(F_X2, bins=30, density=True, alpha=0.5, label='Experimental')

# Построение теоретического гамма-распределения
x = np.linspace(min(F_X2), max(F_X2), 100)
plt.plot(x, gamma.pdf(x, 2, scale=1), 'r-', label='Theoretical Gamma(2, 1)')

plt.legend()

# 𝑛(1 − 𝐹(𝑋(𝑛))) → 𝑈2 ∼ Γ(1, 1) = Exp(1)
plt.figure()
# Массив для хранения статистик
F_Xn = np.zeros(num_samples)

# Генерация выборок и вычисление статистик
# В этом случае мы генерируем равномерно распределенные выборки и вычисляем их порядковые статистики.
for i in range(num_samples):
    sample = np.random.uniform(size=sample_size)
    sample.sort()
    F_Xn[i] = (1 - sample[-1])  # n-ый порядковый статистик

# Построение гистограммы
counts, bins, _ = plt.hist(F_Xn, bins=30, alpha=0.5, label='Experimental')

# Построение теоретического экспоненциального распределения
x = np.linspace(min(F_Xn), max(F_Xn), 100)
bin_width = bins[1] - bins[0]  # Размер бина
pdf_values = expon.pdf(x, scale=1/sample_size)  # Значения плотности вероятности
scaled_pdf_values = pdf_values * bin_width * num_samples  # Масштабирование значений плотности вероятности
plt.plot(x, scaled_pdf_values, 'r-', label='Theoretical Exp(1)')
# plt.plot(x, gamma.pdf(x, 1, scale=1), 'g', label='Theoretical Gamma(1, 1)')

plt.legend()
plt.show()

