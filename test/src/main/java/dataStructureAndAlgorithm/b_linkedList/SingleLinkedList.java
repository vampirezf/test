package dataStructureAndAlgorithm.b_linkedList;

/**
 * 单链表插入、删除、查找
 *
 * @author zf
 * @date
 */
public class SingleLinkedList {

    //定义一个Node
    public static class Node{
        private int data;
        private Node next;

        public Node(int data,Node next){
            this.data = data;
            this.next = next;
        }
        public int getData(){
            return data;
        }
    }

    public static Node createNode(int value){
        return new Node(value,null);
    }

    private Node head = null;

    public Node findByValue(int value){
        Node p = head;
        while(p != null && p.data != value){
            p = p.next;
        }
        return p;
    }

    public Node findByIndex(int index){
        Node p = head;
        int pos = 0;
        while (p != null && pos != index){
            p = p.next;
            pos++;
        }
        return p;
    }

    //顺序插入
    //链表尾部插入
    public void insertTail(int value){

        Node newNode = new Node(value,null);
        //空链表，可以插入新节点作为head也可以不操作
        if(head == null){
            head = newNode;
        }else {
            Node p = head;
            while (p.next != null){
                p = p.next;
            }
            newNode.next = p.next;
            p.next = newNode;
        }
    }

    //删除
    public void deleteByNode(Node p) {
        if (p == null || head == null){
            return;
        }

        if (p == head) {
            head = head.next;
            return;
        }

        Node q = head;
        while (q != null && q.next != p) {
            q = q.next;
        }

        if (q == null) {
            return;
        }

        q.next = q.next.next;
    }

    public void deleteByValue(int value){
        if(head == null){
            return;
        }
        Node p = head;
        Node q = null;
        while (p != null && p.data != value){
            q = p;
            p = p.next;
        }
        if(p == null){
            return;
        }
        if(q == null){
            head = head.next;
        }else {
            q.next = head.next.next;
        }
    }

    public void printAll() {
        Node p = head;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SingleLinkedList link = new SingleLinkedList();
        int data[] = {1,2,5,3,1};

        for(int i =0; i < data.length; i++){
            link.insertTail(data[i]);
        }
        link.printAll();

    }

}
