
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//邻接矩阵
public class AdjacencyMatrix {

    private int V;  // 顶点
    private int E;  // 边
    private int[][] adj;    // 领接矩阵

    public AdjacencyMatrix(String filename) {
        File file = new File(filename);
        if(!file.exists()) throw new IllegalArgumentException("文件不存在");

        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();  // 读取第一行的第一个，即多少个顶点
            if(V < 0) throw new IllegalArgumentException("V must be non-negative");
            adj = new int[V][V];    // 根据顶点创建矩阵

            E = scanner.nextInt();          // 第一行的第二值个为边
            if(E < 0) throw new IllegalArgumentException("E must be non-negative");
            for(int i = 0; i < E; i ++){    //根据边生成邻接矩阵，每一行2个值表示a b ，表示这2个顶点相连
                int a = scanner.nextInt();validateVertex(a);
                int b = scanner.nextInt();validateVertex(b);

                if(a == b) throw new IllegalArgumentException("Self Loop is Detected!");    // 判断自环边
                if(adj[a][b] == 1) throw new IllegalArgumentException("Parallel Edges are Detected!");  // 判断平行边

                adj[a][b] = 1;
                adj[b][a] = 1;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


    private void validateVertex(int v){
        if(v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is invalid");
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    // 是否存在边
    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }


    // 返回v这个顶点相邻的顶点
    public ArrayList<Integer> adj(int v) {
        validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++)
            if (adj[v][i] == 1)
                res.add(i);
        return res;
    }

    // 返回一个顶点的度，即顶点相邻的点有几个
    public int degree(int v){
        return adj(v).size();
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("V = %d, E = %d\n", V, E));
        for(int i = 0; i < V; i ++){
            for(int j = 0; j < V; j ++)
                sb.append(String.format("%d ", adj[i][j]));
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args){

        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix("g.txt");
        System.out.print(adjacencyMatrix);
    }

}
