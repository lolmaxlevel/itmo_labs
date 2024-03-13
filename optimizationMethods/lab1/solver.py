# x^4 + x^2 + x + 1, [a,b] = [-1,0], eps=0.003
from time import sleep


def f(x):
    return x ** 4 + x ** 2 + x + 1


# Производная функции f = 4x^3 + 2x + 1
def df(x):
    return 4 * x ** 3 + 2 * x + 1


# Вторая производная функции f = 12x^2 + 2
def d2f(x):
    return 12 * x ** 2 + 2


# Метод половинного деления (метод дихотомии)
def half_divide(func, a, b, eps):
    while (b - a) > (2 * eps):
        x1 = (a + b - eps) / 2
        x2 = (a + b + eps) / 2
        y1 = func(x1)
        y2 = func(x2)
        if y1 > y2:
            a = x1
        else:
            b = x2
    else:
        xm = (a + b) / 2
    return xm


# Метод золотого сечения
def golden_section(func, a, b, eps):
    x1 = a + 0.382 * (b - a)
    x2 = a + 0.618 * (b - a)
    while abs(b - a) > eps:
        if func(x1) < func(x2):
            b = x2
            x2 = x1
            x1 = a + 0.382 * (b - a)
        else:
            a = x1
            x1 = x2
            x2 = a + 0.618 * (b - a)
    return (a + b) / 2


# Метод Хорд
def chord(func, a, b, eps):
    x = a - func(a) * (b - a) / (func(b) - func(a))
    while abs(func(x)) > eps:
        if func(a) * func(x) < 0:
            b = x
        else:
            a = x
        x = a - func(a) * (b - a) / (func(b) - func(a))
    return x


# Метод Ньютона
def newton_method(func, dfunc, x0, eps):
    x = x0
    while True:
        x_new = x - func(x) / dfunc(x)
        if abs(func(x_new)) <= eps:
            break
        x = x_new
    return x_new


hd = half_divide(f, -1, 0, 0.003)
gs = golden_section(f, -1, 0, 0.003)
cm = chord(df, -1, 0, 0.003)
nm = newton_method(df, d2f, 0, 0.003)

# Вывод результатов
print(f"Half divide method: \n\tpoint = {hd}, "
      f"value = {f(hd)}\n",
      f"Golden section method: \n\tpoint = {gs}, "
      f"value = {f(gs)}\n",
      f"Chord method: \n\tpoint = {cm}, "
      f"value = {f(cm)}\n",
      f"Newton method: \n\tpoint = {nm}, "
      f"value = {f(nm)}",
      sep="")
