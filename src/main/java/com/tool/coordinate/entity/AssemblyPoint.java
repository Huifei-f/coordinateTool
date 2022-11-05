package com.tool.coordinate.entity;

import com.tool.coordinate.entity.Enum.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @apiNote: 组件点对象
 * @date 2021/9/17 22:24
 */
public class AssemblyPoint {

    //  最大X轴方向坐标
    private double max_x = Double.MIN_VALUE;
    //  最大Y轴方向坐标
    private double max_y = Double.MIN_VALUE;
    //  最大Z轴方向坐标
    private double max_z = Double.MIN_VALUE;
    //  最小X轴方向坐标
    private double min_x = Double.MAX_VALUE;
    //  最小Y轴方向坐标
    private double min_y = Double.MAX_VALUE;
    //  最小Z轴方向坐标
    private double min_z = Double.MAX_VALUE;
    //  组件的长
    private double length = 0.0D;
    //  组件的宽
    private double width = 0.0D;
    //  组件的高
    private double height = 0.0D;
    //  组件边界X轴方向上的长度
    private double XBoxLength = 0.0D;
    //  组件边界Y轴方向上的长度
    private double YBoxLength = 0.0D;
    //  组件边界Z轴方向上的长度
    private double ZBoxLength = 0.0D;
    //  组件的中心点对象
    private CartesianPoint centerPoint = null;
    //  中心线左端端点
    private CartesianPoint leftCenterPoint = null;
    //  中心线右端端点
    private CartesianPoint rightCenterPoint = null;
    //  最大Z轴坐标一致的点集合
    private List<CartesianPoint> maxZPointList = new ArrayList<CartesianPoint>();
    //  组件摆放方向
    private Direction dir;

    public List<CartesianPoint> getMaxZPointList() {
        return maxZPointList;
    }

    public CartesianPoint getLeftCenterPoint() {
        return leftCenterPoint;
    }

    public void setLeftCenterPoint(CartesianPoint leftCenterPoint) {
        this.leftCenterPoint = leftCenterPoint;
    }

    public CartesianPoint getRightCenterPoint() {
        return rightCenterPoint;
    }

    public void setRightCenterPoint(CartesianPoint rightCenterPoint) {
        this.rightCenterPoint = rightCenterPoint;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getXBoxLength() {
        return XBoxLength;
    }

    public void setXBoxLength(double XBoxLength) {
        this.XBoxLength = XBoxLength;
    }

    public double getYBoxLength() {
        return YBoxLength;
    }

    public void setYBoxLength(double YBoxLength) {
        this.YBoxLength = YBoxLength;
    }

    public double getZBoxLength() {
        return ZBoxLength;
    }

    public void setZBoxLength(double ZBoxLength) {
        this.ZBoxLength = ZBoxLength;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void setMaxZPointList(CartesianPoint point) {
        this.maxZPointList.add(point);
    }

    public double getMax_x() {
        return max_x;
    }

    public void setMax_x(double max_x) {
        this.max_x = Math.max(this.max_x, max_x);
    }

    public double getMin_x()
    {
        return min_x;
    }

    public void setMin_x(double min_x)
    {
        this.min_x = Math.min(this.min_x, min_x);
    }

    public double getMax_y()
    {
        return max_y;
    }

    public void setMax_y(double max_y)
    {
        this.max_y = Math.max(this.max_y, max_y);
    }

    public double getMin_y()
    {
        return min_y;
    }

    public void setMin_y(double min_y)
    {
        this.min_y = Math.min(this.min_y, min_y);
    }

    public double getMax_z()
    {
        return max_z;
    }

    public void setMax_z(double max_z)
    {
        this.max_z = Math.max(this.max_z, max_z);
    }

    public double getMin_z()
    {
        return min_z;
    }

    public void setMin_z(double min_z)
    {
        this.min_z = Math.min(this.min_z, min_z);
    }

    /**
     * @apiNote   获取中心点坐标
     * @author yk
     * @date 2021/9/17 22:31
     * @return
     */
    public CartesianPoint getCenterPoint()
    {
        if (this.centerPoint != null)
        {
            return this.centerPoint;
        }
        double centerX = (this.max_x + this.min_x) / 2;
        double centerY = (this.max_y + this.min_y) / 2;
        double centerZ = (this.max_z + this.min_z) / 2;
        this.centerPoint = new CartesianPoint(centerX, centerY, centerZ);
        return centerPoint;
    }

    /**
     * @apiNote   设置中心点
     * @author yk
     * @date 2021/9/17 22:30
     * @param centerPoint
     * @return
     */
    public void setCenterPoint(CartesianPoint centerPoint)
    {
        this.centerPoint = centerPoint;
    }
}
