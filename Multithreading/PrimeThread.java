import java.util.*;

class Prime extends Thread {

    int start;
    int end;
    int count;

    public Prime(int a, int b) {
        start = a;
        end = b;
        count = 0;
    }

    public void run() {
        int i, j;
        if (start < 2)
            start = 2;

        for (i = start; i < end; i++) {
            for (j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    count--;
                    break;
                }
            }
            count++;
        }
        // System.out.println(count);
    }

}

public class PrimeThread {
    public static void main(String[] args) {

        int N, delta, i, j, z, sum=0, count=0;
        long tStart, tEnd, sStart, sEnd, tThreaded = 0, tSerial = 0;
        Scanner in = new Scanner(System.in);
        final int nIter = 10;

        System.out.println("Enter number of threads: ");
        N = Integer.parseInt(in.nextLine());
        System.out.println("Enter number limit (delta): ");
        delta = Integer.parseInt(in.nextLine());

        for (z = 0; z < nIter; z++) {
            sum = 0;
            count = 0;

            tStart = System.nanoTime();
            {
                Prime[] p = new Prime[N];

                for (i = 0; i < N; i++) {
                    p[i] = new Prime(i * delta, (i + 1) * delta);
                    p[i].start();
                }
                try {
                    p[N - 1].join();
                } catch (InterruptedException e) {
                }

                for (i = 0; i < N; i++) {
                    sum += p[i].count;
                }

            }
            tEnd = System.nanoTime();

            sStart = System.nanoTime();
            {
                for (i = 2; i < N * delta; i++) {
                    for (j = 2; j * j <= i; j++) {
                        if (i % j == 0) {
                            count--;
                            break;
                        }
                    }
                    count++;
                }

            }
            sEnd = System.nanoTime();

            tThreaded += (tEnd - tStart);
            tSerial += (sEnd - sStart);
        }

        tThreaded /= nIter;
        tSerial /= nIter;

        System.out.println("Number of primes between 0-" + N * delta + "= " + sum);
        System.out.println("Number of primes between 0-" + N * delta + "= " + count);
        System.out.println("Threaded Time(nanosec) = " + tThreaded);
        System.out.println("Serial Time(nanosec) = " + tSerial);
        System.out.println("Time Ratio(tThreaded to tSerial) = " + (float) tThreaded / (float) tSerial);
        in.close();
    }
}