import math
import scipy.integrate as integrate

from Equation import Equation


def remove_discontinuity(f):
    def g(x):
        if f(x):
            return f(x)
        else:
            return 0.5 * (f(x + 1e-6) + f(x - 1e-6))

    return g


def integrate_middle_rect(f, a, b, n):
    """
    Вычисляет приближенное значение определенного интеграла функции f на интервале [a, b]
    с использованием метода средних прямоугольников с n прямоугольниками.
    """
    dx = (b - a) / n  # ширина прямоугольников
    x = a + dx / 2  # координата центра первого прямоугольника
    area = 0  # суммарная площадь всех прямоугольников

    for i in range(n):
        area += f(x) * dx  # вычисляем площадь текущего прямоугольника
        x += dx  # перемещаемся к центру следующего прямоугольника
    return area


def integrate_left_rect(f, a, b, n):
    """
    Вычисляет приближенное значение определенного интеграла функции f на интервале [a, b]
    с использованием метода левых прямоугольников с n прямоугольниками.
    """
    dx = (b - a) / n  # ширина прямоугольников
    x = a  # координата левого края первого прямоугольника
    area = 0  # суммарная площадь всех прямоугольников

    for i in range(n):
        area += f(x) * dx  # вычисляем площадь текущего прямоугольника
        x += dx  # перемещаемся к левому краю следующего прямоугольника

    return area


def integrate_right_rect(f, a, b, n):
    """
    Вычисляет приближенное значение определенного интеграла функции f на интервале [a, b]
    с использованием метода правых прямоугольников с n прямоугольниками.
    """
    dx = (b - a) / n  # ширина прямоугольников
    x = a + dx  # координата правого края первого прямоугольника
    area = 0  # суммарная площадь всех прямоугольников

    for i in range(n):
        area += f(x) * dx  # вычисляем площадь текущего прямоугольника
        x += dx  # перемещаемся к правому краю следующего прямоугольника
    return area


def equation_1(x):
    if x > 0:
        return 1
    elif x < 0:
        return 0


equations = [Equation(lambda x: x ** 2, [], "x^2"),
             Equation(lambda x: math.sin(x), [], "sin(x)"),
             Equation(lambda x: math.cos(x), [], "cos(x)"),
             Equation(equation_1, [0], "x=1, x>0\nx=0, x<0"),
             ]

try:
    equation, a, b, n = equations[int(input("Выберите какое уравнение решать: \n" + "\n".join(
            [str(i + 1) + "." + equations[i].string_form for i in range(len(equations))]) + "\n")) - 1], \
            int(input("Введите левую границу: ")), \
            int(input("Введите правую границу: ")), int(input("Введите количество прямоугольников: "))
    if n < 1:
        raise ValueError("Количество прямоугольников должно быть больше 0")
    if a > b:
        raise ValueError("Левая граница должна быть меньше правой")
except (IndexError, ValueError) as e:
    if e == IndexError:
        print("Такого уравнения нет, перезапустите программу и введите номер уравнения из списка")
    if e == ValueError:
        print("Неверный формат ввода, перезапустите программу и введите число")
    exit(1)


if equation.discontinuity_points and a < equation.discontinuity_points[0] < b:
    left_result = integrate_left_rect(remove_discontinuity(equation.f), a, b, n)
    right_result = integrate_right_rect(remove_discontinuity(equation.f), a, b, n)
    middle_result = integrate_middle_rect(remove_discontinuity(equation.f), a, b, n)
else:
    left_result = integrate_left_rect(equation.f, a, b, n)
    right_result = integrate_right_rect(equation.f, a, b, n)
    middle_result = integrate_middle_rect(equation.f, a, b, n)


correct = integrate.quad(remove_discontinuity(equation.f), a, b)[0]


print(f"Результат методом левых прямоугольников: {left_result} ошибка: {abs(correct - left_result)}")
print(f"Результат методом правых прямоугольников: {right_result} ошибка: {abs(correct - right_result)}")
print(f"Результат методом центральных прямоугольников: {middle_result} ошибка: {abs(correct - middle_result)}")
print(f"Правильный ответ: {correct}")
