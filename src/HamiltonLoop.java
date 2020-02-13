import java.util.ArrayList;
import java.util.Collections;

public class HamiltonLoop {
    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private int end;

    public HamiltonLoop(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        end = -1;

        dfs(0,0);
    }

    // 深度优先遍历
    private boolean dfs(int v,int parent){
        visited[v] = true;
        pre[v] = parent;

        // 遍历相邻节点
        for(int w : G.adj(v)){
            if(!visited[w]){
                if(dfs(w,v)) return true;
            }
            else if(w == 0 && allVisited()){    // 如果回到起始点0并且所有的点都被访问过了，则找到了哈密尔回路
                end = v;
                return true;
            }

        }

        // 回溯
        visited[v] = false;
        return false;


    }

    private boolean allVisited(){
        for(int v = 0;v<G.V();v++){
            if(!visited[v]) return false;
        }
        return true;
    }

    private ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(end == -1) return res;

        int cur = end;
        while(cur != 0){
            res.add(cur);
            cur = pre[cur]; // 上一个节点
        }
        res.add(0);
        Collections.reverse(res);   // 反
        return res;
    }

    public static void main(String[] args){

        Graph g = new Graph("g4.txt");
        HamiltonLoop hamiltonLoop = new HamiltonLoop(g);
        System.out.println(hamiltonLoop.result());
    }
}
