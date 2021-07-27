package dataStructureAndAlgorithm;


/**
 * 二维数组
 */
public class XsArray {
    public static void main(String[] args) {
        System.out.println("原始数组：");
        int[][] ysArray = new int[10][10];// 创建了 一个二维数组————相当于一个10*10的棋盘
        ysArray[3][5] = 1;
        ysArray[7][9] = 2;// 在棋盘中落子，1代表白棋，2代表黑棋

        int sum = 0;//记录棋盘中的棋子个数
        //遍历二维数组
        for (int i = 0; i < ysArray.length; i++) {
            for (int j = 0; j < ysArray[i].length; j++) {
                if (ysArray[i][j] != 0) {//判断各个位置是否有棋子
                    sum++;
                }
                System.out.print(ysArray[i][j] + "\t");

            }
            System.out.println();
        }

        System.out.println("=========================================================");
        System.out.println("稀疏数组：");

        /*
         * 创建稀疏数组：
         * 		第一行：1、行数，2、列数，3、棋子个数
         * 		其他行：1、棋子所在的行数，2、棋子所在的列数，3、棋子的数值
         * 	特点：1、稀疏数组的列数是个常数————3列
         * 		  2、稀疏数组的行数由棋子个数决定————棋子个数+1
         */

        int[][] xsArray = new int[sum + 1][3];
        //第一行
        xsArray[0][0] = ysArray.length;//得到二维数组的行数
        xsArray[0][1] = ysArray[0].length;//得到二维数组的行数（得到第0行有几个元素）
        xsArray[0][2] = sum;

        int count = 0;//初始化行数
        //遍历二维数组，得到棋子的位置及数值
        for (int i = 0; i < ysArray.length; i++) {
            for (int j = 0; j < ysArray[i].length; j++) {
                if (ysArray[i][j] != 0) {
                    count++;//从第二行开始
                    xsArray[count][0] = i;//得到棋子所在行数
                    xsArray[count][1] = j;//得到棋子所在列数
                    xsArray[count][2] = ysArray[i][j];//得到棋子的数值
                }
            }
        }

        //遍历打印稀疏数组
        for (int i = 0; i < xsArray.length; i++) {
            for (int j = 0; j < xsArray[i].length; j++) {
                System.out.print(xsArray[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("=========================================================");
        System.out.println("稀疏数组还原成原始数组：");

        //创建二维数组				行				列
        int[][] hyArray = new int[xsArray[0][0]][xsArray[0][1]];
        for (int i = 1; i < xsArray.length; i++) {
            hyArray[xsArray[i][0]][xsArray[i][1]] = xsArray[i][2];//从稀疏数组中得到棋子位置
        }

        //遍历打印二维数组
        for (int i = 0; i < hyArray.length; i++) {
            for (int j = 0; j < hyArray[i].length; j++) {
                System.out.print(hyArray[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
