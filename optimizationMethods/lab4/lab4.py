# x1^4 + x2^4-2(x1-x2)^2

import numpy as np


def func(x):
    return x[0] ** 4 + x[1] ** 4 - 2 * ((x[0] - x[1]) ** 2)

# def func(x):
#     return 2*x[0] + 4*x[1]-x[0]**2-2*x[1]**2


# def gradient(x):
#     return np.array([2 - 2 * x[0], 4 - 4 * x[1]])

def gradient(x):
    return np.array([4 * (x[0]**3 - x[0] + x[1]), 4 * (x[0] + x[1]**3 - x[1])])


def gradient_descent(x_start, learning_rate, eps):
    x = x_start
    grad = gradient(x)
    new_x = x - learning_rate * grad
    while abs(func(new_x) - func(x)) >= eps:
        x = new_x
        grad = gradient(x)
        new_x = x - learning_rate * grad
    return new_x


def vector_norm(x):
    return sum(i ** 2 for i in x) ** 0.5


def minimize_scalar(func, x, grad, start=0, end=1, eps=0.001):
    while end - start > eps:
        mid = (start + end) / 2
        if func([x[i] - mid * grad[i] for i in range(len(x))]) < func(
                [x[i] - (mid + eps) * grad[i] for i in range(len(x))]):
            end = mid
        else:
            start = mid + eps
    return (start + end) / 2


def steepest_descent(x_start, eps):
    x = x_start
    while True:
        grad = gradient(x)
        learning_rate = minimize_scalar(func, x, grad)
        new_x = [x[i] - learning_rate * grad[i] for i in range(len(x))]
        if vector_norm([new_x[i] - x[i] for i in range(len(x))]) < eps:
            break
        x = new_x
    return x


x_start = [1, -1]
eps = 0.05

x_min = steepest_descent(x_start, eps)

print(f"The minimum point found by steepest descent is {x_min}")

eps = 0.05
x_start = np.array([1, -1])
learning_rate = 0.1

x_min = gradient_descent(x_start, learning_rate, eps)

print(f"The minimum point found by gradient descent is {x_min}")
