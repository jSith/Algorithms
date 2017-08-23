import sys

def euclidean_algorithm (a, b):

    r = 1

    while r != 0:
        if a < b:
            a = a + b
            b = a - b
            a = a - b
        r = a % b
        if r == 0:
            return b
        a = b
        b = r

    return b

def main():
    print(euclidean_algorithm(int(sys.argv[1]), int(sys.argv[2])))

if __name__ == '__main__':
    main()