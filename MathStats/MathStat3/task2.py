import numpy as np

theta = 5
sample_sizes = [25, 10000]
num_experiments = 1000
alpha = 0.05
cover_counts = {25: 0, 10000: 0}

for n in sample_sizes:
    for _ in range(num_experiments):
        sample = np.random.uniform(low=-theta, high=theta, size=n)

        sample_min = np.min(sample)
        sample_max = np.max(sample)

        a = sample_max/((1-alpha/2)**(1/n))
        b = sample_max/((1-alpha/2)**(1/n))
        if a <= theta <= b:
            cover_counts[n] += 1


for n in sample_sizes:
    coverage = cover_counts[n] / num_experiments
    print(f"Попадания для выборки {n}: {coverage}")