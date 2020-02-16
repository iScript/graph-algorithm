import java.util.Arrays;

// 单源最短路径的Dijkstra算法（从源点到其他节点的最短路径
// 不能处理权重为负数
public class Dijkstra {
    private WeightedGraph G;
    private int s; //源点
    private int[] dis ; // 原点到各个顶点的距离
    private boolean[] visited;

    public Dijkstra(WeightedGraph G,int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;

        dis = new int[G.V()];
        Arrays.fill(dis,Integer.MAX_VALUE); // 初始化路径最大
        dis[0] = 0 ; //源点

        visited = new boolean[G.V()];

        // Dijkstra
        while(true){
            int curdis = Integer.MAX_VALUE; //当前找到的最小距离值
            int cur = -1;   //当前点
            System.out.println(curdis);

            // 寻找最小值
            // 如第一次循环，寻找到0，其他都是等于Integer.MAX_VALUE
            // 第二次循环，0已经visited ， 寻找他们的最小值，等等。
            // 如第二次找到weight2 4，那么0到weight为2的顶点肯定是最小路径，因为0经过其他点回到2会经过4
            // 下面同理，以weight2这个顶点开始找下面的。。
            for(int v=0;v<G.V();v++){
                if(!visited[v] && dis[v] < curdis){

                    curdis = dis[v];    //
                    cur = v;
                }
            }
            if(cur == -1) break;

            visited[cur] = true;

            // 当前点遍历相邻，如第一次从0开始
            for(int w:G.adj(cur)){
                 if(!visited[w]){
                     // 从cur到w的长度 小于w的长度 （dis默认是无限大）
                     // 如将无限大更新成实际值
                     // 逐步更新dis
                     if(dis[cur] + G.getWeight(cur,w) < dis[w]  ){
                         dis[w] = dis[cur] + G.getWeight(cur,w);

                     }
                 }

            }

        }
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return visited[v];
    }

    public int disTo(int v){
        G.validateVertex(v);
        return dis[v];
    }

    public static void main(String[] args){

        WeightedGraph g = new WeightedGraph("g7.txt");
        Dijkstra d = new Dijkstra(g,0);
        System.out.println(d.disTo(3));
    }
}
