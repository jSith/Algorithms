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


def prefix (A, start, end):
    #Note: the prefix of a list is either the prefix of its first half, or the sum of its first half plus the prefix of its left half
    # Input: A, a list of integers; start, the starting index; end, the ending index;
        # sum, a running total of the sum of A's elements; max, the max prefix sum of A
    # Output: The largest prefix sum in A and the sum of the whole list

    # Base case: length is 1
    if start == end:
        return A[start], A[start]

    # Split in 2
    midpoint = math.floor((start+end)/2)
    left = prefix(A, start, midpoint)
    right = prefix(A, midpoint+1, end)

    # Find sum + prefix for both halves
    if left[0] > right[0] + left[1]:
        return left[0], left[1] + right[1]
    else:
        return right[0]+left[1], left[1] + right[1]

def main():
    A = [6, 4, -2, 3, -5, 1]
    print("Expected:", max_sum(A, 0, 0, 0))
    print("Actual:", prefix(A, 0, len(A)-1))

if __name__ == '__main__':
    main()
