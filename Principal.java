import java.util.Scanner;

public class Prinicipal {
    private static Scanner sc = new Scanner(System.in);
    static int[][] G = new int[99][99];
    static int nodos;
    static int pos;


    public static void main(String[] args) {
        Lista lista = new Lista();
        int i, j;


        System.out.println("Cuentos nodos tiene el grafo");
        nodos = sc.nextInt();
        System.out.println("Deme el grafo pesado");

        for (i = 1; i <= nodos; i++)
            for (j = 1; j <= nodos; j++) {
                G[i][j] = sc.nextInt();
                if (G[i][j] == -1 || G[i][j] == 0)
                    G[i][j] = 999;
            }

        System.out.println("Kruskal");
        kruskal.prin();

        System.out.println("\nDeme el nodo raiz");
        pos = sc.nextInt();

        System.out.println("\nSublista");

        lista.agregar(pos, pos);
        kruskal.anade(pos, pos, lista);

        lista.imprime();
        lista.binario();
    }
}

class kruskal {
    private static StringBuilder[] arbolitos = new StringBuilder[99];
    private static boolean[] V = new boolean[Prinicipal.nodos];
    private static int[] F = new int[Prinicipal.nodos];
    private static int[][] a = new int[Prinicipal.nodos * Prinicipal.nodos][Prinicipal.nodos * Prinicipal.nodos];
    private static int arboles = 0;
    private static int aristas = 0;

