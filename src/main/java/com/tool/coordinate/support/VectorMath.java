package com.tool.coordinate.support;

import com.tool.coordinate.entity.CartesianPoint;
import com.tool.coordinate.entity.Coordinate3D;
import com.tool.coordinate.entity.Enum.Direction;
import org.apache.commons.math.util.MathUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author yk
 * @version 1.0
 * @description:
 * @date 2021/9/20 20:38
 */
public class VectorMath {

    /**
     * @apiNote  通过向量的起始坐标和终点坐标计算模长
     * @author yk
     * @date 2021/9/20 21:18
     * @param pStartPoint   -- 起始点位
     * @param pEndPont      -- 结束点位
     * @param precision     -- 精度
     * @return
     */
    public static double mathDieLength(CartesianPoint pStartPoint, CartesianPoint pEndPont, int precision)
    {
        double xLength = pStartPoint.get2PointLength(pEndPont, Direction.DIR_X, precision);
        double yLength = pStartPoint.get2PointLength(pEndPont, Direction.DIR_Y, precision);
        double zLength = pStartPoint.get2PointLength(pEndPont, Direction.DIR_Z, precision);

        double total = Math.pow(xLength, 2)
                + Math.pow(yLength, 2)
                + Math.pow(zLength, 2);
        return Math.sqrt(total);
    }

    /**
     * @apiNote  计算向量模长
     * @author yk
     * @date 2021/12/4 23:28
     * @param vector        --  向量
     * @param precision     -- 精度
     * @return
     */
    public static double mathDieLength(CartesianPoint vector, int precision)
    {
        CartesianPoint pEndPont = new CartesianPoint(0.0, 0.0, 0.0);
        double xLength = vector.get2PointLength(pEndPont, Direction.DIR_X, precision);
        double yLength = vector.get2PointLength(pEndPont, Direction.DIR_Y, precision);
        double zLength = vector.get2PointLength(pEndPont, Direction.DIR_Z, precision);

        int scale = (precision + "").length();
        double total = Math.pow(xLength, 2)
                + Math.pow(yLength, 2)
                + Math.pow(zLength, 2);
        return MathUtils.round(Math.sqrt(total), scale);
    }

    /**
     * @apiNote  通过向量的起始坐标和终点坐标计算模长保留精度
     * @author yk
     * @date 2021/9/20 21:17
     * @param pStartPoint
     * @param pEndPont
     * @param precision
     * @return
     */
    public static long mathDieLengthByPre(CartesianPoint pStartPoint, CartesianPoint pEndPont, int precision)
    {
        long xLength = pStartPoint.get2PointLengthByPre(pEndPont, Direction.DIR_X, precision);
        long yLength = pStartPoint.get2PointLengthByPre(pEndPont, Direction.DIR_Y, precision);
        long zLength = pStartPoint.get2PointLengthByPre(pEndPont, Direction.DIR_Z, precision);

        double total = new BigDecimal(Math.pow(xLength, 2))
                .add(new BigDecimal(Math.pow(yLength, 2)))
                .add(new BigDecimal(Math.pow(zLength, 2)))
                .doubleValue();
        long die = (long) Math.floor(Math.sqrt(total));
        return die;
    }

    /**
     * @apiNote    计算向量模长保留精度
     * @author yk
     * @date 2021/9/20 21:16
     * @param vector
     * @param precision
     * @return
     */
    public static long mathDieLengthByPre(CartesianPoint vector, int precision)
    {
        CartesianPoint pEndPont = new CartesianPoint(0.0, 0.0, 0.0);
        long xLength = vector.get2PointLengthByPre(pEndPont, Direction.DIR_X, precision);
        long yLength = vector.get2PointLengthByPre(pEndPont, Direction.DIR_Y, precision);
        long zLength = vector.get2PointLengthByPre(pEndPont, Direction.DIR_Z, precision);

        double total = new BigDecimal(Math.pow(xLength, 2))
                .add(new BigDecimal(Math.pow(yLength, 2)))
                .add(new BigDecimal(Math.pow(zLength, 2)))
                .doubleValue();
        long die = (long)Math.floor(Math.sqrt(total));
        return die;
    }

