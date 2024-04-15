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

        # Initialize the solution at the initial condition
        y = y_a

        # Calculate the initial step size
        h = (b - a) / 100.0

        while a <= b:
            # Calculate the slope at the beginning of the interval
            slope = func(a, y)

            # Estimate the solution at the end of the interval using the standard Euler method
            y_temp = y + h * slope

            # Calculate the slope at the end of the interval
            slope_end = func(a + h, y_temp)

            # Calculate the average of the two slopes
            average_slope = (slope + slope_end) / 2.0

            # Update the solution using the average slope
            y_new = y + h * average_slope

            # If the error is too large, reduce the step size
            if abs(y_new - y_temp) > epsilon:
                h = h / 2.0
            else:
                # If the error is small enough, double the step size
                h = h * 2.0
                # Update the solution and the current x-value
                y = y_new
                a = a + h

        return y


# How to use this function:
# result = Result()
# result.solveByEulerImproved(4, 0.01, 0.0, 0.0, 1.0)
result = Result()

print(result.solveByEulerImproved(4, 0.01, 0.0, 0.0, 1.0))
