import java.util.*;

// 单源路径
public class SingleSourcePath {
    private Graph G;
    private int s;
    private boolean[] visited;
    private int[] pre;  //上一个点


    public SingleSourcePath(Graph G , int s){
        G.validateVertex(s);
        this.G = G;
        this.s = s; // 原点
        visited = new boolean[G.V()];
        pre = new int[G.V()];

        for(int i=0;i<pre.length;i++)
            pre[i] = -1;

        //dfs(s,s);
        bfs(s);
    }

    // 深度优先遍历
    private void dfs(int v,int parent){
        visited[v] = true;
        pre[v] = parent;
        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w])
                dfs(w,v);
        }
    }

    // 广度优先遍历
    private void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();  // LinkedList 实现了queue接口

        // 入队并标记访问
        queue.add(s);
        visited[s] = true;
        pre[s] = s;

        while(!queue.isEmpty()){
            int v = queue.remove(); //从队列取出元素
            for(int w:G.adj(v)){
                if(!visited[w]) {
                    // 入队并标记访问
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                }
            }
        }
    }

    // 从源到目标是否可以连接
    public boolean isConnectedTo(int t){
        G.validateVertex(t);
        return visited[t];
    }

    // 从源到目标的路径
    public Iterable<Integer> path(int t){
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo(t)) return res;

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
        SingleSourcePath singleSourcePath = new SingleSourcePath(g,0);
        System.out.println(singleSourcePath.path(6));
    }
}
