
//二分图检测
public class BipartitionDetection {
    private Graph G;
    private boolean[] visited;
    private int[] colors;
    private boolean isBipartite = true;

    public BipartitionDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        colors = new int[G.V()];

        for(int i=0;i<G.V();i++) colors[i] = -1;

        // 多包一层for，防止有多个联通分量
        for(int v = 0 ;v<G.V();v++){
            if(!visited[v])
                if(!dfs(v,0)) {
                    isBipartite = false;break;
                }
        }
    }

    // 深度优先遍历
    private boolean dfs(int v,int color){
        visited[v] = true;
        colors[v] = color;
        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w]){
                if(!dfs(w,1-color)) return false;   // 遍历下一个点，染不同颜色
            }
            else if(colors[w] == colors[v]) return false;   // 如果相邻被访问过且相等，则是同一颜色，不是二分图。二分图边的两点隶属于不同的2部分
        }
        return true;
    }

    public boolean isBipartite(){
        return isBipartite;
    }


    public static void main(String[] args){

        Graph g = new Graph("g2.txt");
        BipartitionDetection bipartitionDetection = new BipartitionDetection(g);
        System.out.println(bipartitionDetection.isBipartite());
    }
}
