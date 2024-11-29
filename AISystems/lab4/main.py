import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from mpl_toolkits.mplot3d import Axes3D

# Загрузка и предварительная обработка данных
df = pd.read_csv('WineDataset.csv')
target_name = 'Wine'

# Заполняем пропущенные значения средними значениями по столбцам
df.fillna(df.mean(), inplace=True)

# Отображение статистики по данным
pd.set_option('display.max_columns', None)
print(df.describe(include="all"))

# Визуализация данных
def plot_3d_scatter(df, x_col, y_col, z_col, label_col):
    x, y, z, labels = df[x_col], df[y_col], df[z_col], df[label_col]

    # Цветовая палитра для классов
    palette = sns.color_palette("hsv", len(labels.unique()))
    colors = [palette[label - 1] for label in labels]

    # Построение 3D графика
    fig = plt.figure(figsize=(10, 7))
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(x, y, z, c=colors, s=50, edgecolor='k', alpha=0.7)

    # Настройка осей
    ax.set_xlabel(x_col)
    ax.set_ylabel(y_col)
    ax.set_zlabel(z_col)
    ax.set_title("3D Visualization of Wine Dataset")

    # Легенда
    handles = [plt.Line2D([0], [0], marker='o', color='w', markerfacecolor=palette[i],
                          markersize=10, label=f'Wine {i+1}') for i in range(len(labels.unique()))]
    ax.legend(handles=handles, title="Wine Classes", loc="upper left", bbox_to_anchor=(1.05, 1))
    plt.show()

plot_3d_scatter(df, 'Alcohol', 'Color intensity', 'Proline', 'Wine')

# Функция для вычисления расстояния
def distance(a: pd.Series, b: pd.Series) -> float:
    return np.linalg.norm(a - b)

# Базовая модель k-ближайших соседей
class KNNModel:
    def __init__(self, k: int, df: pd.DataFrame, target: str):
        self.k = k
        self.df = df
        self.target = target

    def predict(self, x: pd.Series) -> int:
        distances = self.df.drop(columns=[self.target]).apply(lambda row: distance(row, x), axis=1)
        nearest_neighbors = distances.nsmallest(self.k).index
        return self.df.loc[nearest_neighbors, self.target].mode()[0]

    def error_matrix(self, X_test: pd.DataFrame, y_test: pd.Series) -> pd.DataFrame:
        unique_classes = y_test.unique()
        m_err = pd.DataFrame(0,
                             index=[f"Actual {int(y)}" for y in unique_classes],
                             columns=[f"Predicted {int(y)}" for y in unique_classes])

        for x, y in zip(X_test.iterrows(), y_test):
            actual_label = f"Actual {int(y)}"
            predicted_label = f"Predicted {int(self.predict(x[1]))}"
            m_err.at[actual_label, predicted_label] += 1

        return m_err

# Случайный kNN с выбором случайных признаков
class RandomKNNModel(KNNModel):
    def predict(self, x: pd.Series) -> int:
        random_features = np.random.choice(self.df.columns.difference([self.target]), size=len(self.df.columns)-1, replace=False)
        distances = self.df[random_features].apply(lambda row: distance(row, x[random_features]), axis=1)
        nearest_neighbors = distances.nsmallest(self.k).index
        return self.df.loc[nearest_neighbors, self.target].mode()[0]

# Нормализация данных
target = df[target_name]
df_normalized = df.drop(columns=[target_name])
df_normalized = (df_normalized - df_normalized.min()) / (df_normalized.max() - df_normalized.min())
df_normalized[target_name] = target

# Разделение на тренировочную и тестовую выборки
df_train = df_normalized.sample(frac=0.5, random_state=42)
X_train, y_train = df_train.drop(columns=[target_name]), df_train[target_name]
X_test = df_normalized.drop(df_train.index).drop(columns=[target_name])
y_test = df_normalized.drop(df_train.index)[target_name]

# Инициализация и тестирование моделей
k_values = [3, 5, 7, 9, 11]
instant_models = [KNNModel(k, df_train, target_name) for k in k_values]
random_models = [RandomKNNModel(k, df_train, target_name) for k in k_values]

# Вывод матриц ошибок для каждой модели
for k, instant, random in zip(k_values, instant_models, random_models):
    print(f"Instant KNN (k={k}):\n", instant.error_matrix(X_test, y_test))
    print(f"Random KNN (k={k}):\n", random.error_matrix(X_test, y_test))
