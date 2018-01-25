import sys
import random


def euclidean_algorithm (a, b):

    remainder = 1

    while remainder != 0:
        if a < b:
            a = a + b
            b = a - b
            a = a - b
        remainder = a % b
        a = b
        b = remainder

    print(a)
    return a


def linear_combination(d, a, b):

    combo = 0
    x = -10
    y = 1

    while combo != d:
        y = random.randint(-100, 100)
        x = random.randint(-100, 100)
        combo = x * a + y * b

    return x, y


def main():
    a = int(sys.argv[1])
    b = int(sys.argv[2])
    print(linear_combination(euclidean_algorithm(a, b), a, b))

if __name__ == '__main__':
    main()