    /**
     * @apiNote   计算两个向量的内积保留精度
     * @author yk
     * @date 2021/9/20 21:15
     * @param aVector   -- a向量
     * @param bVector   -- b向量
     * @param precision -- 精度
     * @return
     */
    public static long mathVectorMultiply(CartesianPoint aVector, CartesianPoint bVector, int precision)
    {
        long total = (aVector.getXInt(precision) * bVector.getXInt(precision))
                + (aVector.getYInt(precision) * bVector.getYInt(precision))
                + (aVector.getZInt(precision) * bVector.getZInt(precision));
        return total;
    }

    /**
     * @apiNote 向量的数乘(返回一个新的对象)
     * @author yk
     * @date 2021/6/3 10:31
     * @param cartesianPoint    --  向量
     * @param num               --  数量
     * @param precision         --  精度
     * @return
     */
    public static CartesianPoint mathDieNumMult(CartesianPoint cartesianPoint, double num, int precision)
    {
        CartesianPoint point = new CartesianPoint();
        point.setX(
                new BigDecimal(Math.floor(num * precision))
                .multiply(new BigDecimal(cartesianPoint.getXInt(precision)))
                .divide(new BigDecimal(precision))
                .divide(new BigDecimal(precision)).doubleValue()
        );
        point.setY(
                new BigDecimal(Math.floor(num * precision))
                .multiply(new BigDecimal(cartesianPoint.getYInt(precision)))
                .divide(new BigDecimal(precision))
                .divide(new BigDecimal(precision)).doubleValue()
        );
        point.setZ(
                new BigDecimal(Math.floor(num * precision))
                .multiply(new BigDecimal(cartesianPoint.getZInt(precision)))
                .divide(new BigDecimal(precision))
                .divide(new BigDecimal(precision)).doubleValue()
        );
        return point;
    }

    /**
     * @apiNote 计算两个向量的外积（法向量）
     * @author yk
     * @date 2021/6/3 14:30
     * @param aVector   -- 向量A
     * @param bVector   -- 向量B
     * @param precision -- 精度
     * @return
     */
    public static CartesianPoint mathNormalVector(CartesianPoint aVector, CartesianPoint bVector, int precision)
    {
        CartesianPoint point = new CartesianPoint();
        Long[][] matrix3D = new Long[3][];
        matrix3D[0] = new Long[] { 1L, 1L, 1L};

        matrix3D[1] = new Long[] { aVector.getXInt(precision), aVector.getYInt(precision), aVector.getZInt(precision)};

        matrix3D[2] = new Long[] { bVector.getXInt(precision), bVector.getYInt(precision), bVector.getZInt(precision)};

        MatrixMath matrixMath = new MatrixMath();
        long x = matrixMath.getAlgebraCovalentFormula3DMatrix(matrix3D, 0, 0);
        long y = matrixMath.getAlgebraCovalentFormula3DMatrix(matrix3D, 0, 1);
        long z = matrixMath.getAlgebraCovalentFormula3DMatrix(matrix3D, 0, 2);
        point.setX(new BigDecimal(x).divide(new BigDecimal(Math.pow(precision, 2))).longValue());
        point.setY(new BigDecimal(y).divide(new BigDecimal(Math.pow(precision, 2))).longValue());
        point.setZ(new BigDecimal(z).divide(new BigDecimal(Math.pow(precision, 2))).longValue());
        point.setCoordinateID(aVector.getCoordinateID());
        return point;
    }

