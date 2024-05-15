import numpy as np
import matplotlib.pyplot as plt

# параметры гамма-распределения
k, l = 1, 1


# заданный порог
threshold = 0.01

sample_sizes = [10, 50, 100, 500, 1000, 5000, 10000]


# Байесовская оценка theta
def estimate_theta_bayesian(sample):
    return np.mean(sample)


# массивы для сохранения результатов
biases = []
variances = []
mses = []
exceed_threshold_counts = []

# обработка результатов
for size in sample_sizes:
    # генерация theta из гамма-распределения
    theta_real = np.random.gamma(shape=k, scale=l)
    # генерация выборок
    samples = [np.random.exponential(scale=theta_real, size=size) for _ in range(1000)]
    # оценка theta для каждой выборки
    theta_estimates = [estimate_theta_bayesian(sample) for sample in samples]
    # расчет выборочных характеристик
    bias = np.mean(theta_estimates) - theta_real
    variance = np.mean((theta_estimates - np.mean(theta_estimates)) ** 2)
    mse = bias ** 2 + variance
    # подсчет количества выборок, для которых оценка отличается от реального параметра более чем на заданный порог
    exceed_threshold_count = sum(
        abs(theta_estimate - theta_real) > threshold for theta_estimate in theta_estimates)
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
