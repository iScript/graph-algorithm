import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.Scanner;

// 邻接表 , 使用红黑树实现作为默认的建图类 , 另外还有2种邻接矩阵和链表实现的邻接表
public class Graph implements  Cloneable{

    private int V;  // 顶点
    private int E;  // 边
    private TreeSet<Integer>[] adj;    // 领接表
    private  boolean directed;
    private  int[] indegrees,outdegrees; // 有向图的出度和入度


    // 默认无向图
    public Graph(String filename){
        this(filename,false);
    }

    // 更新支持有向图
    public Graph(String filename,boolean directed) {
        this.directed = directed;

        File file = new File(filename);
        if(!file.exists()) throw new IllegalArgumentException("文件不存在");

        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();  // 读取第一行的第一个，即多少个顶点
            if(V < 0) throw new IllegalArgumentException("V must be non-negative");
            adj = new TreeSet[V];    // 根据顶点创建

            for(int i = 0; i < V; i ++){
                adj[i] = new TreeSet<Integer>();
            }

            indegrees = new int[V];
            outdegrees = new int[V];

            E = scanner.nextInt();          // 第一行的第二值个为边
            if(E < 0) throw new IllegalArgumentException("E must be non-negative");
            for(int i = 0; i < E; i ++){    //
                int a = scanner.nextInt();validateVertex(a);
                int b = scanner.nextInt();validateVertex(b);

                if(a == b) throw new IllegalArgumentException("Self Loop is Detected!");    // 判断自环边
                if(adj[a].contains(b)) throw new IllegalArgumentException("Parallel Edges are Detected!");  // 判断平行边

                adj[a].add(b);
                if(directed){
                    // 统计入度和出度
                    outdegrees[a]++;
                    indegrees[b]++;
                }

                if(!directed)
                    adj[b].add(a);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    public boolean isDirected(){
        return directed;
    }

    public void validateVertex(int v){
        if(v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is invalid");
    }

    // 顶点
    public int V(){
        return V;
    }

    // 边
    public int E(){
        return E;
    }

    // 是否存在边
    public boolean hasEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        return adj[v].contains(w);
    }


    // 返回v这个顶点相邻的顶点
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    // 返回一个顶点的度，即顶点相邻的点有几个
    public int degree(int v){
        //如果是有向图
        if(directed)
            throw new IllegalArgumentException("只支持无向图");
        validateVertex(v);
        return adj[v].size();
    }

    public int indegree(int v){
        if(!directed)
            throw new RuntimeException("indegree only works in directed graph.");
        validateVertex(v);
        return indegrees[v];
    }

    public int outdegree(int v){
        if(!directed)
            throw new RuntimeException("outdegree only works in directed graph.");
        validateVertex(v);
        return outdegrees[v];
    }


    public void removeEdge(int v,int w){
        validateVertex(v);
        validateVertex(w);

        if(adj[v].contains(w)){
            E --;
            if(directed){
                indegrees[w] --;
                outdegrees[v] --;
            }
        }

        adj[v].remove(w);
        if(!directed)
            adj[w].remove(v);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("V = %d, E = %d , directed = %b \n", V, E,directed));
        for(int i = 0; i < V; i ++){
            sb.append(String.format("%d :", i));
            for(int w : adj[i])
                sb.append(String.format("%d ", w));
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public Object clone(){

        try{
            Graph cloned = (Graph)super.clone();
            cloned.adj = new TreeSet[V];
            for(int v = 0; v < V; v ++){
                cloned.adj[v] = new TreeSet<Integer>();
                for(int w: adj[v])
                    cloned.adj[v].add(w);
            }
            return  cloned;

        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }

        return null;

    }

    public static void main(String[] args){

//        Graph graph = new Graph("g.txt");
//        System.out.print(graph);

        Graph graph = new Graph("g8.txt",true);
        System.out.print(graph);
    }
}
