import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

//拓扑排序
public class TopoSort {

    private Graph G;
    private ArrayList<Integer> res;
    private boolean hasCycle = false;

    public TopoSort(Graph G){
        if(!G.isDirected())
            throw new IllegalArgumentException("只支持有向图");

        res = new ArrayList<>();

        int[] indegrees = new int[G.V()];
        Queue<Integer> q = new LinkedList<>();  // 队列

        for(int v = 0; v < G.V(); v ++){
            indegrees[v] = G.indegree(v);   // 每个顶点的入度值
            if(indegrees[v] == 0) q.add(v); // 如果入度是0，第一个
        }

        while(!q.isEmpty()){
            int cur = q.remove(); //队列中取中一个
            res.add(cur);   // 排进去

            for(int next: G.adj(cur)){  // 有向图的下一个
                indegrees[next] --;     //入度减
                if(indegrees[next] == 0) q.add(next);
            }
        }

        // 判断是否有环，有环不能拓扑排序(无环的话，所有元素都会add进去，等于
        if(res.size() != G.V()){
            hasCycle = true;
            res.clear();
        }

    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public ArrayList<Integer> result(){
        return res;
    }

    public static void main(String[] args){

        Graph g = new Graph("g8.txt", true);
        TopoSort topoSort = new TopoSort(g);
        System.out.println(topoSort.result());
    }

}
