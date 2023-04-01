import numpy as np

from equations import Equation
import math

list_of_equations = [
    Equation(lambda x: -(0.5 + x ** 2 - math.cos(x)),
             lambda x: x - (0.5 + x ** 2 - math.cos(x)), "-(0.5 + x^2 - cos(x))"),
    Equation(lambda x: math.sin(x),
             lambda x: x + math.sin(x), "sin(x)"),
    Equation(lambda x: -(math.log(x) - math.cos(x)),
             lambda x: x - (math.log(x) - math.cos(x)), "-(log(x)-cos(x))"),
    Equation((lambda x1, x2: 1 + math.cos(x2), lambda x1, x2: 1 + math.sin(x1))
             , None, "1+cos(y)=0\n1+sin(x)=0"),
    Equation((lambda x1, x2: x1**2+x2**2-1, lambda x1, x2: math.sin(x1)-1),
             None, "x^2+y^2=1\n1+sin(x)=0")
]


def simple_iteration_method_for_systems(f, first_guess, _eps, max_iterations=1000):
    """
    Решает систему нелинейных уравнений методом простых итераций.

    f: список из 2 функций
    first_guess: начальное приближение.
    _eps: заданная точность.
    max_iterations: максимальное количество итераций.
    """
    x_prev = first_guess.copy()
    x_cur = [f[0](x_prev[0], x_prev[1]), f[1](x_prev[0], x_prev[1])]
    for i in range(max_iterations):
        if abs(sum(np.array(x_prev) - np.array(x_cur))) < _eps:
            x_prev = x_cur.copy()
            x_cur = [f[0](x_prev[0], x_prev[1]), f[1](x_prev[0], x_prev[1])]
        else:
            return x_cur
    raise Exception("Превышено количество итераций")


def simple_iteration_method(g, x0=0.99, _eps=1e-5, max_iterations=1000):
    """
    Решает нелинейное уравнение методом простых итераций.

    g: преобразованная функция f, (в моем примере x-f(x)).
    x0: начальное приближение.
    _eps: заданная точность.
    max_iterations: максимальное количество итераций.
    """
    x_old = x0
    for i in range(max_iterations):
        x_new = g(x_old)
        if abs(x_new - x_old) < eps:
            return x_new
        x_old = x_new
    raise Exception("Превышено максимальное количество итераций.")


def bisection_method(f, a, b, _eps):
    """
    Решает нелинейное уравнение методом деления пополам.

    f: функция, содержащая неизвестное значение x.
    a, b: начальные границы для решения уравнения.
    _eps: заданная точность.
    """
    if f(a) * f(b) > 0:
        raise Exception("На отрезке нет корней")
    while abs(b - a) > _eps:
        c = (a + b) / 2
        if f(c) == 0:
            return c
        elif f(a) * f(c) < 0:
            b = c
        else:
            a = c
    return (a + b) / 2


if input("Введите 1 для решения уравнения и 2 для решения системы: ") == "1":
    try:
        # noinspection PyTupleAssignmentBalance
        eq, approx, a, b, eps, max_iter = [int(input("Выберите какое уравнение решать: \n" + "\n".join(
            [str(i + 1) + "." + list_of_equations[i].string for i in range(3)]) + "\n")),
                                           float(input("Введите приближенное значение(для метода итераций): ")),
                                           *map(int, input("Введите границы для метода деления пополам: ").split(" ")),
                                           float(input("Введите желаемую точность: ")),
                                           int(input("Введите максимальное количество итераций: "))]
    except Exception:
        print("ух ты блин, ошибочка, перезапустите программу и повторите ввод")
        exit(-1)
    try:
        ans1 = simple_iteration_method(list_of_equations[int(eq) - 1].equation_g, approx, eps, max_iter)
        ans2 = bisection_method(list_of_equations[int(eq) - 1].equation, a, b, eps)
    except Exception as e:
        print("Ошибка!", e)
        exit(-1)
    print(f"Ответ методом простых итераций: {ans1}.\n"
          f"Ответ методом деления пополам: {ans2}.\n"
          f"Разница между этими методами:{abs(ans1-ans2)}.")
else:
    try:
        eq, start_points, eps = [int(input("Выберите какое уравнение решать: \n" + "\n".join(
            [str(i + 1) + "." + list_of_equations[i].string for i in range(3, 5)]) + "\n")),
                                           list(map(float, input("Введите 2 приближенных значения x и y:").split(" "))),
                                           float(input("Введите желаемую точность: ")),
                                           ]
    except Exception as e:
        print("а у вас ошибка, перезапустите программу и выполните ввод снова")
        exit(-1)
    try:
        ans = simple_iteration_method_for_systems(list_of_equations[int(eq) - 1].equation, start_points, eps, 1000)
    except Exception as e:
        print("Ошибка!", e)
        exit(-1)
    print(f"Корни: x: {ans[0]} y: {ans[1]}")
