package com.tool.coordinate.entity;

/**
 * 顶点信息
 * @author yk
 * @version 1.0
 * @apiNote:
 * @date 2021/9/17 22:34
 */
public class VertexInfo {

    // 访问标记
    private Boolean isVisited;
    // 顶点索引
    private int vertexIndex;
    // 顶点信息
    private CartesianPoint point;

    public VertexInfo(CartesianPoint point)
    {
        this.point = point;
        this.vertexIndex = point.getIndex() + 1;
        this.isVisited = false;
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public int getVertexIndex() {
        return vertexIndex;
    }

    public void setVertexIndex(int vertexIndex) {
        this.vertexIndex = vertexIndex;
    }

    public CartesianPoint getPoint() {
        return point;
    }

    public void setPoint(CartesianPoint point) {
        this.point = point;
    }

    // 标记为已访问
    public void flagVisited()
    {
        this.isVisited = true;
    }

    // 重置访问标记，重新标记为false
    public void remakeIsVisited()
    {
        this.isVisited = false;
    }

    /**
     * @apiNote   若要比较两个对象是否一致先调用该方法然后再单独比较【访问标记isVisited】是否一致
     * 不把isVisited直接放到该方法比较，是为了方便该类当HashMap的Key，因为isVisited在使用中会发生变更
     * @author yk
     * @date 2021/9/17 22:36
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass())
        {
            return false;
        }
        VertexInfo that = (VertexInfo) obj;
        return vertexIndex == that.vertexIndex &&
                this.getPoint().equals(that.getPoint());
    }

    @Override
    public int hashCode() {
        return this.vertexIndex;
    }
}
