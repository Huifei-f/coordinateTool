package com.tool.coordinate.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @apiNote:    路径对象
 * @date 2021/9/20 17:46
 */
public class Point2PointPath {

    // 起始点
    private CartesianPoint startPoint;

    // 终点
    private CartesianPoint endPoint;

    // 路径集合
    private List<VertexInfo> path;

    // 路径数量
    private int pathCount;

    public Point2PointPath(LinkedList<VertexInfo> linkedList){
        if (linkedList == null || linkedList.size() == 0)
        {
            throw new RuntimeException("路径不能为空！！");
        }
        this.startPoint = linkedList.getFirst().getPoint();
        this.endPoint = linkedList.getLast().getPoint();
        this.pathCount = linkedList.size();
        this.path = new ArrayList<VertexInfo>();
        for (VertexInfo next : linkedList)
        {
            this.path.add(next);
        }
    }

    public CartesianPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(CartesianPoint startPoint) {
        this.startPoint = startPoint;
    }

    public CartesianPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(CartesianPoint endPoint) {
        this.endPoint = endPoint;
    }

    public List<VertexInfo> getPath() {
        return path;
    }

    public void setPath(List<VertexInfo> path) {
        this.path = path;
    }

    public int getPathCount() {
        return pathCount;
    }

    public void setPathCount(int pathCount) {
        this.pathCount = pathCount;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int iCyc = 0, len = path.size(); iCyc < len; iCyc++)
        {
            VertexInfo vertex = path.get(iCyc);
            str.append(vertex.getVertexIndex());
            if (!(iCyc == len - 1))
            {
                str.append("->");
            }
        }
        str.append("]");
        str.append("\n");
        return str.toString();
    }
}
