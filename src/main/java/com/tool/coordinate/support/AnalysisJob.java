package com.tool.coordinate.support;

import com.tool.coordinate.entity.*;

import java.util.*;

/**
 * @author yk
 * @version 1.0
 * @description:
 * @date 2021/11/25 17:42
 */
public class AnalysisJob {

    /**
     * @apiNote   判断两点之间是否有连线
     * @author yk
     * @date 2021/11/25 17:44
     * @param point1
     * @param point2
     * @param vertex
     * @return
     */
    public boolean is2PointWithLine(CartesianPoint point1, CartesianPoint point2, PolyVertex vertex)
    {
        boolean flag = false;
        int polyFaceMeshVertex0 = vertex.IsPolyFaceEdge0Visible() ? vertex.getPolyFaceMeshVertex0() : vertex.getPolyFaceMeshVertex0() * -1;
        int polyFaceMeshVertex1 = vertex.IsPolyFaceEdge1Visible() ? vertex.getPolyFaceMeshVertex1() : vertex.getPolyFaceMeshVertex1() * -1;
        int polyFaceMeshVertex2 = vertex.IsPolyFaceEdge2Visible() ? vertex.getPolyFaceMeshVertex2() : vertex.getPolyFaceMeshVertex2() * -1;
        int polyFaceMeshVertex3 = vertex.IsPolyFaceEdge3Visible() ? vertex.getPolyFaceMeshVertex3() : vertex.getPolyFaceMeshVertex3() * -1;
        int[] arr = new int[]{
                polyFaceMeshVertex0,polyFaceMeshVertex1,polyFaceMeshVertex2,polyFaceMeshVertex3
        };
        int index1 = this.getVertexIndex(point1, vertex);
        int index2 = this.getVertexIndex(point2, vertex);
        if (index1 == -1 || index2 == -1)
        {
            return false;
        }
        int val1 = arr[index1];
        int val2 = arr[index2];
        int abs = Math.abs(index1 - index2);
        switch (abs)
        {
            case 1:
                if (index1 < index2)
                {
                    flag = val1 < 0 ? false : true;
                }
                else
                {
                    flag = val2 < 0 ? false : true;
                }
                break;
            case 3:
                if (index1 < index2)
                {
                    flag = val2 < 0 ? false : true;
                }
                else
                {
                    flag = val1 < 0 ? false : true;
                }
                break;
            case 2:
                if (index1==0 || index2==0)
                {
                    if (index1 < index2)
                    {
                        flag = val2 < 0 ? false : true;
                    }
                    else
                    {
                        flag = val1 < 0 ? false : true;
                    }
                }
                break;
            default:
                break;
        }
        return flag;
    }

    /**
     * @apiNote   获取点在多面网格索引中的角标
     * @author yk
     * @date 2021/11/25 17:44
     * @param point
     * @param vertex
     * @return
     */
    private int getVertexIndex(CartesianPoint point, PolyVertex vertex)
    {
        int vertexIndex = -1;
        int vertexIndex1 = point.getIndex() + 1;
        int polyFaceMeshVertex0 = vertex.getPolyFaceMeshVertex0();
        int polyFaceMeshVertex1 = vertex.getPolyFaceMeshVertex1();
        int polyFaceMeshVertex2 = vertex.getPolyFaceMeshVertex2();
        int polyFaceMeshVertex3 = vertex.getPolyFaceMeshVertex3();
        if (vertexIndex1 == polyFaceMeshVertex0)
        {
            vertexIndex = 0;
        }
        else if (vertexIndex1 == polyFaceMeshVertex1)
        {
            vertexIndex = 1;
        }
        else if (vertexIndex1 == polyFaceMeshVertex2)
        {
            vertexIndex = 2;
        }
        else if (vertexIndex1 == polyFaceMeshVertex3)
        {
            vertexIndex = 3;
        }
        return vertexIndex;
    }

    /**
     * @apiNote 判断两点是否同在一个网格顶点中
     * @param myPoint1
     * @param myPoint2
     * @param vertex
     * @return
     */
    public boolean is2PointInVertex(CartesianPoint myPoint1, CartesianPoint myPoint2, PolyVertex vertex)
    {
        boolean flag = false;
        boolean inVertex1 = this.isInVertex(myPoint1, vertex);
        boolean inVertex2 = this.isInVertex(myPoint2, vertex);
        if(inVertex2 && inVertex1) {
            flag = true;
        }
        return flag;
    }

