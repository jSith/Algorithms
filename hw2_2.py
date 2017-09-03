import random

def largest_increase(A):

    # Input: A, a list of integers
    # Output: the largest increase between consecutive integers in A

    max = 0

    for i in range (0, len(A)-1):
        diff = A[i+1] - A[i]
        if diff > max:
            max = diff
    return max

def recursive_increase (A, index, max):

    # Input: A, a list of integers; index, an integer; and max, an integer
    # Output: the largest increase between consecutive integers in A

    if index >= len(A)-1:
        return max
    elif (A[index+1] - A[index]) > max:
        return recursive_increase(A, index+1, A[index+1] - A[index])
    else:
        return recursive_increase(A, index+1, max)

def main():

    A = []
    for i in range (0, 10):
        j = random.randint(0,100)
        j = random.randint(0,100)
        A.append(j)
    print(A)

    print("Expected:", largest_increase(A))
    print("Returned:", recursive_increase(A, 0, 0))


if __name__ == '__main__':
    main()
