package com.tool.coordinate.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yk
 * @version 1.0
 * @apiNote:
 * @date 2021/9/16 16:48
 */
public class PolyVertex {

    private int polyFaceMeshVertex0;
    private int polyFaceMeshVertex1;
    private int polyFaceMeshVertex2;
    private int polyFaceMeshVertex3;

    public int getPolyFaceMeshVertex0() {
        return Math.abs(polyFaceMeshVertex0);
    }

    public void setPolyFaceMeshVertex0(int polyFaceMeshVertex0) {
        this.polyFaceMeshVertex0 = polyFaceMeshVertex0;
    }

    public int getPolyFaceMeshVertex1() {
        return Math.abs(polyFaceMeshVertex1);
    }

    public void setPolyFaceMeshVertex1(int polyFaceMeshVertex1) {
        this.polyFaceMeshVertex1 = polyFaceMeshVertex1;
    }

    public int getPolyFaceMeshVertex2() {
        return Math.abs(polyFaceMeshVertex2);
    }

    public void setPolyFaceMeshVertex2(int polyFaceMeshVertex2) {
        this.polyFaceMeshVertex2 = polyFaceMeshVertex2;
    }

    public int getPolyFaceMeshVertex3() {
        return Math.abs(polyFaceMeshVertex3);
    }

    public void setPolyFaceMeshVertex3(int polyFaceMeshVertex3) {
        this.polyFaceMeshVertex3 = polyFaceMeshVertex3;
    }

    public Boolean IsPolyFaceEdge0Visible()
    {
        return this.polyFaceMeshVertex0 > 0;
    }

    public Boolean IsPolyFaceEdge1Visible()
    {
        return this.polyFaceMeshVertex1 > 0;
    }

    public Boolean IsPolyFaceEdge2Visible()
    {
        return this.polyFaceMeshVertex2 > 0;
    }

    public Boolean IsPolyFaceEdge3Visible()
    {
        return this.polyFaceMeshVertex3 > 0;
    }

    /**
     * @apiNote   根据给出的折线对象，过滤并取消相同连线的线段组合(不分起始点)
     * @author yk
     * @date 2021/9/17 22:56
     * @param vertex -- 给出的折线对象
     * @return
     */
    public void FiltersRepeatPoly(PolyVertex vertex)
    {
        List<int[]> var1 = this.GetPolyLineList();
        List<int[]> var2 = vertex.GetPolyLineList();

        if (var1.size() > 0)
        {
            for (int[] temp1 : var1)
            {
                int var1_1 = temp1[0];
                int var1_2 = temp1[1];

                for (int[] temp2 : var2)
                {
                    int var2_1 = temp2[0];
                    int var2_2 = temp2[1];
                    if ((var1_1 == var2_1 && var1_2 == var2_2)
                            || (var1_1 == var2_2 && var1_2 == var2_1))
                    {
                        this.negation(var1_1);
                        vertex.negation(var2_1);
                        break;
                    }
                }
            }
        }
    }

    // 获取当前对象中所有的连线对象
    public List<int[]> GetPolyLineList()
    {
        List<int[]> rr = new ArrayList<int[]>();
        if (this.IsPolyFaceEdge0Visible())
        {
            int[] temp = {this.polyFaceMeshVertex0, this.polyFaceMeshVertex1};
            rr.add(temp);
        }

        if (this.IsPolyFaceEdge1Visible())
        {
            int[] temp = {this.polyFaceMeshVertex1, this.polyFaceMeshVertex1};
            rr.add(temp);
        }

        if (this.IsPolyFaceEdge2Visible())
        {
            int[] temp = {this.polyFaceMeshVertex2, this.polyFaceMeshVertex0};
            rr.add(temp);
        }
        return rr;
    }

    /**
     * @apiNote   取反操作
     * @author yk
     * @date 2021/9/17 23:09
     * @param polyIndex -- 折线顶点的索引
     * @return
     */
    private void negation(int polyIndex)
    {
        if (polyIndex == this.getPolyFaceMeshVertex0() && this.polyFaceMeshVertex0 > 0)
        {
            this.polyFaceMeshVertex0 = this.getPolyFaceMeshVertex0() * -1;
        }
        if (polyIndex == this.getPolyFaceMeshVertex1() && this.polyFaceMeshVertex1 > 0)
        {
            this.polyFaceMeshVertex1 = this.getPolyFaceMeshVertex1() * -1;
        }
        if (polyIndex == this.getPolyFaceMeshVertex2() && this.polyFaceMeshVertex2 > 0)
        {
            this.polyFaceMeshVertex2 = this.getPolyFaceMeshVertex2() * -1;
            this.polyFaceMeshVertex3 = this.getPolyFaceMeshVertex3() * -1;
        }
    }

    @Override
    public String toString() {
        return "PolyVertex{" +
                "polyFaceMeshVertex0=" + polyFaceMeshVertex0 +
                ", polyFaceMeshVertex1=" + polyFaceMeshVertex1 +
                ", polyFaceMeshVertex2=" + polyFaceMeshVertex2 +
                ", polyFaceMeshVertex3=" + polyFaceMeshVertex3 +
                '}';
    }
}
