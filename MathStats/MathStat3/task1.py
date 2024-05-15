import numpy as np
from scipy.stats import f


def confidence_interval(n, m, alpha=0.05):
    X = np.random.normal(0, np.sqrt(2), n)
    Y = np.random.normal(0, 1, m)

    var_X = np.var(X, ddof=1)
    var_Y = np.var(Y, ddof=1)
    stats = f.interval(1 - alpha / 2, m - 1, n - 1)
    lower = f.ppf(alpha / 2, m - 1, n - 1)
    upper = f.ppf(1 - alpha / 2, m - 1, n - 1)
    # lower = stats[0]
    # upper = stats[1]
    # print(var_X / var_Y)
    lower_bound = lower * (var_X / var_Y)
    upper_bound = upper * (var_X / var_Y)
    return lower_bound, upper_bound


def experiment(n, m, iterations=1000):
    tau = 2 / 1  # истинное значение отношения дисперсий
    count = 0
    for _ in range(iterations):
        lower_bound, upper_bound = confidence_interval(n, m)
        # print("Lower bound: ", lower_bound, "Upper bound: ", upper_bound)
        if lower_bound <= tau <= upper_bound:
            count += 1
    return count / iterations


print("For sample size 25: ", experiment(25, 25))
print("For sample size 10000: ", experiment(10000, 10000))
