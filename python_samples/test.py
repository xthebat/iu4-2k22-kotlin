import sys
from itertools import starmap


def main(args):
    string = "abcdefght"

    list_of_tuples = [
        (1, 2),
        (3, 4)
    ]

    slices = list(map(lambda start_end: string[start_end[0]:start_end[1]], list_of_tuples))
    print(slices)

    slices = list(starmap(lambda start, end: string[start:end], list_of_tuples))
    print(slices)


if __name__ == '__main__':
    main(sys.argv)
