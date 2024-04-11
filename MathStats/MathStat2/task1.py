import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import gamma


# Определение функции плотности вероятности
def pdf(x, theta, k):
    return (1 / (k - 1) * theta ** k) * x ** (k - 1) * np.exp(-x / theta)


# Оценка параметра theta методом моментов
def estimate_theta(sample, k):
    return np.mean(sample) / k


# Задание массива объемов выборки
sample_sizes = np.arange(10, 1000, 10)

# Истинное значение theta
true_theta = 2
k = 3

# Массивы для хранения результатов
means = []
variances = []

for n in sample_sizes:
    # Генерация выборок и оценка theta
    estimates = [estimate_theta(gamma.rvs(a=k, scale=true_theta, size=n), k) for _ in range(1000)]

    # Вычисление выборочных характеристик
    mean = np.mean(estimates)
    variance = np.var(estimates)

    means.append(mean)
    variances.append(variance)

# Визуализация результатов
plt.figure(figsize=(12, 6))

plt.subplot(1, 2, 1)
plt.plot(sample_sizes, means, label='Mean of estimates')
plt.axhline(y=true_theta, color='r', linestyle='-', label='True theta')
plt.xlabel('Sample size')
plt.ylabel('Mean of theta estimates')
plt.legend()

plt.subplot(1, 2, 2)
plt.plot(sample_sizes, variances, label='Variance of estimates')
plt.xlabel('Sample size')
plt.ylabel('Variance of theta estimates')
plt.legend()

plt.tight_layout()
plt.show()