import java.util.ArrayList;

// 寻找桥算法 （和环算法类似）
public class FindBridges {

    private Graph G;
    private boolean[] visited;

    private int ord[];  // 表示顶点的遍历顺序 ，如 ord[3] = 4 表示3这个点dfs顺序是4
    private int low[];  // dfs过程中，顶点v能到达的最小order值，如low[3] = 0 表示顶点3另一条路的最小顶点为0。
    private int cnt;

    private ArrayList<Edge> res;

    public FindBridges(Graph G){

        this.G = G;
        visited = new boolean[G.V()];

        res = new ArrayList<>();
        ord = new int[G.V()];
        low = new int[G.V()];
        cnt = 0;

        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])
                dfs(v, v);
    }

    // 判断v-w是否是桥，通过w能否从另外一条路回到v或者v之前的节点

    private void dfs(int v, int parent){

        visited[v] = true;
        ord[v] = cnt;   //初始
        low[v] = ord[v];
        cnt ++;

        for(int w: G.adj(v))
            if(!visited[w]){
                dfs(w, v);
                // dfs结束 回退

                low[v] = Math.min(low[v], low[w]);  //更新low，对于vw这条边，如果low[w]比较小，说明他的下一节点w通过另一条路能返回，并经过v

                if(low[w] > ord[v]) // 判断是否是桥，vw这条边， 下一个相邻点的low比order小说明有另一条路，low比较大则没有路是桥（没有等于，即父节点）
                    res.add(new Edge(v, w));
            }
            else if(w != parent)    // 被访问过，并且不是父节点，则看这个点的low值，若low值较小，说明对于vw这条边，通过这个w能从另外一条路回到v。即便切断vw也可以。
                low[v] = Math.min(low[v], low[w]);  // 更新这个顶点的low值，即这个顶点除了父节点这条路，还有
    }

    public ArrayList<Edge> result(){
        return res;
    }

    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        FindBridges fb = new FindBridges(g);
        System.out.println("Bridges in g : " + fb.result());
//
//        Graph g2 = new Graph("g2.txt");
//        FindBridges fb2 = new FindBridges(g2);
//        System.out.println("Bridges in g2 : " + fb2.result());
//
//        Graph g3 = new Graph("g3.txt");
//        FindBridges fb3 = new FindBridges(g3);
//        System.out.println("Bridges in g3 : " + fb3.result());
//
//        Graph tree = new Graph("tree.txt");
//        FindBridges fb_tree = new FindBridges(tree);
//        System.out.println("Bridges in tree : " + fb_tree.result());
    }
}
