import java.util.*;

// 最大流问题
public class MaxFlow {
    private WeightedGraph network;
    private WeightedGraph rG; // 残量图
    private int s,t;    //原点和汇点
    private int maxFlow;//流出来的最大流

    public MaxFlow(WeightedGraph network,int s,int t){

        if(!network.isDirected())
            throw new IllegalArgumentException("MaxFlow only works in directed graph.");

        //至少要有一个原点一个汇点
        if(network.V() < 2)
            throw new IllegalArgumentException("The network should has at least 2 vertices.");

        network.validateVertex(s);
        network.validateVertex(t);

        if(s == t)
            throw new IllegalArgumentException("s and t should be different.");

        this.network = network;
        this.s = s;
        this.t = t;

        // 创建残量图,没有任何边
        this.rG = new WeightedGraph(network.V(), true);
        //为残量图添加边
        for(int v = 0; v < network.V(); v ++)
            for(int w: network.adj(v)){
                int c = network.getWeight(v, w);
                rG.addEdge(v, w, c);        // 添加边，容量
                rG.addEdge(w, v, 0);    // 添加反向边
            }


        while(true){
            // 寻找增广路径
            ArrayList<Integer> augPath = getAugmentingPath();
            System.out.println(augPath);
            if(augPath.size() == 0) break;

            int f = Integer.MAX_VALUE;
            // TODO: 计算增广路径上的最小值
            for(int i = 1; i < augPath.size(); i ++) {
                int v = augPath.get(i - 1);
                int w = augPath.get(i);
                f = Math.min(f, rG.getWeight(v, w));
            }

            maxFlow += f;

            // TODO: 根据增广路径更新 rG
            for(int i = 1; i < augPath.size(); i ++){
                int v = augPath.get(i - 1);
                int w = augPath.get(i);

                rG.setWeight(v, w, rG.getWeight(v, w) - f);
                rG.setWeight(w, v, rG.getWeight(w, v) + f); // 反向边
            }
        }
    }

    // 寻找一条增广路径
    private ArrayList<Integer> getAugmentingPath(){
        Queue<Integer> q = new LinkedList<>();
        int[] pre = new int[network.V()];
        Arrays.fill(pre, -1);   // 默认-1 ，大于等于0则代表被遍历过
       // System.out.println(pre);
        q.add(s);
        pre[s] = s;

        // 广度优先，找从s到t的路径
        while(!q.isEmpty()){
            int cur = q.remove();
            if(cur == t) break;
            for(int next: rG.adj(cur))
                if(pre[next] == -1 && rG.getWeight(cur, next) > 0){
                    pre[next] = cur;
                    q.add(next);
                }
        }

        ArrayList<Integer> res = new ArrayList<>();
        if(pre[t] == -1) return res;    // 没找到路径

        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);

        Collections.reverse(res);
        return res;


    }

    public int result(){
        return maxFlow;
    }

    public int flow(int v, int w){

        if(!network.hasEdge(v, w))
            throw new IllegalArgumentException(String.format("No edge %d-%d", v, w));

        return rG.getWeight(w, v);
    }

    public static void main(String[] args){

        WeightedGraph network = new WeightedGraph("g9.txt", true);
        MaxFlow maxflow = new MaxFlow(network, 0, 3);
        System.out.println(maxflow.result());
        for(int v = 0; v < network.V(); v ++)
            for(int w: network.adj(v))
                System.out.println(String.format("%d-%d: %d / %d", v, w, maxflow.flow(v, w), network.getWeight(v, w)));

    }

}