    /**
     * @apiNote 将相机坐标系中的点转换成世界坐标中坐标
     * @author yk
     * @date 2021/6/6 11:27
     * @param cartesianPoint    -- 相机坐标系中坐标点
     * @param worldCoordinate   -- 世界坐标系
     * @param localCoordinate   -- 相机坐标系
     * @param precision         -- 精度
     * @return
     */
    public static CartesianPoint coordinateTransformation(CartesianPoint cartesianPoint, Coordinate3D worldCoordinate, Coordinate3D localCoordinate, int precision)
    {
        if (cartesianPoint.getCoordinateID() != localCoordinate.getCoordinateID())
        {
            throw new RuntimeException("[ERROR:给出的点与坐标系不是相机坐标系中的点!!!]");
        }
        if (localCoordinate.getoPoint().getCoordinateID() != worldCoordinate.getCoordinateID())
        {
            throw new RuntimeException("[ERROR:相机坐标系原点不在世界坐标系中!!!]");
        }

        int scale = (precision+"").length();
        /** 世界坐标系原点对象 */
        CartesianPoint worldPoint = worldCoordinate.getoPoint();
        /** 相机坐标原点在世界坐标系中的坐标 */
        CartesianPoint localPoint = localCoordinate.getoPoint();
        double pWorldXPoint = (cartesianPoint.getX() * mathCosValue(worldCoordinate.getxAxis(), localCoordinate.getxAxis(), precision))
                + (cartesianPoint.getY() * mathCosValue(worldCoordinate.getxAxis(), localCoordinate.getyAxis(), precision))
                + (cartesianPoint.getZ() * mathCosValue(worldCoordinate.getxAxis(), localCoordinate.getzAxis(), precision));

        double pWorldYPoint = (cartesianPoint.getX() * mathCosValue(worldCoordinate.getyAxis(), localCoordinate.getxAxis(), precision))
                + (cartesianPoint.getY() * mathCosValue(worldCoordinate.getyAxis(), localCoordinate.getyAxis(), precision))
                + (cartesianPoint.getZ() * mathCosValue(worldCoordinate.getyAxis(), localCoordinate.getzAxis(), precision));

        double pWorldZPoint = (cartesianPoint.getX() * mathCosValue(worldCoordinate.getzAxis(), localCoordinate.getxAxis(), precision))
                + (cartesianPoint.getY() * mathCosValue(worldCoordinate.getzAxis(), localCoordinate.getyAxis(), precision))
                + (cartesianPoint.getZ() * mathCosValue(worldCoordinate.getzAxis(), localCoordinate.getzAxis(), precision));

        double xPoint = pWorldXPoint + localPoint.getX() - worldPoint.getX();
        double yPoint = pWorldYPoint + localPoint.getY() - worldPoint.getY();
        double zPoint = pWorldZPoint + localPoint.getZ() - worldPoint.getZ();
        /** 世界坐标系中该点的位置坐标 */
        CartesianPoint target = new CartesianPoint(MathUtils.round(xPoint, scale),
                MathUtils.round(yPoint, scale),
                MathUtils.round(zPoint, scale),
                worldCoordinate.getCoordinateID());
        return target;
    }

    /**
     * @apiNote   计算两个向量的COS(θ)值
     * @author yk
     * @date 2021/9/20 21:11
     * @param aVector   -- a向量
     * @param bVector   -- b向量
     * @param precision -- 精度
     * @return
     */
    public static double mathCosValue(CartesianPoint aVector, CartesianPoint bVector, int precision)
    {
        CartesianPoint o = new CartesianPoint(0.0, 0.0, 0.0);
        /** 计算A,B两个向量的模长 */
        long pADie = mathDieLengthByPre(aVector, o, precision);
        long pBDie = mathDieLengthByPre(bVector, o, precision);
        /** 计算两个向量的内积值 */
        long vectorMultiply = mathVectorMultiply(aVector, bVector, precision);

        /** 两个向量的内积值 / 两个向量的模长积 */
        int tPrecisionLen = (precision+"").length()+1;
        BigDecimal divide = new BigDecimal(vectorMultiply).divide(new BigDecimal(pADie).multiply(new BigDecimal(pBDie)),
                                                    tPrecisionLen,
                                                    RoundingMode.FLOOR);
        return divide.doubleValue();
    }

    /**
     * @apiNote   计算两个向量的SIN(θ)值
     * @author yk
     * @date 2021/9/20 20:39
     * @param aVector   -- a向量
     * @param bVector   -- b向量
     * @param precision -- 精度
     * @return
     */
    public static double mathSinValue(CartesianPoint aVector, CartesianPoint bVector, int precision)
    {
        double cosValue = mathCosValue(aVector, bVector, precision);
        //  通过floor将多余的小数位去除
        double cosValue2 = Math.pow(Math.floor(cosValue * precision), 2);

        double sinValue = Math.sqrt(Math.abs(cosValue2 - 1) / Math.pow(precision, 2));
        return sinValue;
    }

