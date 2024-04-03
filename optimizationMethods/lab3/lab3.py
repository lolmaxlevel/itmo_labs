# f(x) =  x^4+x^2+x+1, [a, b] = [-1, 0], eps = 0.0001

def f(x):
    return x ** 4 + x ** 2 + x + 1


def quadratic_approximation(function, a, b, eps):
    x1 = a
    x2 = (a + b) / 2
    x3 = b
    while abs(x3 - x1) > eps:
        f1 = function(x1)
        f2 = function(x2)
        f3 = function(x3)
        a1 = (f2 - f1) / (x2 - x1)
        a2 = ((f3 - f1) / (x3 - x1) - a1) / (x3 - x2)
        x_bar = (x1 + x2) / 2 - a1 / (2 * a2)
        if x_bar > x2:
            x1, x2 = x2, x_bar
        else:
            x2, x3 = x_bar, x2
    return (x1 + x3) / 2


# def quadratic_approximation_v2(x0, dx, eps1, eps2, max_iter=50):
#     x1 = x0
#     step_flag = False
#     for _ in range(max_iter):
#         if not step_flag:
#             x2 = x1 + dx
#             f1, f2 = f(x1), f(x2)
#             x3 = x1 + 2 * dx if f1 > f2 else x1 - dx
#             f3 = f(x3)
#         step_flag = False
#         F_min, x_min = min((f1, x1), (f2, x2), (f3, x3))
#         x_star = 0.5 * ((x2 ** 2 - x3 ** 2) * f1 + (x3 ** 2 - x1 ** 2) * f2 + (x1 ** 2 - x2 ** 2) * f3) / (
#                 (x2 - x3) * f1 + (x3 - x1) * f2 + (x1 - x2) * f3)
#         f_star = f(x_star)
#         if abs((F_min - f_star) / f_star) < eps1 and abs((x_min - x_star) / x_star) < eps2:
#             return x_sta
#         elif min(x1, x2, x3) <= x_star <= max(x1, x2, x3):
#             print(x1,x2,x3,x_star)
#             x2 = min(x_min, x_star)
#
#             step_flag = True
#             # print(a, a.index(x2), x1, x2, x3)
#         else:
#             x1 = x_star
#         print(f"Итерация: {_}, x_star = {x_star}, f_star = {f_star}, step_flag = {step_flag}, x1 = {x1}, x2 = {x2}, "
#               f"x3 = {x3}")
#     return x_star


# x0 = -0.3
# dx = 0.1
# eps1 = 0.0001
# eps2 = 0.0001
# extremum = quadratic_approximation_v2(x0, dx, eps1, eps2)
# print(f"Экстремум функции находится в точке x = {extremum}")

a = -1
b = 0
eps = 0.0001
extremum = quadratic_approximation(f, a, b, eps)
print(f"Экстремум функции находится в точке x = {extremum}")
