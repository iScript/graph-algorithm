import java.util.Arrays;

public class BellmanFord {
    private WeightedGraph G;
    private int s;
    private int[] dis;
    private boolean hasNegCycle = false;    // 是否包含负权环

    public BellmanFord(WeightedGraph G, int s){

        this.G = G;

        G.validateVertex(s);
        this.s = s;

        dis = new int[G.V()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[s] = 0;

        // 进行v-1轮循环
        for(int pass = 1; pass < G.V(); pass ++){
            //对所有的边进行松弛操作
            for(int v = 0; v < G.V(); v ++)
                for(int w: G.adj(v))
                    // 对这条边 从原点到v+ v到w的权重，小于dis[w] ,更新dis[w]
                    if(dis[v] != Integer.MAX_VALUE && dis[v] + G.getWeight(v, w) < dis[w])
                        dis[w] = dis[v] + G.getWeight(v, w);
        }

        for(int v = 0; v < G.V(); v ++)
            for(int w : G.adj(v))
                if(dis[v] != Integer.MAX_VALUE && dis[v] + G.getWeight(v, w) < dis[w])
                    hasNegCycle = true;

    }

    public boolean hasNegativeCycle(){
        return hasNegCycle;
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return dis[v] != Integer.MAX_VALUE;
    }

    public int distTo(int v){
        G.validateVertex(v);
        if(hasNegCycle) throw new RuntimeException("exist negative cycle.");
        return dis[v];
    }

    static public void main(String[] args){

        WeightedGraph g = new WeightedGraph("g7.txt");
        BellmanFord bf = new BellmanFord(g, 0);
        if(!bf.hasNegativeCycle()){
            for(int v = 0; v < g.V(); v ++)
                System.out.print(bf.distTo(v) + " ");
            System.out.println();
        }
        else
            System.out.println("exist negative cycle.");


    }

}