    /**
     * @apiNote   判断两个向量是否平行
     * @author yk
     * @date 2021/9/20 20:39
     * @param aVector   -- a向量
     * @param bVector   -- b向量
     * @param precision -- 精度
     * @return
     */
    public static Boolean mathVectorParallel(CartesianPoint aVector, CartesianPoint bVector, int precision)
    {
        double value = mathCosValue(aVector, bVector, precision);
        if (value == 1 || value == -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @apiNote 根据向量计算角度值(绝对值)
     * @author yk
     * @param aVector
     * @param bVector
     * @param precision
     * @param roundingMode
     * @return
     * @date 2021年11月5日 下午9:40:55
     */
    public static double mathAbsAngleValue(CartesianPoint aVector, CartesianPoint bVector, int precision, RoundingMode roundingMode)
    {
        double v = mathCosValue(aVector, bVector, precision);
        int tPrecisionLen = (precision+"").length()+1;
        BigDecimal rotateValue = new BigDecimal(Math.acos(v)).multiply(new BigDecimal(180)).divide(new BigDecimal(Math.PI), tPrecisionLen, roundingMode);
        return rotateValue.doubleValue();
    }

    /**
     * @apiNote 根据向量计算角度值(顺时针旋转为正角度 ， 逆时针旋转为负角度)
     *             <br/>从aVector转到bVector所需要的角度值
     * @author yk
     * @param aVector
     * @param bVector
     * @param precision
     * @param roundingMode
     * @return
     * @date 2021年11月6日 下午10:00:46
     */
    public static double mathAngleValue(CartesianPoint aVector, CartesianPoint bVector, int precision, RoundingMode roundingMode)
    {
        if(aVector.getZ() == bVector.getZ())
        {
            int prec = (precision+"").length();
            double v1 = Math.atan2(aVector.getY(), aVector.getX());
            double v2 = Math.atan2(bVector.getY(), bVector.getX());
            BigDecimal subtract = new BigDecimal(v1).subtract(new BigDecimal(v2));
            if(subtract.doubleValue()>Math.PI)
            {
                BigDecimal subtract1 = subtract.subtract(new BigDecimal(Math.PI).multiply(new BigDecimal(2)));
                BigDecimal pAngleValue = subtract1.multiply(new BigDecimal(180)).divide(new BigDecimal(Math.PI), prec, roundingMode);
                return pAngleValue.doubleValue();
            }
            else if(subtract.doubleValue()<-Math.PI)
            {
                BigDecimal add = subtract.add(new BigDecimal(Math.PI).multiply(new BigDecimal(2)));
                BigDecimal pAngleValue = add.multiply(new BigDecimal(180)).divide(new BigDecimal(Math.PI), prec, roundingMode);
                return pAngleValue.doubleValue();
            }
            else
            {
                BigDecimal pAngleValue = subtract.multiply(new BigDecimal(180)).divide(new BigDecimal(Math.PI), prec, roundingMode);
                return pAngleValue.doubleValue();
            }
        }
        else
        {
            throw new RuntimeException("当前两个向量不在同一等高处！");
        }
    }

    /**
     * @apiNote 向量旋转
     * @author yk
     * @date 2021/11/30 18:59
     * @param pVector       --  向量
     * @param pAIntValue    --  旋转角度(顺时针旋转为正角度，逆时针旋转为负角度)
     * @param scale         --  计算精度
     * @return
     */
    public static CartesianPoint mathRotate(CartesianPoint pVector, double pAIntValue, int scale)
    {
        CartesianPoint vector = new CartesianPoint();
        BigDecimal x1,y1;
        double l = Math.PI * Math.abs(pAIntValue) / 180;
        if(pAIntValue<0)
        {
            x1 = new BigDecimal(pVector.getX()).multiply(new BigDecimal(Math.cos(l)))
                    .subtract(new BigDecimal(pVector.getY()).multiply(new BigDecimal(Math.sin(l))));
            y1= new BigDecimal(pVector.getX()).multiply(new BigDecimal(Math.sin(l)))
                    .add(new BigDecimal(pVector.getY()).multiply(new BigDecimal(Math.cos(l))));
        }
        else
        {
            x1 = new BigDecimal(pVector.getX()).multiply(new BigDecimal(Math.cos(l)))
                    .add(new BigDecimal(pVector.getY()).multiply(new BigDecimal(Math.sin(l))));
            y1 = new BigDecimal(pVector.getY()).multiply(new BigDecimal(Math.cos(l)))
                    .subtract(new BigDecimal(pVector.getX()).multiply(new BigDecimal(Math.sin(l))));
        }
        vector.setX(MathUtils.round(x1.doubleValue(), scale));
        vector.setY(MathUtils.round(y1.doubleValue(), scale));
        vector.setZ(0);
        return vector;
    }
}
