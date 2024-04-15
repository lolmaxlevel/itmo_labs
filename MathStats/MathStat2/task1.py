import numpy as np
import matplotlib.pyplot as plt
from scipy.special import factorial

# параметры распределения
k = 3
theta = 2

theoretical_mean = k * theta
# функция плотности распределения
def f(x, theta, k):
    return (1 / (factorial(k - 1) * (theta ** k))) * (x ** (k - 1)) * np.exp(-x / theta)


# оценка параметра theta методом моментов
def estimate_theta(sample):
    return np.mean(sample)


# массив объемов выборки
sample_sizes = [10, 50, 100, 500, 1000, 5000, 10000]

# массив для хранения оценок параметра theta
theta_estimates = []

# проведение эксперимента

# заданный порог
threshold = 0.1

# массивы для хранения выборочных характеристик
biases = []
variances = []
mses = []
exceed_threshold_counts = []

# обработка результатов
for size in sample_sizes:
    # генерация выборок
    samples = [np.random.gamma(shape=k, scale=theta, size=size) for _ in range(100)]
    # оценка параметра theta для каждой выборки
    theta_estimates = [estimate_theta(sample) for sample in samples]
    # расчет выборочных характеристик
    bias = np.mean(theta_estimates) - theoretical_mean
    variance = np.mean((theta_estimates - np.mean(theta_estimates)) ** 2)
    mse = bias ** 2 + variance
    # подсчет количества выборок, для которых оценка отличается от реального параметра более чем на заданный порог
    exceed_threshold_count = sum(abs(theta_estimate - theoretical_mean) > threshold for theta_estimate in theta_estimates)
    # сохранение результатов
    biases.append(bias)
    variances.append(variance)
    mses.append(mse)
    exceed_threshold_counts.append(exceed_threshold_count)

# визуализация результатов
plt.figure(figsize=(12, 8))

plt.subplot(2, 2, 1)
plt.plot(sample_sizes, biases, marker='o')
plt.xlabel('Sample size')
plt.ylabel('Bias')

plt.subplot(2, 2, 2)
plt.plot(sample_sizes, variances, marker='o')
plt.xlabel('Sample size')
plt.ylabel('Variance')

plt.subplot(2, 2, 3)
plt.plot(sample_sizes, mses, marker='o')
plt.xlabel('Sample size')
plt.ylabel('Mean Squared Error')

plt.subplot(2, 2, 4)
plt.plot(sample_sizes, exceed_threshold_counts, marker='o')
plt.xlabel('Sample size')
plt.ylabel('Exceed threshold count')

plt.tight_layout()
plt.show()