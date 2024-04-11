# 2x1 + 3x2 -> max

import numpy as np
import matplotlib.pyplot as plt


# Определение диапазона значений
x = np.linspace(6, 8, 1000)
y = np.linspace(0, 2, 1000)
x, y = np.meshgrid(x, y)

# Ограничения
plt.figure(figsize=(10,10))
feasible_region = ((2*x - 3*y >= 12) & (x + y >= 2) & (3*x + 6*y <= 24) & (x >= 0) & (y >= 0)).astype(int)
plt.imshow(feasible_region, extent=(x.min(), x.max(), y.min(), y.max()), origin="lower", cmap="Greys", alpha=0.3);

# Целевая функция
z = 2*x + 3*y
plt.contour(x, y, z, levels=np.arange(10, 20, 1), cmap="RdGy")

# Добавление стрелки, указывающей направление уровней контура
plt.quiver(6, 0, 3, 4, color='r', angles='xy', scale_units='xy', scale=0.5)

# Оси
plt.grid(visible=True)
plt.xlabel('X1')
plt.ylabel('X2')

plt.show()