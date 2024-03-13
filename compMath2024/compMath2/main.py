class Solution:
    isSolutionExists = True
    errorMessage = ""

    #
    # Complete the 'solveByGauss' function below.
    #
    # The function is expected to return a DOUBLE_ARRAY.
    # The function accepts following parameters:
    #  1. INTEGER n
    #  2. 2D_DOUBLE_ARRAY matrix
    #
    @staticmethod
    def gaussian_eliminate(matrix):
        """
        Метод для преобразования матрицы к треугольному виду

        :param matrix: исходная матрица
        :return: преобразованная треугольная матрица
        """
        n = len(matrix)
        for i in range(n):
            pivot = matrix[i][i]
            if pivot == 0:
                # Если на главной диагонали находится 0, то метод не применим, выставляем флаг и выходим из метода
                Solution.isSolutionExists = False
                Solution.errorMessage = "The system has no roots of equations or has an infinite set of them."
                return matrix
            for j in range(i + 1, n):
                ratio = matrix[j][i] / pivot
                matrix[j] = [matrix[j][k] - ratio * matrix[i][k] for k in range(n + 1)]
        return matrix

    @staticmethod
    def back_substitute(matrix):
        """
        Метод для обратной подстановки и нахождения корней системы

        :param matrix: треугольная матрица
        :return: корни системы уравнений
        """
        n = len(matrix)
        x = [0]*n
        for i in range(n - 1, -1, -1):
            x[i] = (matrix[i][n] - sum(matrix[i][j] * x[j] for j in range(i + 1, n))) / matrix[i][i]
        return x

    @staticmethod
    def calculate_residuals(matrix, x):
        """
        Метод для вычисления невязок системы

        :param matrix: исходная матрица
        :param x: корни системы
        :return: невязки системы
        """
        n = len(matrix)
        residuals = [0]*n
        for i in range(n):
            residuals[i] = matrix[i][n] - sum(matrix[i][j] * x[j] for j in range(n))
        return residuals

    @staticmethod
    def solveByGauss(n, matrix):
        """
        Основной метод для решения системы уравнений методом Гаусса

        :param n: размерность матрицы(unused)
        :param matrix: исходная матрица
        :return: корни системы уравнений и невязки или сообщение об ошибке если метод не применим
        """
        matrix_copy = matrix.copy()
        matrix = Solution.gaussian_eliminate(matrix)
        if Solution.isSolutionExists:
            x = Solution.back_substitute(matrix)
            residuals = Solution.calculate_residuals(matrix_copy, x)
            return x + residuals
        else:
            return Solution.errorMessage


if __name__ == '__main__':
    try:
        n = int(input("Enter the number of equations: "))
        matrix = []
        for i in range(n):
            matrix.append(list(map(float, input().split())))
    except ValueError:
        print("Invalid input")
        exit(-1)
    sol = Solution.solveByGauss(n, matrix)
    if Solution.isSolutionExists:
        print("Roots of the system of equations: ", sol[:n])
        print("Residuals of the system of equations: ", sol[n:])
    else:
        print(sol)
