import java.util.ArrayList;

public class Prim {

    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Prim(WeightedGraph G){

        this.G = G;
        mst = new ArrayList<WeightedEdge>();

        // 需要判断联通
        // ...

        // Prim 算法
        boolean[] visited = new boolean[G.V()];
        visited[0] = true;  // 随便切一个值
        for(int i=1;i<G.V();i++){
            WeightedEdge minEdge = new WeightedEdge(-1,-1,Integer.MAX_VALUE);
            // 每次扫描图中的所有边，找到最小切分
            for(int v=0;v<G.V();v++){
                if(visited[v] == true){ // v是true,如果下面的w是false则组成横切边
                    for(int w:G.adj(v)){
                        if(!visited[w] && G.getWeight(v,w) < minEdge.getWeight() ){
                            minEdge = new WeightedEdge(v,w,G.getWeight(v,w));
                        }
                    }
                }
            }

            mst.add(minEdge);
            // 扩充切分
            visited[minEdge.getV()] = true;
            visited[minEdge.getW()] = true;

        }


    }

    public ArrayList<WeightedEdge> result(){
        return mst;
    }

    public static void main(String[] args){

        WeightedGraph g = new WeightedGraph("g6.txt");
        Prim prim = new Prim(g);
        System.out.println(prim.result());
    }
}
