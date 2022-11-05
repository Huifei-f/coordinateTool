package com.tool.coordinate.entity;

/**
 * @author yk
 * @version 1.0
 * @apiNote: 3D坐标系对象
 * @date 2021/9/17 22:21
 */
public class Coordinate3D {

    private CartesianPoint xAxis;
    private CartesianPoint yAxis;
    private CartesianPoint zAxis;
    /**
     * 原点坐标
     */
    private CartesianPoint oPoint;
    /**
     * 坐标系识别ID(0/为自定义或未定义)
     */
    private int coordinateID;

    public Coordinate3D() {
    }

    public Coordinate3D(CartesianPoint xAxis, CartesianPoint yAxis, CartesianPoint zAxis, CartesianPoint oPoint, int coordinateID) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.oPoint = oPoint;
        this.coordinateID = coordinateID;
    }

    public static Coordinate3D DEFAULT_WORLDCOORDINATE()
    {
        Coordinate3D pCoordinate = new Coordinate3D(
                new CartesianPoint(1, 0, 0, 0),
                new CartesianPoint(0, 1, 0, 0),
                new CartesianPoint(0, 0, 1, 0),
                new CartesianPoint(0, 0, 0, 0),
                0
        );
        return pCoordinate;
    }

    public CartesianPoint getxAxis() {
        return xAxis;
    }

    public void setxAxis(CartesianPoint xAxis) {
        this.xAxis = xAxis;
    }

    public CartesianPoint getyAxis() {
        return yAxis;
    }

    public void setyAxis(CartesianPoint yAxis) {
        this.yAxis = yAxis;
    }

    public CartesianPoint getzAxis() {
        return zAxis;
    }

    public void setzAxis(CartesianPoint zAxis) {
        this.zAxis = zAxis;
    }

    public CartesianPoint getoPoint() {
        return oPoint;
    }

    public void setoPoint(CartesianPoint oPoint) {
        this.oPoint = oPoint;
    }

    public int getCoordinateID() {
        return coordinateID;
    }

    public void setCoordinateID(int coordinateID) {
        this.coordinateID = coordinateID;
    }

    @Override
    public String toString() {
        return "Coordinate3D{" +
                "xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", zAxis=" + zAxis +
                ", oPoint=" + oPoint +
                ", coordinateID=" + coordinateID +
                '}';
    }
}
