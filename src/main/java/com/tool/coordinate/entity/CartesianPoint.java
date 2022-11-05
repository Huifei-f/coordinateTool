package com.tool.coordinate.entity;

import com.tool.coordinate.entity.Enum.Direction;

import java.math.BigDecimal;

/**
 * @author yk
 * @version 1.0
 * @description:
 * @date 2021/9/16 15:52
 */
public class CartesianPoint {

    private double x;
    private double y;
    private double z;
    /**
     * 坐标系识别ID(0/为自定义或未定义)
     * 该点属于哪个坐标系下的
     */
    private int coordinateID;
    //  点所在数组中的下标索引信息
    private int index;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getCoordinateID() {
        return coordinateID;
    }

    public void setCoordinateID(int coordinateID) {
        this.coordinateID = coordinateID;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CartesianPoint(double x, double y, double z, int coordinateID, int index) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.coordinateID = coordinateID;
        this.index = index;
    }

    public CartesianPoint(Direction pDirection, int coordinateID)
    {
        switch (pDirection)
        {
            case DIR_X:
                this.x = 1;
                this.y = 0;
                this.z = 0;
                break;
            case DIR_Y:
                this.x = 0;
                this.y = 1;
                this.z = 0;
                break;
            case DIR_Z:
                this.x = 0;
                this.y = 0;
                this.z = 1;
                break;
            default:
                break;
        }
        this.coordinateID = coordinateID;
    }

    public static CartesianPoint DEFAULT_VECTOR(Direction pDir)
    {
        CartesianPoint vector = null;
        switch(pDir)
        {
            case DIR_X:
                vector = new CartesianPoint(1, 0, 0, 0);
                break;
            case DIR_Y:
                vector = new CartesianPoint(0, 1, 0, 0);
                break;
            case DIR_Z:
                vector = new CartesianPoint(0, 0, 1, 0);
                break;
            default :
                break;
        }
        return vector;
    }

