import java.util.Arrays;
import java.util.Scanner;

public class Prinicipal {
    static int[][] G = new int[99][99];
    private static Scanner sc = new Scanner(System.in);
    private static int nodos;
    public static void main(String[] args) {
        System.out.println("Cuentos nodos tiene el grafo");
        nodos = sc.nextInt();
        System.out.println("Deme el grafo pesado");
        int i,j;
        for (i = 1; i <= nodos; i++)
            for (j = 1; j <= nodos; j++) {
                G[i][j] = sc.nextInt();
                if (G[i][j] == -1 || G[i][j] == 0)
                    G[i][j] = 999;
            }
        kruskal();

    }
    private static void kruskal(){
        boolean[] v = new boolean[nodos+1];
        int[][] a = new int[99][99];
        Arrays.fill(v,false);
        int r=0;
        for (int i = 1; i <= nodos ; i++) {
            for (int j = 1; j <= nodos; j++) {
                a[r][0] = G[i][j];
                a[r][1] = i;
                a[r][2] = j;
                r++;
            }
        }

        v[1]=true;
        int[] aux = new int[99];
        for (int h = 0; h < nodos*nodos; h++) {
            for (int i = 0; i < nodos*nodos -1; i++) {
                if (a[i][0] > a[i + 1][0]) {

                    aux[0] = a[i + 1][0];
                    aux[1] = a[i + 1][1];
                    aux[2] = a[i + 1][2];

                    a[i + 1][0] = a[i][0];
                    a[i + 1][1] = a[i][1];
                    a[i + 1][2] = a[i][2];

                    a[i][0] = aux[0];
                    a[i][1] = aux[1];
                    a[i][2] = aux[2];
                }

            }
        }
        int j=0;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < nodos; i++) {
            if (!s.toString().contains(String.valueOf(a[j][1])+String.valueOf(a[j][2]))){
                System.out.println(a[j][1] + "->" + a[j][2]);
                s.append(String.valueOf(a[j][1])).append(String.valueOf(a[j][2]));
            }
            j++;
        }

    }
}
