# Solo el método de solución:
def riddle(arr: list[int]) -> list[int]:
    n = len(arr)
    left  = [-1] * n
    right = [ n] * n
    stack = []

    # 1) previous smaller
    for i in range(n):
        while stack and arr[stack[-1]] >= arr[i]:
            stack.pop()
        left[i] = stack[-1] if stack else -1
        stack.append(i)

    # 2) next smaller
    stack.clear()
    for i in range(n - 1, -1, -1):
        while stack and arr[stack[-1]] >= arr[i]:
            stack.pop()
        right[i] = stack[-1] if stack else n
        stack.append(i)

    # 3) bestMin[len] = mejor mínima para ventanas de tamaño len
    best_min = [0] * (n + 1)
    # Inicializamos con -inf implícito: como valores ≥ 0, basta con 0
    for i in range(n):
        length = right[i] - left[i] - 1
        best_min[length] = max(best_min[length], arr[i])

    # 4) rellenar huecos de derecha a izquierda
    for k in range(n - 1, 0, -1):
        best_min[k] = max(best_min[k], best_min[k + 1])

    # 5) devolvemos posiciones 1…n
    return best_min[1:]


if __name__ == "__main__":
  print(riddle([6, 3, 9, 8, 10, 2, 1, 15, 7]))