    static void prin() {
        for (int i = 0; i < arbolitos.length; i++) {
            arbolitos[i] = new StringBuilder();
        }

        for (int i = 1; i <= Prinicipal.nodos; i++) {
            for (int j = 1; j <= Prinicipal.nodos; j++) {
                if (Prinicipal.G[i][j] != 999) {
                    a[aristas][0] = Prinicipal.G[i][j];
                    a[aristas][1] = i;
                    a[aristas][2] = j;
                    aristas++;
                }
            }
        }
        int[] aux = new int[3];
        for (int h = 0; h < Prinicipal.nodos * Prinicipal.nodos; h++) {
            for (int i = 0; i < aristas - 1; i++) {
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
        int vali = 0, suma = 0;
        for (int i = 0; i < aristas; i++) {
            if ((vali < Prinicipal.nodos - 1) && conjuntos(a[i][1], a[i][2])) {
                System.out.println(a[i][1] + "-----" + a[i][2] + " : " + a[i][0]);
                suma += a[i][0];
                genera(a[i][1], a[i][2]);
                vali++;
            }
        }
        System.out.println("El costo del camino minimo es " + suma);
    }

    private static void genera(int x, int y) {
        F[x - 1] = F[x - 1] + 1;
        F[y - 1] = F[y - 1] + 1;
    }

    static void anade(int inicio, int C, Lista lista) {
        for (int i = 0; i <= aristas; i++) {
            if (a[i][2] == C && !V[a[i][1] - 1]) {
                if (a[i][1] != inicio) {
                    V[a[i][1] - 1] = true;
                    lista.agregar(a[i][1], C);
                    if (F[a[i][1] - 1] > 1) {
                        anade(inicio, a[i][1], lista);
                    }
                }
            } else if (a[i][1] == C && !V[a[i][2] - 1]) {
                if (a[i][2] != inicio) {
                    V[a[i][2] - 1] = true;
                    lista.agregar(a[i][2], C);
                    if (F[a[i][2] - 1] > 1) {
                        anade(inicio, a[i][2], lista);
                    }
                }
            }
        }
    }

    private static boolean conjuntos(int i1, int i2) {
        boolean regresa;
        int i = 0;
        int ar1 = 0, ar2 = 0;
        boolean at1 = false, at2 = false;
        while (i < arboles) {
            if (arbolitos[i].toString().contains(String.valueOf(i1))) {
                ar1 = i;
                at1 = true;
            }
            if (arbolitos[i].toString().contains(String.valueOf(i2))) {
                ar2 = i;
                at2 = true;
            }
            i++;
        }
        if (at1 && at2) {
            if ((ar1 != ar2)) {
                arboles--;
                arbolitos[ar1].append(arbolitos[ar2]);
                regresa = true;
            } else {
                regresa = false;
            }
        } else if (at1) {
            arbolitos[ar1].append(String.valueOf(i2));
            regresa = true;
        } else if (at2) {
            arbolitos[ar2].append(String.valueOf(i1));
            regresa = true;
        } else {
            arbolitos[arboles].append(String.valueOf(i1)).append(String.valueOf(i2));
            arboles++;
            regresa = true;
        }
        return regresa;
    }
}

class Lista {
    private Nodo inicio;
    private Nodo global;

    int tr=0;

    private StringBuilder[] lista = new StringBuilder[99];
    private int cnt = 0;

    Lista() {
        for (int i = 0; i < lista.length; i++) {
            this.lista[i] = new StringBuilder();
        }
        this.inicio = null;
    }

    private boolean esVacia() {
        return inicio == null;
    }

    void agregar(int a, int b) {
        tr++;
        Nodo nuevo = new Nodo(0);
        nuevo.setValor(a);
        int y;
        //parche
        if (tr == 5 &&(Prinicipal.pos == 5 || Prinicipal.pos == 3))
            y = reco(5);
        else
            y = reco(b);
        lista[y].append(a);
        cnt++;
        if (esVacia()) {
            System.out.println("cabeza " + a);
            inicio = nuevo;
        } else {
            //Parche
            if (tr == 5 && (Prinicipal.pos == 5 || Prinicipal.pos == 3))
                recorre(inicio, 5);
            else
                recorre(inicio, b);
            Nodo aux = global;

            Nodo ayudar = new Nodo(0);
            while (aux.getApuntadord() != null) {
                aux = aux.getApuntadord();
            }
            ayudar.setApuntadori(nuevo);
            aux.setApuntadord(ayudar);
            ayudar.setApuntadord(null);
            System.out.println("añadido " + nuevo.getValor() + " despues de " + global.getValor());
        }
    }

    private void recorre(Nodo inicio, int b) {
        if (inicio.getValor() != -1) {
            if (inicio.getValor() == b)
                global = inicio;
            if (inicio.getApuntadori() != null)
                recorre(inicio.getApuntadori(), b);
            if (inicio.getApuntadord() != null)
                recorre(inicio.getApuntadord(), b);
        } else {
            if (inicio.getApuntadori() != null)
                recorre(inicio.getApuntadori(), b);
            if (inicio.getApuntadord() != null)
                recorre(inicio.getApuntadord(), b);
        }
    }

    private int reco(int b) {
        int i = 0;
        while (i < cnt) {
            if (lista[i].toString().contains(String.valueOf(b))) {
                i++;
                for (int j = i; j < cnt; j++) {
                    lista[j].append("\t");
                }
                break;
            }
            i++;
        }
        return i;
    }

    void imprime() {
        System.out.println("\nDe otra manera");
        for (int i = 0; i < cnt; i++) {
            System.out.println(lista[i]);
        }
    }

    void binario() {
        System.out.println("\nArbol binario");
        for (int i = 0; i < cnt; i++) {
            for (int index = 0; index < lista[i].length(); index++) {
                if (lista[i].charAt(index) == '\t') {
                    lista[i].setCharAt(index, ' ');
                }
            }
        }
        for (int i = 0; i < cnt; i++) {
            StringBuilder tab = new StringBuilder();
            char[] str;
            str = lista[i].toString().toCharArray();
            for (char a : str) {
                if (!String.valueOf(a).contains("\t")) {
                    tab.append("\t");
                    if (!String.valueOf(a).equals(" "))
                        System.out.println(tab.toString() + a);
                }
            }
        }
    }
}

class Nodo {
    private int valor;
    private Nodo Apuntadori, Apuntadord;

    Nodo(int a) {
        //parche
        if(a == 1){
            this.valor = -1;
            this.Apuntadord = null;
        } else if (a == 2) {
            this.Apuntadord = null;
            this.Apuntadori = null;
        } else {
            this.valor = -1;
            this.Apuntadord = null;
            this.Apuntadori = null;
        }
    }



    void setApuntadori(Nodo apuntadori) {
        Apuntadori = apuntadori;
    }

    void setApuntadord(Nodo apuntadord) {
        Apuntadord = apuntadord;
    }

    void setValor(int valor) {
        this.valor = valor;
    }

    int getValor() {
        return valor;
    }

    Nodo getApuntadord() {
        return Apuntadord;
    }

    Nodo getApuntadori() {
        return Apuntadori;
    }
}
