package com.tool.coordinate.entity;

import com.tool.coordinate.entity.Enum.Direction;

import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @apiNote: 组件实体对象
 * @date 2021/9/16 15:35
 */
public class AssemblyEntity {

    //  唯一识别ID
    private String globalID;
    //  杆件号
    private String tag;
    //  索引(和相机坐标系的坐标ID是同一个)
    private int labelID;
    //  属于那个相机坐标系
    private Coordinate3D localCoordinate;
    //  点最大值与最小值以及中心点坐标对象
    private AssemblyPoint assemblyPoint;
    //  点集合
    private List<CartesianPoint> pointList;
    //  组件摆放方向
    private Direction direction;
    //  组件的各个面的信息（每个面由哪些顶点组成）
    private List<PolyVertex> vertexList;
    //  对组件的点排序并根据x,y,z轴坐标来分组
    private PointAndVertexDealJob pointAndVertexDealJob;

    public String getGlobalID() {
        return globalID;
    }

    public void setGlobalID(String globalID) {
        this.globalID = globalID;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLabelID() {
        return labelID;
    }

    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }

    public Coordinate3D getLocalCoordinate() {
        return localCoordinate;
    }

    public void setLocalCoordinate(Coordinate3D localCoordinate) {
        this.localCoordinate = localCoordinate;
    }

    public AssemblyPoint getAssemblyPoint() {
        return assemblyPoint;
    }

    public void setAssemblyPoint(AssemblyPoint assemblyPoint) {
        this.assemblyPoint = assemblyPoint;
    }

    public List<CartesianPoint> getPointList() {
        return pointList;
    }

    public void setPointList(List<CartesianPoint> pointList) {
        this.pointList = pointList;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<PolyVertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<PolyVertex> vertexList) {
        this.vertexList = vertexList;
    }

    public PointAndVertexDealJob getPointAndVertexDealJob() {
        return pointAndVertexDealJob;
    }

    public void setPointAndVertexDealJob(PointAndVertexDealJob pointAndVertexDealJob) {
        this.pointAndVertexDealJob = pointAndVertexDealJob;
    }

    @Override
    public String toString()
    {
        StringBuilder ss = new StringBuilder();
        ss.append("GlobalID:"+this.globalID);
        ss.append("\n");
        ss.append("Tag:"+this.tag);
        ss.append("\n");
        ss.append("LabelID:"+this.labelID);
        ss.append("\n");
        ss.append("Direction:" + direction.toString());
        ss.append("\n");
        ss.append("PointList:[");
        ss.append("\n");
        for (CartesianPoint point : this.pointList)
        {
            ss.append("\t" + point.toString());
            ss.append("\n");
        }
        ss.append("]");
        ss.append("\n");
        CartesianPoint cc = this.assemblyPoint.getCenterPoint();
        CartesianPoint ll = this.assemblyPoint.getLeftCenterPoint();
        CartesianPoint rr = this.assemblyPoint.getRightCenterPoint();
        List<CartesianPoint> mz = this.assemblyPoint.getMaxZPointList();
        ss.append("CenterPoint:" + cc.toString());
        ss.append("\n");
        if (ll != null)
        {
            ss.append("LeftPoint:" + ll.toString());
        }
        else
        {
            ss.append("LeftPoint:null");
        }
        ss.append("\n");
        if (rr != null)
        {
            ss.append("RightPoint:" + rr.toString());
        }
        else
        {
            ss.append("RightPoint:null");
        }
        ss.append("\n");
        ss.append("MaxZPointList:[");
        ss.append("\n");
        for (CartesianPoint point : mz)
        {
            ss.append("\t" + point.toString());
            ss.append("\n");
        }
        ss.append("]");
        ss.append("\n");
        ss.append("VertexMap:[");
        ss.append("\n");
        for (PolyVertex vertex : vertexList)
        {
            ss.append("\t" + vertex.toString());
            ss.append("\n");
        }
        ss.append("]");
        ss.append("\n");
        return ss.toString();
    }
}
