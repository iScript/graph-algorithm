// 有向图的环检测
public class DirectedCycleDetection {
    private Graph G;
    private boolean[] visited;
    private boolean[] onPath;   //是否在当前搜索路径上
    private boolean hasCycle = false;

    public DirectedCycleDetection(Graph G){
        if(!G.isDirected())
            throw new IllegalArgumentException("只支持有向图");

        this.G = G;
        visited = new boolean[G.V()];
        onPath = new boolean[G.V()];

        // 多包一层for，防止有多个联通分量
        for(int v = 0 ;v<G.V();v++){
            if(!visited[v])
                if(dfs(v,v)) {
                    hasCycle = true;break;
                }

        }
    }

    // 从顶点v开始，判断图中是否有环
    private boolean dfs(int v,int parent){
        visited[v] = true;
        onPath[v] = true;

        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w]) {
                if (dfs(w, v)) return true;           //
            }else if( onPath[w])    // 判断是否有环：不是父级且在搜索路径上
                    return true;
        }

        onPath[v] = false;

        return false;
    }

    // 是否有环
    public boolean hasCycle(){
        return hasCycle;
    }


    public static void main(String[] args){


        Graph g2 = new Graph("g8.txt",true);
        DirectedCycleDetection cycleDetection2 = new DirectedCycleDetection(g2);
        System.out.println(cycleDetection2.hasCycle());

    }
}
