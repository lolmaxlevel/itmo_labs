import pandas as pd
from scipy import stats
import matplotlib.pyplot as plt

# Загрузка данных
data = pd.read_csv('song_data.csv')

# Тест Шапиро-Уилка на нормальность
w, p_value = stats.shapiro(data['song_popularity'])
print(f"Shapiro-Wilk Test: W={w}, p-value={p_value}")
if p_value > 0.05:
    print("Рейтинг песен не распределен нормально.")
else:
    print("Рейтинг песен распределен нормально.")
print("\n")
plt.hist(data['song_popularity'], bins=30, edgecolor='black')
plt.title('Histogram of Song Popularity')
plt.xlabel('Popularity')
plt.ylabel('Frequency')
plt.show()

# t-тест Стьюдента для независимых выборок
threshold = 180000  # Задайте свой порог для длины песни
short_songs = data[data['song_duration_ms'] < threshold]['song_popularity']
long_songs = data[data['song_duration_ms'] >= threshold]['song_popularity']
t, p_value = stats.ttest_ind(short_songs, long_songs)
print(f"Student's t-Test: t={t}, p-value={p_value}")
if p_value < 0.05:
    print("Рейтинги коротких и длинных песен не однородны.")
else:
    print("Рейтинги коротких и длинных песен однородны.")

# Разделение песен на две группы по длительности
short_songs = data[data['song_duration_ms'] < 180000]['song_popularity']
long_songs = data[data['song_duration_ms'] >= 180000]['song_popularity']

# Вычисление среднего рейтинга для каждой группы
short_songs_mean = short_songs.mean()
long_songs_mean = long_songs.mean()

print(f"Средний рейтинг песен короче 3 минут: {short_songs_mean}")
print(f"Средний рейтинг песен длиннее 3 минут: {long_songs_mean}")
print("\n")

# Корреляционный анализ
correlation, p_value = stats.pearsonr(data['song_popularity'], data['energy'])
print(f"Pearson Correlation: correlation={correlation}, p-value={p_value}")
if p_value < 0.05:
    print("Популярность зависит от энергичности.")
else:
    print("Популярность не зависит от энергичности.")

plt.scatter(data['energy'], data['song_popularity'])
plt.title('Scatter Plot of Energy vs Song Popularity')
plt.xlabel('Energy')
plt.ylabel('Song Popularity')
plt.show()