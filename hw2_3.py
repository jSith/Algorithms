import math

def max_sum (A, index, sum, max):
    # Input: A, a list of integers; index, an integer; and max, an integer
    # Output: the largest prefix sum in the given list and the prefix sum of the whole list

    if index >= len(A):
        return max, sum
    elif sum+A[index] > max:
        return max_sum(A, index+1, sum+A[index], sum+A[index])
    else:
        return max_sum(A, index+1, sum+A[index], max)

def prefix (A, start, end, sum, max):
    left = prefix(A, start+1, math.floor(len(A)/2), sum + A[start], max)
    right = prefix(A, len(A)/2 + 1, end, sum + math.floor(A[len(A)/2] + 1), max)
    if left[0] > right[0] + left[1]:
        return
    else:
        return right[0] + left[1]



def main():
    A = [6, 4, -2, 3, -5, 1]
    print("Expected:", max_sum(A, 0, 0, 0))

if __name__ == '__main__':
    main()