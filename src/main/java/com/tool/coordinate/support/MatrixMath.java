package com.tool.coordinate.support;

import com.tool.coordinate.entity.Enum.MatrixColumn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author yk
 * @version 1.0
 * @description:
 * @date 2021/9/20 19:06
 */
public class MatrixMath {

    /**
     * @apiNote   计算[2x2]矩阵的行列式
     * @author yk
     * @date 2021/4/15 18:33
     * @param arr -- 要计算的矩阵
     * @return
     */
    public long determinant2D(Long[][] arr)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            return (long)((arr[1][1] * arr[0][0]) - (arr[0][1] * arr[1][0]));
        }
        else
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
    }

    /**
     * @apiNote  计算[3x3]矩阵的行列式
     * @author yk
     * @date 2021年4月15日 18点31分
     * @param arr       --  要计算的矩阵
     * @param rowNo     --  起始行
     * @param columnNo  --  起始列
     * @param count     --  行列式的值
     * @return
     */
    public long determinant3D(Long[][] arr, int rowNo, int columnNo, long count)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            List<List<Long>> covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
            if (covalentFormulaMatrix.size() == 2)
            {
                if ((rowNo + columnNo) % 2 == 0)
                {
                    count = (count + (this.determinant2D(this.formatList2Array(covalentFormulaMatrix)) * arr[rowNo][columnNo]));
                }
                else
                {
                    count = (count + (this.determinant2D(this.formatList2Array(covalentFormulaMatrix)) * arr[rowNo][columnNo] * -1));
                }
                columnNo++;
            }
            if (columnNo >= column)
            {
                return count;
            }
            else
            {
                return determinant3D(arr, rowNo, columnNo, count);
            }
        }
        else
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
    }

    /**
     * @apiNote   计算[4x4]矩阵的行列式
     * @author yk
     * @date 2021/4/16 16:26
     * @param arr
     * @param rowNo
     * @param columnNo
     * @param count
     * @return
     */
    public long determinant4D(Long[][] arr, int rowNo, int columnNo, long count)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            List<List<Long>> covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
            if (covalentFormulaMatrix.size() == 3)
            {
                if ((rowNo + columnNo) % 2 == 0)
                {
                    count = (count + (this.determinant3D(this.formatList2Array(covalentFormulaMatrix), 0, 0, 0) * arr[rowNo][columnNo]));
                }
                else
                {
                    count = (count + (this.determinant3D(this.formatList2Array(covalentFormulaMatrix), 0, 0, 0) * arr[rowNo][columnNo] * -1));
                }
                columnNo++;
            }
            if (columnNo >= column)
            {
                return count;
            }
            else
            {
                return determinant4D(arr, rowNo, columnNo, count);
            }
        }
        else
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
    }

    /**
     * @apiNote   求矩阵的余子式
     * @author yk
     * @date 2021/4/15 18:34
     * @param arr       --  要计算的矩阵
     * @param rowNo     --  起始行
     * @param columnNo  --  起始列
     * @return
     */
    public List<List<Long>> getCovalentFormulaMatrix(Long[][] arr, int rowNo, int columnNo)
    {
        List<List<Long>> r = new ArrayList<List<Long>>();
        for (int iCyc = 0, len = arr.length; iCyc < len; iCyc++)
        {
            if (rowNo != iCyc)
            {
                List<Long> bigDecimals = new ArrayList<Long>();
                for (int lCyc = 0, mlen = arr[0].length; lCyc < mlen; lCyc++)
                {
                    if (lCyc != columnNo)
                    {
                        bigDecimals.add(arr[iCyc][lCyc]);
                    }
                }
                r.add(bigDecimals);
            }
        }
        return r;
    }

    /**
     * @apiNote 求一个[3x3]矩阵的代数余子式
     * @author yk
     * @date 2021/4/16 18:03
     * @param arr       --  要计算的矩阵
     * @param rowNo     --  起始行
     * @param columnNo  --  起始列
     * @return
     */
    public long getAlgebraCovalentFormula3DMatrix(Long[][] arr, int rowNo, int columnNo)
    {
        List<List<Long>> covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
        Long[][] bigDecimals = this.formatList2Array(covalentFormulaMatrix);
        long bigDecimal = this.determinant2D(bigDecimals);
        if ((rowNo + columnNo) % 2 == 0)
        {
            return bigDecimal;
        }
        else
        {
            return bigDecimal * -1;
        }
    }

    /**
     * @apiNote   计算一个[3x3]矩阵的逆矩阵
     * @author yk
     * @date 2022/1/30 11:05
     * @param arr
     * @return
     */
    public Double[][] getInverse3DMatrix(Long[][] arr)
    {
        if(!checkSquareMatrix(arr))
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
        int row = arr.length;
        if(row>3)
        {
            throw new RuntimeException("该方法只可以计算[3x3]以下的矩阵！");
        }
        if(row==2)
        {
            long l = determinant2D(arr);
            if(l==0)
            {
                throw new RuntimeException("由于该矩阵行列式为0，不存在逆矩阵！");
            }
            Double[][] temp = new Double[2][2];
            temp[0][0] = new BigDecimal(arr[1][1])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[0][1] = new BigDecimal(arr[0][1])
                    .multiply(new BigDecimal(-1))
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][0] = new BigDecimal(arr[1][0])
                    .multiply(new BigDecimal(-1))
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][1] = new BigDecimal(arr[0][0])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            return temp;
        }
        else
        {
            long l = determinant3D(arr, 0, 0, 0);
            if(l==0)
            {
                throw new RuntimeException("由于该矩阵行列式为0，不存在逆矩阵！");
            }
            Long[][] acfMatrix = getAlgebraCovalentFormulaMatrix(arr);
            Long[][] matrixTranspose = getMatrixTranspose(acfMatrix);
            Double[][] temp = new Double[3][3];
            temp[0][0] = new BigDecimal(matrixTranspose[0][0])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[0][1] = new BigDecimal(matrixTranspose[0][1])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[0][2] = new BigDecimal(matrixTranspose[0][2])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][0] = new BigDecimal(matrixTranspose[1][0])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][1] = new BigDecimal(matrixTranspose[1][1])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][2] = new BigDecimal(matrixTranspose[1][2])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[2][0] = new BigDecimal(matrixTranspose[2][0])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[2][1] = new BigDecimal(matrixTranspose[2][1])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            temp[2][2] = new BigDecimal(matrixTranspose[2][2])
                    .divide(new BigDecimal(l), 4, RoundingMode.FLOOR)
                    .doubleValue();
            return temp;
        }
    }

    /**
     * @apiNote 获取[3x3]矩阵的代数余子式矩阵
     * @author yk
     * @date 2022/1/30 14:06
     * @param arr
     * @return
     */
    public Long[][] getAlgebraCovalentFormulaMatrix(Long[][] arr)
    {
        if(!checkSquareMatrix(arr))
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
        Long[][] r = new Long[arr.length][arr.length];
        for(int iCyc=0,len=arr.length; iCyc<len; iCyc++)
        {
            for(int lCyc=0,mlen=arr.length; lCyc<mlen; lCyc++)
            {
                r[iCyc][lCyc] = getAlgebraCovalentFormula3DMatrix(arr, iCyc, lCyc);
            }
        }
        return r;
    }

    /**
     * @apiNote 矩阵的转置
     * @author yk
     * @date 2022/1/30 14:32
     * @param arr
     * @return
     */
    public Long[][] getMatrixTranspose(Long[][] arr)
    {
        Long[][] r = new Long[arr[0].length][arr.length];
        for(int iCyc=0,len=arr.length; iCyc<len; iCyc++)
        {
            for(int lCyc=0,mlen=arr[0].length; lCyc<mlen; lCyc++)
            {
                r[lCyc][iCyc] = arr[iCyc][lCyc];
            }
        }
        return r;
    }

    /**
     * 集合转数组
     * @param list
     * @return
     */
    public Long[][] formatList2Array(List<List<Long>> list)
    {
        Long[][] bigr = new Long[list.size()][];

        for (int iCyc = 0, len = list.size(); iCyc < len; iCyc++)
        {
            List<Long> pColumnArr = list.get(iCyc);
            Long[] bigDecimals = pColumnArr.toArray(new Long[]{});
            bigr[iCyc] = bigDecimals;
        }
        return bigr;
    }

    /**
     * @apiNote   矩阵相乘（多列矩阵x单列矩阵）
     * @author yk
     * @date 2021/6/6 22:16
     * @param matrix1
     * @param matrix2
     * @return
     */
    public Long[][] multipMatrixSingleColumn(Long[][] matrix1, Long[][] matrix2)
    {
        if (matrix1.length != matrix2.length)
        {
            throw new RuntimeException("相乘的矩阵行数不相等!!!");
        }
        Long[][] temp = new Long[matrix2.length][matrix2[0].length];
        for (int iCyc = 0, len = matrix1.length; iCyc < len; iCyc++)
        {
            Long[] pRowArr = getRowOrColumn(matrix1, iCyc, MatrixColumn.ROW);
            Long cTemp = 0L;
            for (int jCyc = 0, nLen = pRowArr.length; jCyc < nLen; jCyc++)
            {
                Long row = pRowArr[jCyc];
//                Long[] pColumnArr = getRowOrColumn(matrix2, jCyc, MatrixColumn.COLUMN);
                Long column = matrix2[jCyc][0];
                cTemp = cTemp + (row * column);
            }
            temp[iCyc][0] = cTemp;
        }
        return temp;
    }

    /**
     * @apiNote   矩阵相乘
     * @author yk
     * @date 2021/4/15 18:35
     * @param matrix1
     * @param matrix2
     * @return
     */
    public Long[][] multipMatrixSpecsEqual(Long[][] matrix1, Long[][] matrix2)
    {
        if (matrix1.length != matrix2.length)
        {
            throw new RuntimeException("相乘的矩阵行列数需要一致!!!");
        }
        Long[][] temp = new Long[matrix1.length][matrix1.length];
        for (int iCyc = 0, len = matrix1.length; iCyc < len; iCyc++)
        {
            Long[] pRowArr = getRowOrColumn(matrix1, iCyc, MatrixColumn.ROW);
            for (int lCyc = 0, mLen = matrix2[0].length; lCyc < mLen; lCyc++)
            {
                Long[] pColumnArr = getRowOrColumn(matrix2, lCyc, MatrixColumn.COLUMN);
                Long cTemp = 0L;
                for (int jCyc = 0, nLen = pRowArr.length; jCyc < nLen; jCyc++)
                {
                    Long row = pRowArr[jCyc];
                    Long column = pColumnArr[jCyc];
                    cTemp = cTemp + (row * column);
                }
                temp[iCyc][lCyc] = cTemp;
            }
        }
        return temp;
    }

    /**
     * @apiNote   打印矩阵
     * @author yk
     * @date 2021/9/20 19:37
     * @param arr
     * @return
     */
    public static void printMatrix(Object[][] arr)
    {
        System.out.println("==============================");
        for (Object[] pRow : arr)
        {
            for (Object pColumn : pRow)
            {
                System.out.print(pColumn.toString() + "\t\t\t");
            }
            System.out.println("\n");
        }
        System.out.println("==============================");
    }

    /**
     * @apiNote   获取一个矩阵的指定行或者列
     * @author yk
     * @date 2021/4/15 18:35
     * @param arr   --  矩阵
     * @param num   --  行号或者列号
     * @param pDir  --  行或者列
     * @return
     */
    private Long[] getRowOrColumn(Long[][] arr, int num, MatrixColumn pDir)
    {
        if (pDir.equals(MatrixColumn.COLUMN))
        {
            List<Long> temp = new ArrayList<Long>();
            for (int iCyc = 0, len = arr.length; iCyc < len; iCyc++)
            {
                temp.add(arr[iCyc][num]);
            }

            return temp.toArray(new Long[temp.size()]);
        }
        else if (pDir.equals(MatrixColumn.ROW))
        {
            return arr[num];
        }
        else
        {
            return new Long[] { };
        }
    }

    /**
     * @apiNote   校验一个矩阵是否是正方形矩阵
     * @author yk
     * @date 2022/1/30 10:54
     * @param
     * @return
     */
    private boolean checkSquareMatrix(Long[][] arr)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                return false;
            }
        }
        return true;
    }
}
