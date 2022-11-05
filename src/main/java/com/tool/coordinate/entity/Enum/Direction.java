package com.tool.coordinate.entity.Enum;

/**
 * @author yk
 * @desc 坐标轴
 * @date 2021/9/16 15:45
 * @return
 */
public enum Direction {

    DIR_X("DIR_X", "X轴"),
    DIR_Y("DIR_Y", "Y轴"),
    DIR_Z("DIR_Z", "Z轴"),
    INTERSECT("INTERSECT", "交叉"),
    NONE("NONE", "无方向");

    private String value;
    private String name;

    Direction(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}