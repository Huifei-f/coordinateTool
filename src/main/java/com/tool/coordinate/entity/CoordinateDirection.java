package com.tool.coordinate.entity;

import com.tool.coordinate.entity.Enum.Direction;

/**
 * @author yk
 * @version 1.0
 * @apiNote:
 * @date 2021/9/20 17:44
 */
public class CoordinateDirection {

    private double x;

    private double y;

    private double z;

    public CoordinateDirection(Direction dir) {
        switch (dir)
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
    }

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
}
