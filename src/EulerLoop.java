import java.util.ArrayList;
import java.util.Stack;

// 欧拉回路
public class EulerLoop {
    private Graph G;

    public EulerLoop(Graph G){
        this.G = G ;
    }

    // 判断是否有欧拉回路
    public boolean hasEulerLoop(){
        // 必须是联通图
        CC cc = new CC(G);
        if(cc.count() > 1) return false;

        // 只需判读图中所有顶点的度数都是偶数
        for(int v = 0; v < G.V(); v ++)
            if(G.degree(v) % 2 == 1) return false;
        return true;
    }

    // Hierholzer算法
    public ArrayList<Integer> result(){

        ArrayList<Integer> res = new ArrayList<>();
        if(!hasEulerLoop()) return res;

        Graph g = (Graph)G.clone();

        Stack<Integer> stack = new Stack<>();

        int curv = 0;   // 当前顶点
        stack.push(curv);

        while(!stack.isEmpty()){
            // 如果度不为0，说明有路走
            if(g.degree(curv) != 0){
                stack.push(curv);
                //System.out.println(stack);
                int w = g.adj(curv).iterator().next();  // 随便取出一条边
                g.removeEdge(curv, w);      // 删除这条边
                curv = w;
            }
            else{   // 这条边走到底了，说明找到了一个环
                res.add(curv);
                curv = stack.pop();
                // 回退，逐步回退的时候将这个环的点加到list，另回退的时候会有一个公共点，连接着另外的环，
                // 接着走这个另外的环，另外的环走完会回到公共点
                // 从公共点继续回退，形成路径
                // （欧拉回路性质，每个点度数是偶数，相当于几个连接的环组成，通过走环及环的公共点）
            }
        }
        return res;
    }

    public static void main(String[] args){

        Graph g = new Graph("g5.txt");
        EulerLoop eulerLoop = new EulerLoop(g);
        System.out.println(eulerLoop.result());
    }
}
