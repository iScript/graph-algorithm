# 图论学习笔记

### 基本概念
图是什么？
图是由若干给定的顶点及连接两点的边所构成的图形。

其他相关概念：
* 度：指和该节点相关联的边的条数。
* 环：从一个顶点经过其他顶点回到原点。
* 桥：对于无向图，如果删除了一条边，整个图联通分量数量发生变化，则这条边称为桥。
* 割点：如果删除了一个顶点（顶点邻边也删除），整个图联通分量数量发生变化，则这个点称为割点。
* 联通分量：图中相互连接可以相互抵达的顶点集合。


### 图的分类
* 无向图、有向图（顶点之间的边是否有方向，如地铁图是无向图、社交关注有向图）
* 有权图、无权图（顶点之间的边是否有权重）
* 稀疏图、稠密图（平均每个节点的度/每个节点度的最大值）


### 图的表示
1. 邻接矩阵 [代码实现](https://github.com/iScript/graph-algorithm/blob/master/src/AdjacencyMatrix.java)
2. 邻接表 [代码实现](https://github.com/iScript/graph-algorithm/blob/master/src/AdjacencyList.java)

### 图的遍历
1. 深度优先遍历 [代码实现](https://github.com/iScript/graph-algorithm/blob/master/src/GraphDFS.java)
2. 广度优先遍历（在无权图中结果即是最短路径） [代码实现](https://github.com/iScript/graph-algorithm/blob/master/src/GraphBFS.java)

### 哈密尔顿回路和欧拉回路
哈密尔顿回路：从一个点出发，沿着边行走，经过每个顶点恰好一次，之后再回到出发点。
[通过深度优先遍历求解](https://github.com/iScript/graph-algorithm/blob/master/src/HamiltonLoop.java)

欧拉回路：从一个点出发，沿着边行走，经过每个边恰好一次，之后再回到出发点。
欧拉回路性质：想让每条边都走一边，回到原点，则每个点必须都有进有出。每个点相邻的边数，必须是偶数。说明[7桥问题](https://baike.baidu.com/item/%E4%B8%83%E6%A1%A5%E9%97%AE%E9%A2%98)无解。
[通过Hierholzer算法求解欧拉回路](https://github.com/iScript/graph-algorithm/blob/master/src/EulerLoop.java)

### 最小生成树
求出一个生成树，使边的权值和最小，又称最小权重生成树的简称。
实际应用：如电缆布线，让每个节点都通上点并且布线费用最低。

切分定理 ：
> 图中的顶点分为两部分，就称为切分。
> 如果一个边的两个端点，属于切分不同的两边，这个边成为横切边。
> 横切边中的最短边，一定属于最小生成树。

反证法证明切分定理：
1. 令三条横切边分别为 e、f、g，且 e 为权值最小的横切边，T 为图的最小生成树
2. 假设 T 包含 f，而不包含 e，那么如果将 e 加入 T，那么e，f 就必然形成了环，不再是生成树（因为最小生成树就是n个顶点一共有n-1条边，多加一条必然有环）
3. 显然，环中e 的权值小于 f，那么用 e 替换 f 就可以形成一个权值小于 T 的生成树，与 T 为最小生成树矛盾

Kruskal算法
每次选择一个最短边，如果这个边没有形成环，相当于对某一个切分，选择了最短横切边。
[Kruskal算法找最小生成树](https://github.com/iScript/graph-algorithm/blob/master/src/Kruskal.java)


Prim算法
操作切分，首先从1：v-1开始，每次找当前切分的最短横切边，逐步扩展切分，直到没有切分。
[Prim算法找最小生成树](https://github.com/iScript/graph-algorithm/blob/master/src/Prim.java)

### 最短路径算法
如从一个城市到另外一个城市经过哪些公路路径最短。

Dijkstra算法

每轮循环
1. 找到当前没有访问的顶点的最短路节点
2. 确认这个节点的最短路就是当前大小
3. 根据这个节点的最短路大小，更新其他节点的路径长度。

[Dijkstra算法实现](https://github.com/iScript/graph-algorithm/blob/master/src/Dijkstra.java)

最短路径算法Dijkstra算法最通用，但是不能处理负权边。
另外还有 [Bellman-Ford 算法](https://github.com/iScript/graph-algorithm/blob/master/src/BellmanFord.java) 和 Floyed算法


### 有向图
有向图的环检测
很多应用中需要确保我们的有向图是无环的，如程序模块的引用/任务调度/学习计划等
[深度优先遍历检测有向图环](https://github.com/iScript/graph-algorithm/blob/master/src/DirectedCycleDetection.java)

一般有向图主要处理 有向无环图，也叫 DAG （Directed Acyclic Graph）。

拓扑排序
是一个有向无环图（DAG, Directed Acyclic Graph）的所有顶点的线性序列。满足
1. 每个顶点出现且只出现一次。
2. 若存在一条从顶点 A 到顶点 B 的路径，那么在序列中顶点 A 出现在顶点 B 的前面。
[代码实现](https://github.com/iScript/graph-algorithm/blob/master/src/TopoSort.java)

### 网络流
一个有向图，有一个源点（入度为0），一个汇点（出度为0），边上有非负权值，表示容量。
最大流问题：从原点到汇点的最大流量是多少。

Edmonds-Karp算法