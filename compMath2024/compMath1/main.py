#
# Complete the 'interpolate_by_newton' function below.
#
# The function is expected to return a DOUBLE.
# The function accepts following parameters:
#  compMath1. DOUBLE_ARRAY x_axis
#  2. DOUBLE_ARRAY y_axis
#  3. DOUBLE x
#

def interpolate_by_newton(x_axis, y_axis, x):
    n = len(x_axis)
    if n != len(y_axis):
        raise ValueError("Количество значений аргумента и функции должно быть одинаковым")

    # Разделенные разности
    def divided_differences(x_axis, y_axis):
        n = len(x_axis)
        table = [y_axis]  # Таблица разделенных разностей
        for i in range(1, n):
            prev_row = table[-1]
            current_row = []
            for j in range(n - i):
                divided_difference = (prev_row[j + 1] - prev_row[j]) / (x_axis[j + i] - x_axis[j])
                current_row.append(divided_difference)
            table.append(current_row)
        return table

    # Вычисление интерполяционного полинома
    def newton_interpolation(x_axis, x, table):
        n = len(x_axis)
        interpolated_value = 0
        for i in range(n):
            term = table[i][0]
            for j in range(i):
                term *= (x - x_axis[j])
            interpolated_value += term
        return interpolated_value

    # Получение таблицы разделенных разностей
    table = divided_differences(x_axis, y_axis)
    # Вычисление значения интерполяционного полинома в точке x
    interpolated_value = newton_interpolation(x_axis, x, table)
    return interpolated_value


try:
    x_values = list(map(int, input("Введите массив x(через пробел): ").split()))
    y_values = list(map(int, input("Введите массив y(через пробел): ").split()))
    x = float(input("Введите точку в которой требуется найти значение: "))
except Exception:
    print("Плохой ввод, попробуйте еще раз")
    exit(-1)
try:
    print(f"Ответ:\nВ точке {x} функция принимает значение {interpolate_by_newton(x_values, y_values, x)}")
except Exception:
    print("Количество значений для x и y не равны!")
    exit(-1)