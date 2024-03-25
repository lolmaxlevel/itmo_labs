import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

sns.set_style("whitegrid")
sns.set_palette("Set2")

# Загрузка данных
df = pd.read_csv('cars93.csv')

# Определение уникальных типов автомобилей
car_types = df['Type'].unique()
print(f"Unique car types: {" ".join(car_types)}")

# Определение наиболее и наименее распространенных типов автомобилей
most_common_type = df['Type'].value_counts().idxmax()
least_common_type = df['Type'].value_counts().idxmin()
print(f"Most common car type: {most_common_type}")
print(f"Least common car type: {least_common_type}")

# Расчет статистик и построение графиков для всей совокупности автомобилей и отдельно для каждого типа автомобиля
for car_type in [None] + list(car_types):
    if car_type is None:
        subset = df
        title = "all cars"
    else:
        subset = df[df['Type'] == car_type]
        title = f"{car_type} cars"

    print(f"\nStats for {title}:")
    print(f"Mean: {subset['Horsepower'].mean()}")
    print(f"Variance: {subset['Horsepower'].var()}")
    print(f"Median: {subset['Horsepower'].median()}")
    print(f"IQR: {subset['Horsepower'].quantile(0.75) - subset['Horsepower'].quantile(0.25)}")

    plt.figure(figsize=(15, 5))
    plt.suptitle(f"Horsepower distribution for {title}")
    plt.subplot(131)
    subset['Horsepower'].plot(kind='hist', cumulative=True, density=True, bins=30)
    plt.title(f"ECDF")
    plt.xlabel("Horsepower")
    plt.ylabel("ECDF")

    plt.subplot(132)
    subset['Horsepower'].hist(bins=30, alpha=0.5, density=True)
    plt.title(f"Histogram")
    plt.xlabel("Horsepower")
    plt.ylabel("Density")

    plt.subplot(133)
    subset['Horsepower'].plot(kind='box')
    plt.title(f"Boxplot ")
    plt.ylabel("Horsepower")

    plt.tight_layout()
    plt.show()
