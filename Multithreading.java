package homeWorkMultiThreading;


/**
 * @Autor: Alexey Androsov
 * Comparison of the speed of sequential execution and multithreaded
 *
 *
 */
public class Multithreading implements Runnable {
    final static int size = 10000000;
    final static int h = size / 2;
    static float[] arr = new float[size];
    static float[] a2 = new float[h];
    static float[] a1 = new float[h];

    static void consistentExecution() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.printf("Выполнение без многопоточности заняло %d мс", (System.currentTimeMillis() - a));
        System.out.println();
    }

    static void multithreading() {
        long a = System.currentTimeMillis();


        System.arraycopy(arr, h, a2, 0, h);
        Thread thread = new Thread(new Multithreading());
        thread.start();
        for (int i = 0; i < a2.length; i++) {
            a2[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.printf("Выполнение с использованием многопоточности заняло %d мс", (System.currentTimeMillis() - a));
    }

    @Override
    public void run() {

        float[] a1 = new float[h];
        System.arraycopy(arr, 0, a1, 0, h);

        for (int i = 0; i < a1.length; i++) {
            a1[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void main(String[] args) {
        Multithreading.consistentExecution();
        Multithreading.multithreading();
    }
}