    /**
     * @apiNote   判断一个点在不在当前多面网格中
     * @author yk
     * @date 2021/11/25 17:45
     * @param myPoint
     * @param vertex
     * @return
     */
    public boolean isInVertex(CartesianPoint myPoint, PolyVertex vertex)
    {
        boolean flag = false;
        int vertexIndex = myPoint.getIndex() + 1;
        int polyFaceMeshVertex0 = vertex.getPolyFaceMeshVertex0();
        int polyFaceMeshVertex1 = vertex.getPolyFaceMeshVertex1();
        int polyFaceMeshVertex2 = vertex.getPolyFaceMeshVertex2();
        int polyFaceMeshVertex3 = vertex.getPolyFaceMeshVertex3();
        if (vertexIndex == polyFaceMeshVertex0
                || vertexIndex == polyFaceMeshVertex1
                || vertexIndex == polyFaceMeshVertex2
                || vertexIndex == polyFaceMeshVertex3)
        {
            flag = true;
        }
        return flag;
    }

    /**
     * @apiNote   查找两点之间的最短路径(深度优先遍历)
     * [1]可以通过端点两边同时查找
     * [2]向上和向下同时查找
     * @author yk
     * @date 2021/11/25 17:51
     * @param startPoint        起点
     * @param endPoint          终点
     * @param assemblyEntity    组件对象
     * @return 返回的集合以相对较小的点为起始位置排序（从小到大）
     */
    public Point2PointPath deepFristSearch(CartesianPoint startPoint, CartesianPoint endPoint, AssemblyEntity assemblyEntity)
    {
        /** 防止两个点对象是同一个 */
        if (startPoint.equals(endPoint))
        {
            return null;
        }
        /** 判断这两点是否已经直接相连 */
        for (PolyVertex vertex : assemblyEntity.getVertexList())
        {
            boolean pointWithLine = this.is2PointWithLine(startPoint, endPoint, vertex);
            if (pointWithLine)
            {
                LinkedList<VertexInfo> linkedPath = new LinkedList<VertexInfo>();
                linkedPath.addLast(new VertexInfo(startPoint));
                linkedPath.addLast(new VertexInfo(endPoint));
                Point2PointPath path = new Point2PointPath(linkedPath);
                return path;
            }
        }

        /** 有向图，邻接表 */
        Map<VertexInfo, List<VertexInfo>> adjacencyTable = assemblyEntity.getPointAndVertexDealJob().getAdjacencyTable();
        /** 有向图，邻接表，表头结点集合 */
        List<VertexInfo> vertices = assemblyEntity.getPointAndVertexDealJob().getVertexInfoList();
        int vertexIndex1 = startPoint.getIndex();
        int vertexIndex2 = endPoint.getIndex();
        VertexInfo startVertex = vertices.get(vertexIndex1);
        VertexInfo endVertex = vertices.get(vertexIndex2);
        /** 所有可行路径 */
        List<Point2PointPath> point2PointPaths = this.loopAdjacencyTable(adjacencyTable, vertices, startVertex, endVertex);
        Point2PointPath minPath = null;
        int mixCount = -1;
        for (Point2PointPath point2PointPath : point2PointPaths)
        {
            int pathCount = point2PointPath.getPathCount();
            if (mixCount == -1)
            {
                mixCount = pathCount;
                minPath = point2PointPath;
            }
            else if (mixCount > pathCount)
            {
                minPath = point2PointPath;
                mixCount = pathCount;
            }
        }
        return minPath;
    }

