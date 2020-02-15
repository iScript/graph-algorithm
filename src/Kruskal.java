import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Kruskal(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<WeightedEdge>();

        // 需要判断联通

        // Kruskal
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        for(int v = 0;v<G.V();v++){
            for(int w : G.adj(v)){
                if(v < w){//避免重复
                    //将边都加入
                    edges.add(new WeightedEdge(v,w,G.getWeight(v,w)));
                }
            }
        }

        // 排序 从小到大
        Collections.sort(edges);

        // 使用并查集判断是否形成环，若形成环在集合中顶点会有重复
        UF uf = new UF(G.V());
        for(WeightedEdge edge: edges){
            int v = edge.getV();
            int w = edge.getW();
            if(!uf.isConnected(v, w)){  // v w 是否在一个集合中
                mst.add(edge);
                uf.unionElements(v, w); // 合并
            }
        }

    }

    public ArrayList<WeightedEdge> result(){
        return mst;
    }

    public static void main(String[] args){

        WeightedGraph g = new WeightedGraph("g.txt");
        Kruskal kruskal = new Kruskal(g);
        System.out.println(kruskal.result());
    }
}