    public CartesianPoint(double x, double y, double z, int coordinateID) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.coordinateID = coordinateID;
    }

    public CartesianPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CartesianPoint() {
    }

    public long getXInt(int precision)
    {
        return (long) Math.floor(this.x * precision);
    }

    public long getYInt(int precision)
    {
        return (long) Math.floor(this.y * precision);
    }

    public long getZInt(int precision)
    {
        return (long) Math.floor(this.z * precision);
    }

    @Override
    public String toString() {
        return "CartesianPoint{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", coordinateID=" + coordinateID +
                ", index=" + index +
                '}';
    }

    /**
     * @apiNote   比较两个点对象并返回较大的点对象
     * @author yk
     * @date 2021/9/16 15:58
     * @param point
     * @param direction
     * @return 
     */
    public CartesianPoint compareToMax(CartesianPoint point, Direction direction)
    {
        CartesianPoint temp = null;
        switch (direction)
        {
            case DIR_X:
                temp = this.getX() < point.getX() ? point : this;
                break;
            case DIR_Y:
                temp = this.getY() < point.getY() ? point : this;
                break;
            case DIR_Z:
                temp = this.getZ() < point.getZ() ? point : this;
                break;
            default:
                throw new RuntimeException("[ERROR:未给出比较坐标轴方向!!!]");
        }
        return temp;
    }

    /**
     * @apiNote    比较两个点对象并返回较小的点对象
     * @author yk
     * @date 2021/9/16 15:59
     * @param point
     * @param direction
     * @return
     */
    public CartesianPoint compareToMin(CartesianPoint point, Direction direction)
    {
        CartesianPoint temp = null;
        switch (direction)
        {
            case DIR_X:
                temp = this.getX() > point.getX() ? point : this;
                break;
            case DIR_Y:
                temp = this.getY() > point.getY() ? point : this;
                break;
            case DIR_Z:
                temp = this.getZ() > point.getZ() ? point : this;
                break;
            default:
                throw new RuntimeException("[ERROR:未给出比较坐标轴方向!!!]");
        }
        return temp;
    }

    /**
     * @apiNote    判断单个方向上坐标点位置是否一致
     * @author yk
     * @date 2021/9/16 16:00
     * @param point
     * @param direction
     * @return
     */
    public Boolean equalsByDir(CartesianPoint point, Direction direction)
    {
        Boolean flag = false;
        double var1 = 0.000001D;
        switch (direction)
        {
            case DIR_X:
                flag = Math.abs(this.x - point.getX()) <= var1;
                break;
            case DIR_Y:
                flag = Math.abs(this.y - point.getY()) <= var1;
                break;
            case DIR_Z:
                flag = Math.abs(this.z - point.getZ()) <= var1;
                break;
            default:
                throw new RuntimeException("[ERROR:未给出比较坐标轴方向!!!]");
        }
        return flag;
    }

    /**
     * @apiNote    获取x,y,z方向上两点的坐标差值
     * @author yk
     * @date 2021/9/16 16:01
     * @param point
     * @param direction
     * @param precision
     * @return
     */
    public double get2PointLength(CartesianPoint point, Direction direction, int precision)
    {
        double result = 0.0D;
        switch (direction)
        {
            case DIR_X:
                long subtractX = this.getXInt(precision) - point.getXInt(precision);
                result = new BigDecimal(Math.abs(subtractX)).divide(new BigDecimal(precision)).doubleValue();
                break;
            case DIR_Y:
                long subtractY = this.getYInt(precision) - point.getYInt(precision);
                result = new BigDecimal(Math.abs(subtractY)).divide(new BigDecimal(precision)).doubleValue();
                break;
            case DIR_Z:
                long subtractZ = this.getZInt(precision) - point.getZInt(precision);
                result = new BigDecimal(Math.abs(subtractZ)).divide(new BigDecimal(precision)).doubleValue();
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @apiNote    获取x,y,z方向上两点的坐标差值保留精度
     * @author yk
     * @date 2021/9/16 16:17
     * @param point
     * @param direction
     * @param precision
     * @return
     */
    public long get2PointLengthByPre(CartesianPoint point, Direction direction, int precision)
    {
        long result = 0;
        switch (direction)
        {
            case DIR_X:
                result = this.getXInt(precision) - point.getXInt(precision);
                break;
            case DIR_Y:
                result = this.getYInt(precision) - point.getYInt(precision);
                break;
            case DIR_Z:
                result = this.getZInt(precision) - point.getZInt(precision);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * @apiNote   通过减去给出的点得到一个向量对象(该方法会获取一个新的对象)
     * @author yk
     * @date 2021/9/16 16:18
     * @param point
     * @return
     */
    public CartesianPoint getVector(CartesianPoint point)
    {
        if (point.coordinateID != this.coordinateID)
        {
            throw new RuntimeException("[ERROR:两点不在同一个坐标系中!!!]");
        }
        CartesianPoint newPoint = new CartesianPoint();
        newPoint.coordinateID = this.coordinateID;
        newPoint.x = this.x - point.x;
        newPoint.y = this.y - point.y;
        newPoint.z = this.z - point.z;
        return newPoint;
    }


    /**
     * @apiNote    根据坐标轴方向取反
     * @author yk
     * @date 2021/9/16 16:22
     * @param direction
     * @return
     */
    public CartesianPoint negationByDir(Direction direction)
    {
        CartesianPoint point = new CartesianPoint(this.x, this.y, this.z, this.coordinateID);
        switch (direction)
        {
            case DIR_X:
                point.setX(point.getX() * -1);
                break;
            case DIR_Y:
                point.setY(point.getY() * -1);
                break;
            case DIR_Z:
                point.setZ(point.getZ() * -1);
                break;
            default:
                break;
        }
        return point;
    }

    /**
     * @apiNote    坐标取反(返回一个新的对象)
     * @author yk
     * @date 2021/9/16 16:47
     * @return
     */
    public CartesianPoint negation()
    {
        return new CartesianPoint(this.x * -1, this.y * -1, this.z * -1, this.coordinateID);
    }
}
