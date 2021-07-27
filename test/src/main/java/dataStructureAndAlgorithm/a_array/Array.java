package dataStructureAndAlgorithm.a_array;

/**
 * 数组的插入、删除、按照下标随机访问
 *
 * @author zf
 * @date
 */
public class Array {

    //定义一个data存储数据
    private int data[];
    //定义数组长度
    private int n;
    //定义实际长度
    private int count;

    //构造方法，定义数组大小
    public Array(int capacity) {
        this.data = new int[capacity];
        this.n = capacity;
        this.count = 0;//初始大小为0
    }

    //根据索引找到元素
    public int find(int index){
        if(index <0 ||index >=count){
            return -1;
        }
        return data[index];
    }

    //元素插入
    public boolean insert(int index,int value){
        //数组空间已满
        if(count == n){
            System.out.println("数组空间已满，没有可插入位置");
            return false;
        }
        //插入索引位置不合理
        if(index > count || index <0){
            System.out.println("插入位置不合理");
            return false;
        }
        //位置合理
        for( int i = count; i > index; --i){
            data[i] = data[i - 1];
        }
        data[index] = value;
        count ++;
        return true;
    }

    //元素删除
    public boolean delete(int index){
        if(index > count || index < 0){
            return false;
        }
        for (int i = index+1; i <count ; i++) {
            data[i-1] = data[i];
        }
        count --;
        return true;
    }


    public static void main(String[] args) {
        Array array=new Array(5);
        array.insert(0,1);
        array.insert(1,2);
        array.delete(1);
        for (int i = 0; i < array.count; i++) {
            System.out.println(array.data[i]);
        }
    }
}
