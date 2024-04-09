import math


class Result:
    error_message = ""
    has_discontinuity = False

    def first_function(x: float):
        return 1 / x

    def second_function(x: float):
        if x == 0:
            return (math.sin(Result.eps) / Result.eps + math.sin(-Result.eps) / -Result.eps) / 2
        return math.sin(x) / x

    def third_function(x: float):
        return x * x + 2

    def fourth_function(x: float):
        return 2 * x + 2

    def five_function(x: float):
        return math.log(x)

    # How to use this function:
    # func = Result.get_function(4)
    # func(0.01)
    def get_function(n: int):
        if n == 1:
            return Result.first_function
        elif n == 2:
            return Result.second_function
        elif n == 3:
            return Result.third_function
        elif n == 4:
            return Result.fourth_function
        elif n == 5:
            return Result.five_function
        else:
            raise NotImplementedError(f"Function {n} not defined.")

    #
    # Complete the 'calculate_integral' function below.
    #
    # The function is expected to return a DOUBLE.
    # The function accepts following parameters:
    #  1. DOUBLE a
    #  2. DOUBLE b
    #  3. INTEGER f
    #  4. DOUBLE epsilon
    #

    def calculate_integral(a, b, f, epsilon):
        Result.error_message = ""
        Result.has_discontinuity = False

        # Get the function to integrate
        func = Result.get_function(f)

        def remove_discontinuity(f):
            def g(x):
                if f(x):
                    return f(x)
                else:
                    return 0.5 * (f(x + 1e-6) + f(x - 1e-6))

            return g

        # Check for discontinuity
        try:
            test_points = [a + epsilon * i for i in range(int((b - a) / epsilon) + 1)]
            for point in test_points:
                func(point)
        except:
            Result.error_message = "Integrated function has discontinuity or does not defined in current interval"
            Result.has_discontinuity = True
            return -69.420
        # Убираем разрывы первого рода принимая среднее значение
        func = remove_discontinuity(func)

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

        n = 1
        while True:
            l = integrate_middle_rect(func, a, b, n)
            r = integrate_middle_rect(func, a, b, n * 2)
            if abs(l - r) < epsilon:
                return r
            n += 1

try:
    fun = int(input("Enter function number: "))
    a = float(input("Enter a: "))
    b = float(input("Enter b: "))
    eps = float(input("Enter epsilon: "))
except ValueError:
    print("Please enter valid values")
    exit()
Result.calculate_integral(a, b, fun, eps)
if Result.has_discontinuity:
    print(Result.error_message)
else:
    print(Result.calculate_integral(a, b, fun, eps))
