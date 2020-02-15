import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

// 带权图
public class WeightedGraph implements  Cloneable{

    private int V;  // 顶点
    private int E;  // 边
    private TreeMap<Integer,Integer>[] adj; //adj数组，里面保存着TreeMap。treemap参数 相邻的顶点 ，权值

    public WeightedGraph(String filename) {
        File file = new File(filename);
        if(!file.exists()) throw new IllegalArgumentException("文件不存在");

        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();  // 读取第一行的第一个，即多少个顶点
            if(V < 0) throw new IllegalArgumentException("V must be non-negative");
            adj = new TreeMap[V];    // 根据顶点创建数组

            for(int i = 0; i < V; i ++){
                adj[i] = new TreeMap<Integer,Integer>();    // 数组里保存着treeMap
            }

            E = scanner.nextInt();
            if(E < 0) throw new IllegalArgumentException("E must be non-negative");
            for(int i = 0; i < E; i ++){    //
                int a = scanner.nextInt();validateVertex(a);
                int b = scanner.nextInt();validateVertex(b);
                int weight = scanner.nextInt(); //一行的第三个值是权重

                if(a == b) throw new IllegalArgumentException("Self Loop is Detected!");    // 判断自环边
                if(adj[a].containsKey(b)) throw new IllegalArgumentException("Parallel Edges are Detected!");  // 判断平行边

                adj[a].put(b,weight);   // adj[x]顶点是一个TreeMap，每个顶点对应着相连的顶点和权重
                adj[b].put(a,weight);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

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
        return adj[v].containsKey(w);
    }


    // 返回v这个顶点相邻的顶点
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v].keySet();     // key是顶点、value是权重
    }

    // 返回边对应的权重
    public int getWeight(int v,int w){
        if(hasEdge(v,w))
            return adj[v].get(w);
        throw new IllegalArgumentException(String.format("no edge %d %d",v,w));
    }

    // 返回一个顶点的度，即顶点相邻的点有几个
    public int degree(int v){
        validateVertex(v);
        return adj[v].size();
    }


//    public void removeEdge(int v,int w){
//        validateVertex(v);
//        validateVertex(w);
//
//        adj[v].remove(w);
//        adj[w].remove(v);
//    }
//
//
//    @Override
//    public Object clone(){
//        try{
//            WeightedGraph cloned = (WeightedGraph)super.clone();
//            cloned.adj = new TreeMap[V];
//            for(int v = 0; v < V; v ++){
//                cloned.adj[v] = new TreeMap<Integer,Integer>();
//                for(Map.Entry<Integer,Integer> entry : adj[v].entrySet() ) //遍历map
//                    cloned.adj[v].put(entry.getKey(),entry.getValue());
//            }
//            return  cloned;
//        }catch(CloneNotSupportedException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
////
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("V = %d, E = %d\n", V, E));
        for(int i = 0; i < V; i ++){
            sb.append(String.format("%d :", i));
            for(Map.Entry<Integer,Integer> entry : adj[i].entrySet() ) //遍历map
                sb.append(String.format("(%d : %d)", entry.getKey(),entry.getValue()));
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args){

        WeightedGraph g = new WeightedGraph("g6.txt");
        System.out.print(g);
    }
}
