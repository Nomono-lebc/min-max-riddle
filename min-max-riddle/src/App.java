import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class App {
        
        /**
         * Monotonic Decreasing Stack
         * — Antes de hacer push(x), desapilamos todo elemento < x,
         *   de modo que el tope siempre sea ≥ que cualquier elemento debajo.
         */
        public static void monotonicDecreasing(int[] arr) {
            Stack<Integer> stack = new Stack<>();
            for (int x : arr) {
                // Mientras haya elementos y el tope sea menor que x, desapilamos
                while (!stack.isEmpty() && stack.peek() < x) {
                    stack.pop();
                }
                stack.push(x);
            }
            // Al final 'stack' contiene valores en orden no creciente (de mayor a menor)
            System.out.println("Decreasing stack: " + stack);
        }
    
        /**
         * Monotonic Increasing Stack
         * — Antes de hacer push(x), desapilamos todo elemento > x,
         *   de modo que el tope siempre sea ≤ que cualquier elemento debajo.
         */
        public static void monotonicIncreasing(int[] arr) {
            Stack<Integer> stack = new Stack<>();
            for (int x : arr) {
                // Mientras haya elementos y el tope sea mayor que x, desapilamos
                while (!stack.isEmpty() && stack.peek() > x) {
                    stack.pop();
                }
                stack.push(x);
            }
            // Al final 'stack' contiene valores en orden no decreciente (de menor a mayor)
            System.out.println("Increasing stack: " + stack);
        }
    
        public static void main(String[] args) {
            int[] data = {2, 1, 5, 3, 4};
            monotonicDecreasing(data);  // Ejemplo de pila monótona decreciente
            monotonicIncreasing(data);  // Ejemplo de pila monótona creciente

            int[] result = riddle(data);
            System.out.println("Result: " + Arrays.toString(result));
        }


        // Solo el método de solución:
public static int[] riddle(int[] arr) {
    int n = arr.length;
    int[] left  = new int[n];
    int[] right = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    // 1) previous smaller element
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
            stack.pop();
        }
        left[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(i);
    }

    stack.clear();
    // 2) next smaller element
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
            stack.pop();
        }
        right[i] = stack.isEmpty() ? n : stack.peek();
        stack.push(i);
    }

    // 3) bestMin[len] = mejor mínima para ventanas de tamaño len
    int[] bestMin = new int[n + 1];
    Arrays.fill(bestMin, Integer.MIN_VALUE);
    for (int i = 0; i < n; i++) {
        int len = right[i] - left[i] - 1;
        bestMin[len] = Math.max(bestMin[len], arr[i]);
    }

    // 4) rellenar huecos: asegurar que bestMin[k] ≥ bestMin[k+1]
    for (int k = n - 1; k >= 1; k--) {
        bestMin[k] = Math.max(bestMin[k], bestMin[k + 1]);
    }

    // 5) construir resultado (índices 1…n)
    int[] result = new int[n];
    for (int k = 1; k <= n; k++) {
        result[k - 1] = bestMin[k];
    }
    return result;
}
    }
