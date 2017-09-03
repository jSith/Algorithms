import random

def mystery_helper (A, b):
    # Input: A, a list of numbers
    # Input: b, an integer
    if b < len(A):
        return A[b] - mystery_helper(A, b+1)
    else:
        return 0

def more_mystery (A):
    # Input: A, a list of numbers
    # Input: b, an integer

    sum = 0

    for i in range (0, len(A)):
        if (i%2 != 0):
            sum -= A[i]
        else:
            sum += A[i]

    return sum

def main():

    A = []

    for i in range (0, 10):
        j = random.randint(0,100)
        A.append(j)
    print(A)

    print("Expected:", mystery_helper(A, 0))
    print("Returned:", more_mystery(A))

if __name__ == '__main__':
    main()
