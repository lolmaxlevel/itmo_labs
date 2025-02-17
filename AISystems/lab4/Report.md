# Отчет по лабораторной работе 4 курса 'Системы искусственного интеллекта'

---
- Студент: `Терновский Илья Евгеньевич`
- Группа: `P3332`
- ИСУ: `334597`
- Вариант задания: `1` (Вино)
---

## Задание
- Выбор датасета:
    - Четный номер в группе - Датасет [о вине](https://www.kaggle.com/datasets/davorbudimir/winedataset)
    - Нечетный номер в группе - Датасет [про диабет](https://www.kaggle.com/datasets/abdallamahgoub/diabetes/data)
- Проведите предварительную обработку данных, включая обработку отсутствующих значений, кодирование категориальных признаков и масштабирование.
- Получите и визуализируйте (графически) статистику по датасету (включая количество, среднее значение, стандартное отклонение, минимум, максимум и различные квантили), постройте 3d-визуализацию признаков.
- Реализуйте метод k-ближайших соседей ****без использования сторонних библиотек, кроме NumPy и Pandas.
- Постройте две модели k-NN с различными наборами признаков:
    - Модель 1: Признаки случайно отбираются.
    - Модель 2: Фиксированный набор признаков, который выбирается заранее.
- Для каждой модели проведите оценку на тестовом наборе данных при разных значениях k. Выберите несколько различных значений k, например, k=3, k=5, k=10, и т. д. Постройте матрицу ошибок.
---

## Выполнение
### Загрузка и предварительная обработка
```python
# Загрузка и предварительная обработка данных
df = pd.read_csv('WineDataset.csv')
target_name = 'Wine'

# Заполняем пропущенные значения средними значениями по столбцам
df.fillna(df.mean(), inplace=True)
```
В нашем случае предварительная обработка заканчивается на заполнении пропущенных значений средними значениями по столбцам, так как в датасете нет категориальных признаков и все признаки числовые.

### Визуализация статистики по датасету
```python
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
```
Здесь выводятся статистики по датасету, а так же визуализируется 3D график по признакам `Alcohol`, `Color intensity` и `Proline`, где цвета точек соответствуют классам вин.

### Реализация метода k-ближайших соседей
```python

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
```
Есть базовая функция вычисления расстояния между точками, а так же основной класс модели, а отдельный класс для модели со случайными признаками.

### Нормализация данных
```python
target = df[target_name]
df_normalized = df.drop(columns=[target_name])
df_normalized = (df_normalized - df_normalized.min()) / (df_normalized.max() - df_normalized.min())
df_normalized[target_name] = target
```
Так как данные представлены в разном масштабе, а метод k-ближайших соседей чувствителен к масштабу, так как нам важно расстояние между признаками нормализуем данные.

### Запуск моделей и оценка производительности
```python
k_values = [3, 5, 7, 9, 11]
instant_models = [KNNModel(k, df_train, target_name) for k in k_values]
random_models = [RandomKNNModel(k, df_train, target_name) for k in k_values]

# Вывод матриц ошибок для каждой модели
for k, instant, random in zip(k_values, instant_models, random_models):
    print(f"Instant KNN (k={k}):\n", instant.error_matrix(X_test, y_test))
    print(f"Random KNN (k={k}):\n", random.error_matrix(X_test, y_test))
```

Вывод матриц ошибок для каждой модели:
```
Instant KNN (k=3):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            3           32            2
Actual 3            0            0           26
Random KNN (k=3):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            3           32            2
Actual 3            0            0           26
Instant KNN (k=5):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            2           33            2
Actual 3            0            0           26
Random KNN (k=5):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            2           33            2
Actual 3            0            0           26
Instant KNN (k=7):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            1           34            2
Actual 3            0            0           26
Random KNN (k=7):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            1           34            2
Actual 3            0            0           26
Instant KNN (k=9):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            2           33            2
Actual 3            0            0           26
Random KNN (k=9):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            2           33            2
Actual 3            0            0           26
Instant KNN (k=11):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            1           34            2
Actual 3            0            0           26
Random KNN (k=11):
           Predicted 1  Predicted 2  Predicted 3
Actual 1           26            0            0
Actual 2            1           34            2
Actual 3            0            0           26
```
Можно заметить что матрица ошибок для обычной и случайной модели одинакова, что говорит о том, что случайный выбор признаков не влияет на результаты.
Так же можно заметить что ошибок не очень много, что говорит либо о хорошей модели, либо о том что данные хорошо разделимы.
## Заключение
В ходе выполнения лабораторной работы были изучены методы k-ближайших соседей, а так же были реализованы две модели k-NN с различными наборами признаков. Была проведена оценка на тестовом наборе данных при разных значениях k и построена матрица ошибок.



