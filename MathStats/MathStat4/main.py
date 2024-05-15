import pandas as pd
from scipy import stats

# Загрузка данных
data = pd.read_csv('song_data.csv')

# Тест Шапиро-Уилка на нормальность
w, p_value = stats.shapiro(data['song_popularity'])
print(f"Shapiro-Wilk Test: W={w}, p-value={p_value}")

# t-тест Стьюдента для независимых выборок
threshold = 200000  # Задайте свой порог для длины песни
short_songs = data[data['song_duration_ms'] < threshold]['song_popularity']
long_songs = data[data['song_duration_ms'] >= threshold]['song_popularity']
t, p_value = stats.ttest_ind(short_songs, long_songs)
print(f"Student's t-Test: t={t}, p-value={p_value}")

# Корреляционный анализ
correlation, p_value = stats.pearsonr(data['song_popularity'], data['energy'])
print(f"Pearson Correlation: correlation={correlation}, p-value={p_value}")