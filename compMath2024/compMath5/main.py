import math

class Result:

    def first_function(x: float, y: float):
        return math.sin(x)

    def second_function(x: float, y: float):
        return (x * y) / 2

    def third_function(x: float, y: float):
        return y - (2 * x) / y

    def fourth_function(x: float, y: float):
        return x + y

    def default_function(x: float, y: float):
        return 0.0

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
        else:
            return Result.default_function

    #
    # Complete the 'solveByEulerImproved' function below.
    #
    # The function is expected to return a DOUBLE.
    # The function accepts following parameters:
    #  1. INTEGER f
    #  2. DOUBLE epsilon
    #  3. DOUBLE a
    #  4. DOUBLE y_a
    #  5. DOUBLE b
    #
    def solveByEulerImproved(self, f, epsilon, a, y_a, b):
        # Get the function to integrate
        func = Result.get_function(f)

        step_size = (b - a) / 1000  # initial step size
        current_x = a
        current_y = y_a
        while current_x < b:
            half_step = step_size / 2
            delta_y = step_size * func(current_x + half_step, current_y + half_step * func(current_x, current_y))
            new_y = current_y + delta_y
            while abs(new_y - current_y) > epsilon:
                step_size = step_size / 2  # reduce step size
                half_step = step_size / 2
                delta_y = step_size * func(current_x + half_step, current_y + half_step * func(current_x, current_y))
                new_y = current_y + delta_y
            current_y = new_y
            current_x += step_size
        return current_y


def main():
    print("Выберите функцию для интегрирования:")
    print("1: sin(x)")
    print("2: (x * y) / 2")
    print("3: y - (2 * x) / y")
    print("4: x + y")
    try:
        function_index = int(input("Введите номер функции: "))

        precision = float(input("Введите желаемую точность: "))
        start_x = float(input("Введите начальное значение x: "))
        start_y = float(input("Введите начальное значение y(x): "))
        end_x = float(input("Введите конечное значение x: "))
    except ValueError:
        print("Ошибка ввода. Попробуйте снова.")
        return

    result = Result().solveByEulerImproved(function_index, precision, start_x, start_y, end_x)
    print(f"Результат: {result}")


if __name__ == "__main__":
    main()
