package com.tool.coordinate.support;

import com.tool.coordinate.entity.Enum.MatrixColumn;
import com.tool.coordinate.tool.PrintTool;

import java.lang.reflect.Array;
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
    public <T> Double determinant2D(T[][] arr)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            BigDecimal bd_11 = new BigDecimal(arr[1][1] + "");
            BigDecimal bd_00 = new BigDecimal(arr[0][0] + "");
            BigDecimal bd_01 = new BigDecimal(arr[0][1] + "");
            BigDecimal bd_10 = new BigDecimal(arr[1][0] + "");

            /**
             * 公式：((arr[1][1] * arr[0][0]) - (arr[0][1] * arr[1][0]))
             */
            BigDecimal result = bd_11.multiply(bd_00).subtract(bd_01.multiply(bd_10));
            return result.doubleValue();
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
    public <T> Double determinant3D(T[][] arr, int rowNo, int columnNo, BigDecimal count)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            T[][] covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
            if (covalentFormulaMatrix.length == 2)
            {
                BigDecimal pDeterminant2D = new BigDecimal(this.determinant2D(covalentFormulaMatrix)+"");
                BigDecimal pValue = new BigDecimal(arr[rowNo][columnNo] + "");
                if ((rowNo + columnNo) % 2 == 0)
                {
                    count = count.add(pDeterminant2D.multiply(pValue));
                }
                else
                {
                    count = count.add(pDeterminant2D.multiply(pValue).multiply(new BigDecimal("-1")));
                }
                columnNo++;
            }
            if (columnNo >= column)
            {
                return count.doubleValue();
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
    public <T> Double determinant4D(T[][] arr, int rowNo, int columnNo, BigDecimal count)
    {
        int row = arr.length;
        if (row > 0)
        {
            int column = arr[0].length;
            if (row != column)
            {
                throw new RuntimeException("这不是一个正方形矩阵！");
            }
            T[][] covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
            if (covalentFormulaMatrix.length == 3)
            {
                BigDecimal pDeterminant3D = new BigDecimal(this.determinant3D(covalentFormulaMatrix, 0, 0, new BigDecimal("0"))+"");
                BigDecimal pValue = new BigDecimal(arr[rowNo][columnNo] + "");
                if ((rowNo + columnNo) % 2 == 0)
                {
                    count = count.add(pDeterminant3D.multiply(pValue));
                }
                else
                {
                    count = count.add(pDeterminant3D.multiply(pValue).multiply(new BigDecimal("-1")));
                }
                columnNo++;
            }
            if (columnNo >= column)
            {
                return count.doubleValue();
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
     * @apiNote 计算矩阵的行列式
     * @param arr
     * @param <T>
     * @return
     */
    public <T> Double mathDeterminant(T[][] arr)
    {
        MathMatrixDeterminant mathMatrixDeterminant = new MathMatrixDeterminant(arr);
        Double v = mathMatrixDeterminant.matrixDeterminant();
        return v;
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
    public <T> T[][] getCovalentFormulaMatrix(T[][] arr, int rowNo, int columnNo)
    {
        int l = arr.length - 1;
        Object obj = Array.newInstance(arr.getClass().getComponentType(), l);
        for(int iCyc = 0; iCyc<l; iCyc++)
        {
            Object temp = Array.newInstance(arr[0].getClass().getComponentType(), arr[0].length-1);
            Array.set(obj, iCyc, temp);
        }
        T[][] tl = (T[][]) obj;
        for (int iCyc = 0, len = arr.length; iCyc < len; iCyc++)
        {
            if (rowNo != iCyc)
            {
                for (int lCyc = 0, mlen = arr[0].length; lCyc < mlen; lCyc++)
                {
                    if (lCyc != columnNo)
                    {
                        tl[rowNo<=iCyc?iCyc-1:iCyc][columnNo<=lCyc?lCyc-1:lCyc] = arr[iCyc][lCyc];
                    }
                }
            }
        }
        return tl;
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
    public <T> Double getAlgebraCovalentFormula3DMatrix(T[][] arr, int rowNo, int columnNo)
    {
        T[][] covalentFormulaMatrix = this.getCovalentFormulaMatrix(arr, rowNo, columnNo);
        Double d = mathDeterminant(covalentFormulaMatrix);
        BigDecimal bigDecimal = new BigDecimal(d+"");
        if ((rowNo + columnNo) % 2 == 0)
        {
            return bigDecimal.doubleValue();
        }
        else
        {
            return bigDecimal.multiply(new BigDecimal("-1")).doubleValue();
        }
    }

    /**
     * @apiNote   计算一个方阵的逆矩阵
     * @author yk
     * @date 2022/1/30 11:05
     * @param arr
     * @return
     */
    public <T> Double[][] getInverse3DMatrix(T[][] arr)
    {
        if(!checkSquareMatrix(arr))
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
        int scale = 6;
        int row = arr.length;
        /*if(row>3)
        {
            throw new RuntimeException("该方法只可以计算[3x3]以下的矩阵！");
        }*/
        Double l = mathDeterminant(arr);
        if(l==0)
        {
            throw new RuntimeException("由于该矩阵行列式为0，不存在逆矩阵！");
        }
        if(row==2)
        {
            Double[][] temp = new Double[2][2];
            temp[0][0] = new BigDecimal(arr[1][1]+"")
                    .divide(new BigDecimal(l+""), scale, RoundingMode.FLOOR)
                    .doubleValue();
            temp[0][1] = new BigDecimal(arr[0][1]+"")
                    .multiply(new BigDecimal(-1))
                    .divide(new BigDecimal(l+""), scale, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][0] = new BigDecimal(arr[1][0]+"")
                    .multiply(new BigDecimal(-1))
                    .divide(new BigDecimal(l+""), scale, RoundingMode.FLOOR)
                    .doubleValue();
            temp[1][1] = new BigDecimal(arr[0][0]+"")
                    .divide(new BigDecimal(l+""), scale, RoundingMode.FLOOR)
                    .doubleValue();
            return temp;
        }
        else
        {
            Double[][] acfMatrix = getAlgebraCovalentFormulaMatrix(arr);
            Double[][] matrixTranspose = getMatrixTranspose(acfMatrix);
            Double[][] temp = new Double[arr.length][arr[0].length];
            for(int iCyc=0; iCyc<arr.length; iCyc++)
            {
                for(int lCyc=0; lCyc<arr[0].length; lCyc++)
                {
                    temp[iCyc][lCyc] = new BigDecimal(matrixTranspose[iCyc][lCyc]+"")
                            .divide(new BigDecimal(l+""), scale, RoundingMode.FLOOR)
                            .doubleValue();
                }
            }
            return temp;
        }
    }

    /**
     * @apiNote 获取矩阵的代数余子式矩阵
     * @author yk
     * @date 2022/1/30 14:06
     * @param arr
     * @return
     */
    public <T> Double[][] getAlgebraCovalentFormulaMatrix(T[][] arr)
    {
        if(!checkSquareMatrix(arr))
        {
            throw new RuntimeException("这不是一个正方形矩阵！");
        }
        Double[][] r = new Double[arr.length][arr.length];
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
     * @date 2022/12/6 14:32
     * @param arr
     * @return
     */
    public <T> T[][] getMatrixTranspose(T[][] arr)
    {
        Object obj = Array.newInstance(arr.getClass().getComponentType(), arr[0].length);
        for(int iCyc=0,len=arr[0].length; iCyc<len; iCyc++)
        {
            Object temp = Array.newInstance(arr[0].getClass().getComponentType(), arr.length);
            Array.set(obj, iCyc, temp);
        }
        T[][] copy = (T[][])obj;
        for(int iCyc=0,len=arr.length; iCyc<len; iCyc++)
        {
            for(int lCyc=0,mlen=arr[0].length; lCyc<mlen; lCyc++)
            {
                copy[lCyc][iCyc] = arr[iCyc][lCyc];
            }
        }
        return copy;
    }

    /**
     * @apiNote   矩阵相乘（多列矩阵x单列矩阵）
     * @author yk
     * @date 2021/6/6 22:16
     * @param matrix1
     * @param matrix2
     * @return
     */
    public <T> Double[] multipMatrixSingleColumn(T[][] matrix1, T[] matrix2)
    {
        if (matrix1[0].length != matrix2.length)
        {
            throw new RuntimeException("相乘的矩阵行数不相等!!!");
        }
        Double[] numbers = {};
        Double[] temp = (Double[])Array.newInstance(numbers.getClass().getComponentType(), matrix1.length);
        for (int iCyc = 0, len = matrix1.length; iCyc < len; iCyc++)
        {
            T[] pRowArr = getRowOrColumn(matrix1, iCyc, MatrixColumn.ROW);
            BigDecimal cTemp = new BigDecimal("0");
            for (int jCyc = 0, nLen = pRowArr.length; jCyc < nLen; jCyc++)
            {
                T row = pRowArr[jCyc];
                T column = matrix2[jCyc];

                BigDecimal bdRow = new BigDecimal(row+"");
                BigDecimal bdColumn = new BigDecimal(column+"");

                cTemp = cTemp.add(bdRow.multiply(bdColumn));
            }
            Array.set(temp, iCyc, cTemp.doubleValue());
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
    public <T> Double[][] multipMatrixSpecsEqual(T[][] matrix1, T[][] matrix2)
    {
        if (matrix1.length != matrix2.length)
        {
            throw new RuntimeException("相乘的矩阵行列数需要一致!!!");
        }
        Double[][] numbers = {{}};
        Object obj = Array.newInstance(numbers.getClass().getComponentType(), matrix1[0].length);
        for(int iCyc=0,len=matrix1[0].length; iCyc<len; iCyc++)
        {
            Object temp = Array.newInstance(numbers[0].getClass().getComponentType(), matrix1.length);
            Array.set(obj, iCyc, temp);
        }
        Double[][] temp = (Double[][])obj;
        for (int iCyc = 0, len = matrix1.length; iCyc < len; iCyc++)
        {
            T[] pRowArr = getRowOrColumn(matrix1, iCyc, MatrixColumn.ROW);
            for (int lCyc = 0, mLen = matrix2[0].length; lCyc < mLen; lCyc++)
            {
                T[] pColumnArr = getRowOrColumn(matrix2, lCyc, MatrixColumn.COLUMN);
                BigDecimal cTemp = new BigDecimal("0");
                for (int jCyc = 0, nLen = pRowArr.length; jCyc < nLen; jCyc++)
                {
                    T row = pRowArr[jCyc];
                    T column = pColumnArr[jCyc];

                    BigDecimal bdRow = new BigDecimal(row+"");
                    BigDecimal bdColumn = new BigDecimal(column+"");

                    cTemp = cTemp.add(bdRow.multiply(bdColumn));
                }
                Array.set(temp[iCyc], lCyc, cTemp.doubleValue());
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
        String[][] pData = new String[arr.length][arr[0].length];
        for(int iCyc=0,len=arr.length; iCyc<len; iCyc++)
        {
            for(int lCyc=0,mlen=arr[0].length; lCyc<mlen; lCyc++)
            {
                BigDecimal temp = new BigDecimal(arr[iCyc][lCyc]+"");
                pData[iCyc][lCyc] = temp.toString();
            }
        }
        PrintTool.printTable(pData);
    }

    /**
     * @apiNote   打印矩阵
     * @author yk
     * @date 2021/9/20 19:37
     * @param arr
     * @return
     */
    public static <T> void printMatrixForList(List<List<T>> list)
    {
        String[][] pData = list.toArray(new String[][]{});
        PrintTool.printTable(pData);
    }

    /**
     * 集合转数组
     * @param list
     * @return
     */
    private Long[][] formatList2Array(List<List<Long>> list)
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
     * @apiNote   获取一个矩阵的指定行或者列
     * @author yk
     * @date 2021/4/15 18:35
     * @param arr   --  矩阵
     * @param num   --  行号或者列号
     * @param pDir  --  行或者列
     * @return
     */
    private <T> T[] getRowOrColumn(T[][] arr, int num, MatrixColumn pDir)
    {
        if (pDir.equals(MatrixColumn.COLUMN))
        {
            List<T> temp = new ArrayList<T>();
            for (int iCyc = 0, len = arr.length; iCyc < len; iCyc++)
            {
                temp.add(arr[iCyc][num]);
            }

            T[] o = (T[])Array.newInstance(arr[0].getClass().getComponentType(), arr[0].length);
            return temp.toArray(o);
        }
        else if (pDir.equals(MatrixColumn.ROW))
        {
            return arr[num];
        }
        else
        {
            throw new RuntimeException("请指定行列[pDir]属性值!");
        }
    }

    /**
     * @apiNote   校验一个矩阵是否是正方形矩阵
     * @author yk
     * @date 2022/1/30 10:54
     * @param
     * @return
     */
    private <T> boolean checkSquareMatrix(T[][] arr)
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

    /**
     * @apiNote  复制多维数组
     * @date 2022/12/6 16:12
     * @param array
     * @return
     */
    private Object deepCopyArrayInternal(Object array)
    {
        int length = Array.getLength(array);
        Object copy = Array.newInstance(array.getClass().getComponentType(), length);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(array, i);
            if (value != null && value.getClass().isArray())
                value = deepCopyArrayInternal(value);
            Array.set(copy, i, value);
        }
        return copy;
    }

    class MathMatrixDeterminant<T>
    {

        private T[][] t;

        public MathMatrixDeterminant(){}

        public MathMatrixDeterminant(T[][] t)
        {
            this.t = t;
        }

        /**
         * @apiNote 计算矩阵的行列式
         * @return
         */
        public Double matrixDeterminant()
        {
            T[][] t = this.t;
            int length = t.length;

            int[] ints = this.GetArray(length);
            //  获取数组的全排列
            List<List<Integer>> pFullList = this.FullArrangement(ints, new ArrayList<Integer>(), new ArrayList<List<Integer>>());
            //  行列式的值
            BigDecimal v = new BigDecimal("0");
            for(int iCyc=0,len=pFullList.size(); iCyc<len; iCyc++)
            {
                //  单项的和
                BigDecimal pMonomialTotal = new BigDecimal("1");

                List<Integer> pOrderList = pFullList.get(iCyc);
                Integer[] tempArr = pOrderList.toArray(new Integer[]{});
                //  计算逆序数
                int i = this.mathReverseOrderNumber(tempArr);
                if(i%2!=0)
                {
                    pMonomialTotal = pMonomialTotal.multiply(new BigDecimal("-1"));
                }

                for (int lCyc=0,mLen=pOrderList.size(); lCyc<mLen; lCyc++)
                {
                    Integer index = pOrderList.get(lCyc);

                    T t1 = t[lCyc][index];
                    pMonomialTotal = pMonomialTotal.multiply(new BigDecimal(t1 + ""));
                }

                v = v.add(pMonomialTotal);
            }
            return v.doubleValue();
        }

        /**
         * @apiNote 计算逆序数
         * @param arr
         * @return
         */
        private int mathReverseOrderNumber(Integer[] arr)
        {
            Integer count = 0;
            Integer[] copyArr = Arrays.copyOf(arr, arr.length);
            for (int iCyc = 1; iCyc < arr.length; iCyc++)
            {
                int tmp = arr[iCyc];
                int jCyc = iCyc;
                while (jCyc > 0 && tmp < arr[jCyc - 1]) {
                    arr[jCyc] = arr[jCyc - 1];
                    jCyc--;
                    count++;
                }

                if (jCyc != iCyc) {
                    arr[jCyc] = tmp;
                }
            }
            return count;
        }

        /**
         * @apiNote 数组的全排列
         * @param arr--数组
         * @param list--暂存集合
         * @param result--结果集合
         * @return
         */
        private List<List<Integer>> FullArrangement(int[] arr, List<Integer> list, List<List<Integer>> result)
        {
            List<Integer> temp = new ArrayList<>(list);
            if (arr.length == list.size())
            {
                result.add(temp);
            }
            for (int iCyc=0; iCyc<arr.length; iCyc++)
            {
                if (temp.contains(arr[iCyc]))
                {
                    continue;
                }
                temp.add(arr[iCyc]);
                FullArrangement(arr, temp, result);
                temp.remove(temp.size()-1);
            }
            return result;
        }

        /**
         * @apiNote 获取数组
         * @param num
         * @return
         */
        private int[] GetArray(int num)
        {
            int[] arr = new int[num];
            for (int iCyc=0; iCyc<num; iCyc++)
            {
                arr[iCyc] = iCyc;
            }
            return arr;
        }
    }
}
