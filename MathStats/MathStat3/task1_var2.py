import numpy as np
import matplotlib.pyplot as plt
import statistics
from scipy.stats import t


def var(data):
    variance = statistics.variance(data)
    return variance


def confidence_interval(X, Y, alpha):
    n = len(X)
    m = len(Y)

    degree_of_freedom = n + m - 2
    z = t.ppf(1 - alpha / 2, degree_of_freedom)

    # t - непрерывная случайная величина Стьюдента; z - буквально критическое значение z
    mean_diff = np.mean(X) - np.mean(Y)
    std = np.sqrt((np.var(X) / n) + (np.var(Y) / m))
    margin = z * std * np.sqrt(1 / n + 1 / m)
    lower_bound = mean_diff - margin
    upper_bound = mean_diff + margin
    return lower_bound, upper_bound


mu1 = 2
mu2 = 1
tau = mu1 - mu2
sigma_square1 = 1
sigma_square2 = 1
alpha = 0.05
iterations = 1000

cover_25 = 0
cover_10000 = 0

for _ in range(iterations):
    X25 = np.random.normal(mu1, sigma_square1, 25)
    Y25 = np.random.normal(mu2, sigma_square2, 25)
    X10000 = np.random.normal(mu1, sigma_square1, 10000)
    Y10000 = np.random.normal(mu2, sigma_square2, 10000)

    lower_25, upper_25 = confidence_interval(X25, Y25, alpha)
    lower_10000, upper_10000 = confidence_interval(X10000, Y10000, alpha)

    if lower_25 <= tau <= upper_25:
        cover_25 += 1
    if lower_10000 <= tau <= upper_10000:
        cover_10000 += 1

prob_25 = cover_25 / iterations
prob_10000 = cover_10000 / iterations

print("Величина выборки: 25")
print("Доверительный интервал: [", lower_25, ", ", upper_25, "]")
print("Вероятность попадания в доверительный интервал:", prob_25)
print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
print("Величина выборки: 10000")
print("Доверительный интервал: [", lower_10000, ", ", upper_10000, "]")
print("Вероятность попадания в доверительный интервал:", prob_10000)


