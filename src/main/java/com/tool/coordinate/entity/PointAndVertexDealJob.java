package com.tool.coordinate.entity;

import com.tool.coordinate.entity.Enum.Direction;

import java.util.*;

/**
 * @author yk
 * @version 1.0
 * @apiNote:
 * @date 2021/9/17 22:32
 */
public class PointAndVertexDealJob {

    // X轴方向上坐标从小到大排序
    private List<CartesianPoint> xList;
    // Y轴方向上坐标从小到大排序
    private List<CartesianPoint> yList;
    // Z轴方向上坐标从小到大排序
    private List<CartesianPoint> zList;
    // X轴方向上坐标从小到大排序（X轴坐标无重复）
    private List<CartesianPoint> noRepeatXList;
    // Y轴方向上坐标从小到大排序（Y轴坐标无重复）
    private List<CartesianPoint> noRepeatYList;
    // Z轴方向上坐标从小到大排序（Z轴坐标无重复）
    private List<CartesianPoint> noRepeatZList;
    // 表头结点集合
    private List<VertexInfo> vertexInfoList = new ArrayList<VertexInfo>();

    /**
     *   每个组件顶点图的邻接表
     *   key - 表头结点
     *   value - 表结点（不包含表头结点）
     */
    private Map<VertexInfo, List<VertexInfo>> adjacencyTable = new HashMap<VertexInfo, List<VertexInfo>>();

    public PointAndVertexDealJob(AssemblyEntity assemblyEntity)
    {
        List<CartesianPoint> pointList = assemblyEntity.getPointList();
        /** 初始化表头结点集合 */
        for (CartesianPoint point : pointList)
        {
            this.vertexInfoList.add(new VertexInfo(point));
        }
        /** 创建邻接表 */
        this.createAdjacencyTable(this.vertexInfoList, assemblyEntity.getVertexList());

        this.xList = new ArrayList<CartesianPoint>(Arrays.asList(new CartesianPoint[pointList.size()]));
        this.yList = new ArrayList<CartesianPoint>(Arrays.asList(new CartesianPoint[pointList.size()]));
        this.zList = new ArrayList<CartesianPoint>(Arrays.asList(new CartesianPoint[pointList.size()]));
        Collections.copy(this.xList, pointList);
        Collections.copy(this.yList, pointList);
        Collections.copy(this.zList, pointList);
        /** 排序 */
        this.xList = this.sort(xList, Direction.DIR_X);
        this.yList = this.sort(yList, Direction.DIR_Y);
        this.zList = this.sort(zList, Direction.DIR_Z);
        /** 对排完序的集合去重 */
        this.noRepeatXList = this.dealRepeat(new ArrayList<CartesianPoint>(), xList, Direction.DIR_X, 0);
        this.noRepeatYList = this.dealRepeat(new ArrayList<CartesianPoint>(), yList, Direction.DIR_Y, 0);
        this.noRepeatZList = this.dealRepeat(new ArrayList<CartesianPoint>(), zList, Direction.DIR_Z, 0);
    }

    /**
     * @apiNote   创建邻接表
     * @author yk
     * @date 2021/9/17 22:53
     * @param vertices
     * @param polyVertexList
     * @return
     */
    private void createAdjacencyTable(List<VertexInfo> vertices, List<PolyVertex> polyVertexList)
    {
        for (VertexInfo vertice : vertices)
        {
            /** 表结点集合 */
            List<VertexInfo> headVertexList = new ArrayList<VertexInfo>();
            for (PolyVertex polyVertex : polyVertexList)
            {
                int nextVertexIndex = this.getNextVertexIndex(vertice.getVertexIndex(), polyVertex);
                if (nextVertexIndex != -1)
                {
                    headVertexList.add(vertices.get(nextVertexIndex - 1));
                }
            }
            this.adjacencyTable.put(vertice, headVertexList);
        }
    }

