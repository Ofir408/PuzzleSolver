import random
import sys


def _generate_initial_board(board_size: int):
    # example format for board size=3, 8-6-7-2-5-4-3-0-1
    while True:
        board = random.sample(range(board_size ** 2), board_size ** 2)
        if is_solvable(board, board_size):
            return '-'.join(str(x) for x in board)


def _generate_game(board_size: int, output_file_path: str):
    f = open(output_file_path, "w")
    initial_board = _generate_initial_board(board_size)
    f.write("3\n{0}\n{1}".format(board_size, initial_board))
    f.close()


def get_inv_count(arr, board_size):
    inv_count = 0
    for i in range(0, 2):
        for j in range(i + 1, board_size):
            # Value 0 is used for empty space
            if arr[j][i] > 0 and arr[j][i] > arr[i][j]:
                inv_count += 1
    return inv_count


def __divide_chunks(l, n):
    # looping till length l
    for i in range(0, len(l), n):
        yield l[i:i + n]


# if given 8 puzzle is solvable.
def is_solvable(puzzle, board_size):
    lists = __divide_chunks(puzzle, board_size)
    # Count inversions in given 8 puzzle
    invCount = get_inv_count(list(lists), board_size)
    # return true if inversion count is even.
    return invCount % 2 == 0


def generate_games(games_num: int, board_size: int, output_directory_path: str):
    for i in range(0, games_num):
        output_path = output_directory_path + "/input{0}.txt".format(i)
        _generate_game(board_size, output_path)


if __name__ == '__main__':
    if len(sys.argv) != 1:
        print("Enter output directory path as an argument")
        exit(1)

    directory_path = sys.argv[0]
    generate_games(games_num=20, board_size=3, output_directory_path=directory_path)
