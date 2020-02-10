import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

//
public class Path {
    private Graph G;
    private int s;
    private int t;
    private boolean[] visited;
    private int[] pre;


    public Path(Graph G , int s, int t){
        G.validateVertex(s);
        G.validateVertex(s);

        this.G = G;
        this.s = s; // 原点
        this.t = t;

        visited = new boolean[G.V()];
        pre = new int[G.V()];

        for(int i=0;i<pre.length;i++)
            pre[i] = -1;

        dfs(s,s);

    }

    // 深度优先遍历
    private boolean dfs(int v,int parent){
        visited[v] = true;
        pre[v] = parent;

        if(v == t) return true;

        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w])
                if(dfs(w,v) ) return true;
        }

        return false;
    }

    // 从源到目标是否可以连接
    public boolean isConnectedTo(){
        return visited[t];
    }

    // 从源到目标的路径
    public Iterable<Integer> path(){
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo()) return res;

        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur]; // 上一个节点
        }
        res.add(s);
        Collections.reverse(res);   // 反
        return res;
    }


    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        Path path = new Path(g,0,6);
        System.out.println(path.path());
    }
}