    /**
     * @apiNote   获取与给出顶点索引相连的下一个顶点索引
     * @author yk
     * @date 2021/9/17 23:10
     * @param vertexIndex
     * @param polyVertex
     * @return
     */
    private int getNextVertexIndex(int vertexIndex, PolyVertex polyVertex)
    {
        int vertexIndex1 = -1;
        int polyFaceMeshVertex0 = polyVertex.getPolyFaceMeshVertex0();
        int polyFaceMeshVertex1 = polyVertex.getPolyFaceMeshVertex1();
        int polyFaceMeshVertex2 = polyVertex.getPolyFaceMeshVertex2();
        int polyFaceMeshVertex3 = polyVertex.getPolyFaceMeshVertex3();
        if (vertexIndex == polyFaceMeshVertex0
                && polyVertex.IsPolyFaceEdge0Visible())
        {
            vertexIndex1 = polyFaceMeshVertex1;
        }
        else if (vertexIndex == polyFaceMeshVertex1
                && polyVertex.IsPolyFaceEdge1Visible())
        {
            vertexIndex1 = polyFaceMeshVertex2;
        }
        else if (vertexIndex == polyFaceMeshVertex2
                && polyVertex.IsPolyFaceEdge2Visible())
        {
            vertexIndex1 = polyFaceMeshVertex0;
        }
        else if (vertexIndex == polyFaceMeshVertex3
                && polyVertex.IsPolyFaceEdge3Visible())
        {
            vertexIndex1 = polyFaceMeshVertex0;
        }
        return vertexIndex1;
    }

    /**
     * @apiNote   去除相同坐标轴上重复的点
     * @author yk
     * @date 2021/9/17 23:11
     * @param dest
     * @param src
     * @param direction
     * @param iCyc
     * @return
     */
    private List<CartesianPoint> dealRepeat(List<CartesianPoint> dest, List<CartesianPoint> src, Direction direction, int iCyc)
    {
        if (iCyc < src.size())
        {
            boolean flag = true;
            for (int lCyc = 0, len = dest.size(); lCyc < len; lCyc++)
            {
                if (dest.get(lCyc).equalsByDir(src.get(iCyc), direction))
                {
                    flag = false;
                    break;
                }
            }
            if (flag)
            {
                dest.add(src.get(iCyc));
            }
            return this.dealRepeat(dest, src, direction, ++iCyc);
        }
        return dest;
    }

    /**
     * @apiNote   冒泡排序 -- 排序结果从小到大
     * @author yk
     * @date 2021/9/17 23:12
     * @param params
     * @param direction
     * @return
     */
    private List<CartesianPoint> sort(List<CartesianPoint> params, Direction direction)
    {
        CartesianPoint myPoint = null;
        for(int iCyc=0,len=params.size(); iCyc<len-1; iCyc++)
        {
            for(int lCyc=len-1; lCyc>iCyc; lCyc--)
            {
                CartesianPoint myPoint1 = params.get(lCyc - 1);
                CartesianPoint myPoint2 = params.get(lCyc);
                CartesianPoint myPoint3 = myPoint1.compareToMax(myPoint2, direction);
                if(myPoint1.equals(myPoint3))
                {
                    myPoint = myPoint1;
                    params.set(lCyc-1, myPoint2);
                    params.set(lCyc, myPoint);
                }
            }
        }
        return params;
    }

    public List<CartesianPoint> getxList() {
        return xList;
    }

    public List<CartesianPoint> getyList() {
        return yList;
    }

    public List<CartesianPoint> getzList() {
        return zList;
    }

    public List<CartesianPoint> getNoRepeatXList() {
        return noRepeatXList;
    }

    public List<CartesianPoint> getNoRepeatYList() {
        return noRepeatYList;
    }

    public List<CartesianPoint> getNoRepeatZList() {
        return noRepeatZList;
    }

    public List<VertexInfo> getVertexInfoList() {
        return vertexInfoList;
    }

    public Map<VertexInfo, List<VertexInfo>> getAdjacencyTable() {
        return adjacencyTable;
    }
}
