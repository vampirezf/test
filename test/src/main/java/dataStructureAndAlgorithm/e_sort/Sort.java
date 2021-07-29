package dataStructureAndAlgorithm.e_sort;

/**
 * 冒泡排序、插入排序、选择排序
 * @author zf
 * @date
 */
public class Sort {

    /**
     * 冒泡排序
     * @param a 数组
     * 重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。
     * 走访数列的工作是重复地进行直到没有再需要交换，
     * 也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。
     *
     * 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 针对所有的元素重复以上的步骤，除了最后一个；
     * 重复步骤1~3，直到排序完成。
     */
    public static int[] bubbleSort(int a[]){
        if(a.length <=1){
            return a;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if(a[j] > a[j+1]){
                    int temp = a[j+1];
                    a[j+1] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }

    /**
     * 插入排序
     * @param a
     * @return
     * 通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
     *
     * 从第一个元素开始，该元素可以认为已经被排序；
     * 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 将新元素插入到该位置后；
     * 重复步骤2~5。
     */
    public static int[] insertSort(int a[]){
        if(a.length <=1){
            return a;
        }
        //从第二个数开始 抽出进行比较
        for (int i = 1; i < a.length; i++) {
            int value = a[i];
            int j = i-1;
            //左边>右边 右移
            while (j >= 0 && a[j] > a[i]) {
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = value;
        }
        return a;
    }

    /**
     * 选择排序
     * @param a
     * @return
     *
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，
     * 再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     *
     * 初始状态：无序区为R[1..n]，有序区为空；
     * 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。
     * 该趟排序从当前无序区中-选出关键字最小的记录 R[k]，将它与无序区的第1个记录R交换，
     * 使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
     * n-1趟结束，数组有序化了。
     */
    public static int[] selectSort(int a[]){
        if (a.length <= 0){
            return a;
        }
        for (int i = 0; i < a.length - 1; i++) {
            //查找最小数
            int minIndex = i;
            for (int j = i+1; j <a.length ; j++) {
                if(a[j] < a[minIndex]){
                    minIndex = j;
                }
            }
            //交换
            int temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        int a [] = {1,3,2};
        int[] ints = selectSort(a);
    }

}
