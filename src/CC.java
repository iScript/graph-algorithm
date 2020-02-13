import java.util.ArrayList;

// Connected Component 联通分量
public class CC {
    private Graph G;
    private int[] visited;
    private int cccount;

    public CC(Graph G){
        this.G = G;
        visited = new int[G.V()];
        for(int i=0;i<visited.length;i++) visited[i] = -1;

        // 多包一层for，防止有多个联通分量
        for(int v = 0 ;v<G.V();v++){
            if(visited[v] == -1 ){
                dfs(v,cccount);
                cccount++;
            }

        }
    }

    // 深度优先遍历
    private void dfs(int v,int ccid){
        visited[v] = ccid;
        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(visited[w] == -1 )
                dfs(w,ccid);
        }
    }

    // 返回几个联通分量
    public int count(){
        for(int e : visited){
            //System.out.print(e + " ");
        }

        return cccount;
    }

    // 2个顶点是否相连
    public boolean isConnected(int v,int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }

    // 返回联通分量数组
    public ArrayList<Integer>[] components(){
        ArrayList<Integer>[]  res = new ArrayList[cccount];
        for(int i=0;i<cccount;i++){
            res[i] = new ArrayList<>();
        }

        // 遍历每一个顶点
        for(int v=0;v<G.V();v++){
            // 放到各自的联通分量
            res[visited[v]].add(v);
        }

        return res;
    }


    public static void main(String[] args){

        Graph g = new Graph("g.txt");
        CC cc = new CC(g);
//        System.out.println(cc.count());
//        System.out.println(cc.isConnected(0,5));

        ArrayList<Integer>[] comp = cc.components();
        for(int ccid=0;ccid<comp.length;ccid++){
            System.out.print(ccid + " : ");
            for(int w:comp[ccid]){
                System.out.print(w + " ");
            }
            System.out.println();
        }

    }
}
