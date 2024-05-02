package Players;

public class ArrayStack<T> {
    private T[] array;
    private int top; // 栈顶指针

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        array = (T[]) new Object[capacity];
        top = 0;
    }

    public ArrayStack() {
        this(10);
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public boolean isFull() {
        return top == array.length;
    }

    public void push(T element) {
        if (isFull()) {
            throw new RuntimeException("Stack is full!");
        }
        array[top++] = element;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty!");
        }
        return array[--top];
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty!");
        }
        return array[top - 1];
    }
}
