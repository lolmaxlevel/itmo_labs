import numpy as np
import matplotlib.pyplot as plt

# функция плотности распределения
k = 1
theta = 1
theoretical_mean = k * theta
# заданный порог
threshold = 0.1

sample_sizes = [10, 50, 100, 500, 1000, 5000, 10000]


# Bayesian estimate of theta
def estimate_theta_bayesian(sample):
    alpha_prime = k + np.sum(sample)
    beta_prime = theta + len(sample)
    return alpha_prime / beta_prime


# array for storing Bayesian estimates of theta
theta_estimates_bayesian = []
biases = []
variances = []
mses = []
exceed_threshold_counts = []
# processing results
for size in sample_sizes:
    # generating samples
    samples = [np.random.gamma(shape=k, scale=theta, size=size) for _ in range(1000)]
    # estimating theta for each sample
    theta_estimates_bayesian = [estimate_theta_bayesian(sample) for sample in samples]
    # calculating sample characteristics
    bias = np.mean(theta_estimates_bayesian) - theoretical_mean
    variance = np.mean((theta_estimates_bayesian - np.mean(theta_estimates_bayesian)) ** 2)
    mse = bias ** 2 + variance
    # counting the number of samples for which the estimate differs from the real parameter by more than the specified threshold
    exceed_threshold_count = sum(
        abs(theta_estimate - theoretical_mean) > threshold for theta_estimate in theta_estimates_bayesian)
    # saving results
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
