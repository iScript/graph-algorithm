import java.util.ArrayList;

public class CycleDetection {

    private Graph G;
    private boolean[] visited;
    private boolean hasCycle = false;

    public CycleDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];

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
        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w])
                if(dfs(w,v)) return true;           //
            else if(w != parent)    // 判断是否有环：一个相邻节点w被访问过并且 该相邻节点w不是当前节点v的上个节点，则说明有环
                return true;
        }
        return false;
    }

    // 是否有环
    public boolean hasCycle(){
        return hasCycle;
    }


    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        CycleDetection cycleDetection = new CycleDetection(g);
        System.out.println(cycleDetection.hasCycle());

    }
}
