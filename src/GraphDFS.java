import java.util.ArrayList;

public class GraphDFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> pre = new ArrayList<>(); //先序遍历结果
    private ArrayList<Integer> post = new ArrayList<>();//后序遍历结果

    public GraphDFS(Graph G){
        this.G = G;
        visited = new boolean[G.V()];

        // 多包一层for，防止有多个联通分量
        for(int v = 0 ;v<G.V();v++){
            if(!visited[v])
                dfs(v);
        }
    }

    // 深度优先遍历
    private void dfs(int v){
        visited[v] = true;
        pre.add(v);

        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w])
                dfs(w);
        }
        post.add(v);
    }

    public Iterable<Integer> pre(){
        return pre;
    }

    public Iterable<Integer> post(){
        return post;
    }

    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        GraphDFS graphDFS = new GraphDFS(g);
        System.out.println(graphDFS.pre());
        System.out.println(graphDFS.post());
    }
}
