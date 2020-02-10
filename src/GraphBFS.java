import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

// 广度优先遍历
public class GraphBFS {
    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> order = new ArrayList<>(); //先序遍历结果

    public GraphBFS(Graph G){
        this.G = G;
        visited = new boolean[G.V()];

        // 多包一层for，防止有多个联通分量
        for(int v = 0 ;v<G.V();v++){
            if(!visited[v])
                bfs(v);
        }
    }

    // 广度优先遍历 , 路径同时是最短路径
    private void bfs(int s){
        Queue<Integer> queue = new LinkedList<>();  // LinkedList 实现了queue接口

        // 入队并标记访问
        queue.add(s);
        visited[s] = true;

        while(!queue.isEmpty()){
            int v = queue.remove(); //从队列取出元素
            order.add(v);

            for(int w:G.adj(v)){
                if(!visited[w]) {
                    // 入队并标记访问
                    queue.add(w);
                    visited[w] = true;
                }
            }
        }
    }

    public Iterable<Integer> order(){
        return order;
    }

    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        GraphBFS graphBFS = new GraphBFS(g);
        System.out.println(graphBFS.order());
    }

}
