package org.example;

import java.util.*;


public class Main {
    static final int N = 109;
    public static int n, m;
    public static List<Integer>[][] nt = new List[N][N], nt1 = new List[N][N];
    static Set<Integer>[] closure = new HashSet[N];

    static void printWithoutEps() {
        System.out.println("\nNFA without epsilon moves:");
        System.out.println("============================");
        System.out.println("Q\t\tSymbols");

        for (int i = 0; i < n; i++) {
            System.out.print("Q" + i + "\t");
            for (int j = 1; j <= m; j++) {
                System.out.print("{");
                for (int ii : nt1[i][j]) {
                    System.out.print(ii + " ");
                }
                System.out.print("}\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void gettingNFA(){
        Scanner sc = new Scanner(System.in);
        System.out.print("No. of states: ");
        n = sc.nextInt();
        System.out.print("No. of input symbols: ");
        m = sc.nextInt();
        System.out.println("Enter transitions:\n");

        // Transition table
        for (int i = 0; i < n; i++) {
            System.out.println("State " + i);
            for (int j = 0; j <= m; j++) {
                System.out.print("\tNo of transitions for ");
                if (j == 0) {
                    System.out.print("eps");
                } else {
                    System.out.print((char) (j + 'a' - 1));
                }
                System.out.print(": ");
                int temp = sc.nextInt();
                nt[i][j] = new ArrayList<>();
                if (temp == 0) {

                } else if (temp == 1) {
                    System.out.print("\tEnter the state: ");
                } else {
                    System.out.print("\tEnter the " + temp + " states: ");
                }
                for (int k = 0; k < temp; k++) {
                    nt[i][j].add(sc.nextInt());
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        gettingNFA();

        // Finding epsilon closure for each state
        for(int i=0; i<n; i++) {
            Queue<Integer> q = new LinkedList<>();
            Set<Integer> vis = new HashSet<>();
            q.add(i); vis.add(i);
            while(!q.isEmpty()) {
                int top = q.poll();
                for(int k=0; k<nt[top][0].size(); k++) {
                    int cur = nt[top][0].get(k);
                    if(!vis.contains(cur)) {
                        vis.add(cur);
                        q.add(cur);
                    }
                }
            }
            closure[i] = new HashSet<>(); // Initialize the set
            for(int j=0; j<n; j++) {
                if(vis.contains(j)) closure[i].add(j);
            }
        }

        // Find epsilon* --> symbol --> epsilon* for each state and symbol
        for (int i = 0; i < n; i++) {
            for (int ii : closure[i]) {
                for (int j = 1; j <= m; j++) {
                    if (nt[ii][j] == null) {
                        continue;
                    }
                    if (nt1[i][j] == null) {
                        nt1[i][j] = new ArrayList<Integer>();
                    }
                    for (int k = 0; k < nt[ii][j].size(); k++) {
                        int cur = nt[ii][j].get(k);
                        for (int iii : closure[cur]) {
                            nt1[i][j].add(iii);
                        }
                    }
                }
            }
        }

        printWithoutEps();
    }
}