    /**
     * @apiNote   循环遍历邻接表
     * @author yk
     * @date 2021/11/25 18:01
     * @param adjacencyTable    邻接表
     * @param vertexs           有向图，表头结点集合
     * @param startVertex       起始顶点
     * @param endVertex         结束顶点
     * @return
     */
    private List<Point2PointPath> loopAdjacencyTable(
            Map<VertexInfo, List<VertexInfo>> adjacencyTable,
            List<VertexInfo> vertexs,
            VertexInfo startVertex,
            VertexInfo endVertex)
    {
        /** 返回路径 */
        List<Point2PointPath> ALL_PATH = new ArrayList<Point2PointPath>();
        /** 起始顶点邻接表 */
        List<VertexInfo> startVertexAdj = adjacencyTable.get(startVertex);
        /** 将起始点标记为以访问，防止后续再遍历点 */
        startVertex.flagVisited();
        for (VertexInfo nextVertex : startVertexAdj)
        {
            /** 先把起始顶点邻接表中所有顶点标记为已访问状态 */
            this.allTrue(startVertexAdj);
            /** 单次访问的路径 */
            LinkedList<VertexInfo> ONCE_PATH_LINK = new LinkedList<VertexInfo>();
            /** 先把顶点放进去 */
            ONCE_PATH_LINK.addFirst(startVertex);
            /** 判断是否已经到达终点，如果紧跟着的下一个顶点就是终点，就没有必要在遍历其它路径，直接跳出循环即可 */
            if (nextVertex.equals(endVertex))
            {
                ONCE_PATH_LINK.addLast(nextVertex);
                /** 重置所有顶点访问记录 */
                this.allFalse(adjacencyTable);
                /** 保存路径 */
                ALL_PATH.add(new Point2PointPath(ONCE_PATH_LINK));
                break;
            }
            else
            {
                ONCE_PATH_LINK.addLast(nextVertex);
                nextVertex.flagVisited();
                this.checkAdjacencyTable(adjacencyTable, nextVertex, endVertex, ONCE_PATH_LINK, ALL_PATH);
                ONCE_PATH_LINK.removeLast();
            }
            /** 重置所有顶点访问记录 */
            this.allFalse(adjacencyTable);
        }
        return ALL_PATH;
    }

    /**
     * @apiNote   遍历邻接表中所有表结点（顶点）
     * @author yk
     * @date 2021/11/25 18:06
     * @param adjacencyTable    邻接表
     * @param nextVertex        下一个需要访问的顶点
     * @param endVertex         终点顶点
     * @param oncePath          本次遍历所走的路径
     * @param allPath           所有路径
     * @return
     */
    private void checkAdjacencyTable(
            Map<VertexInfo, List<VertexInfo>> adjacencyTable,
            VertexInfo nextVertex,
            VertexInfo endVertex,
            LinkedList<VertexInfo> oncePath,
            List<Point2PointPath> allPath)
    {
        List<VertexInfo> vertexList = adjacencyTable.get(nextVertex);
        for (VertexInfo vertex : vertexList)
        {
            /** 判断是否已经到达终点 */
            if (vertex.equals(endVertex))
            {
                //  保存终点
                oncePath.addLast(vertex);
                allPath.add(new Point2PointPath(oncePath));
                //  移除终点，因为可能还要去循环其他路径
                oncePath.removeLast();
                break;
            }
            else if (vertex.getVisited())
            {
                continue;
            }
            else
            {
                //  保存路径中走过的结点
                oncePath.addLast(vertex);
                //  标记为已访问状态
                vertex.flagVisited();
                this.checkAdjacencyTable(adjacencyTable, vertex, endVertex, oncePath, allPath);
                //  移除上一个结点并且重置访问标记
                oncePath.removeLast();
                //vertex.remakeIsVisited();
            }
        }
    }

    /**
     * @apiNote   全标记
     * @author yk
     * @date 2021/11/25 18:07
     * @param vertexs
     * @return
     */
    private void allTrue(List<VertexInfo> vertexs)
    {
        for (VertexInfo vertex : vertexs)
        {
            vertex.flagVisited();
        }
    }

    /**
     * @apiNote   全重置
     * @author yk
     * @date 2021/11/25 18:07
     * @param adjacencyTable
     * @return
     */
    private void allFalse(Map<VertexInfo, List<VertexInfo>> adjacencyTable)
    {
        adjacencyTable.forEach((vertexInfo, vertexInfos) -> {
            for (VertexInfo vertex : vertexInfos) {
                vertex.remakeIsVisited();
            }
        });
    }
}
