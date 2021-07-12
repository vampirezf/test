package lambda;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaTest {

    @Test
    public void test1(){
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    @Test
    public void test2(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        for (Integer integer : squaresList) {
            System.out.println(integer);
        }
    }

    @Test
    public void test3(){
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    @Test
    public void test4(){
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    @Test
    public void test5(){
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
    }

    @Test
    public void test6(){
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    @Test
    public void test7(){
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        System.out.println("筛选列表: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);
    }

    @Test
    public void test8(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
    }

    @Test
    public void test9(){
        LambdaTest tester = new LambdaTest();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> { return a * b; };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) ->
                System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");
    }
    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    @Test
    public void test10(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        list.add(7);
        list.add(2);
        list=list.stream().sorted().collect(Collectors.toList());
        list.forEach(e-> System.out.println("升序"+e));
        System.out.println("---分割线---");
        list=list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        list.forEach(e-> System.out.println("升序"+e));
    }

    @Test
    public void test11(){
        Account a1 = new Account(1,"小白",100D);
        Account a2 = new Account(2,"小黑",10000D);
        Account a3 = new Account(3,"小红",200D);
        Account a4 = new Account(4,"小蓝",100D);
        List<Account> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);

        //id升序
        Comparator<Account> idAsc = Comparator.comparing(Account::getId);
        //id降序
        Comparator<Account> idDesc = Comparator.comparing(Account::getId).reversed();
        //金钱升序
        Comparator<Account> moneyAsc = Comparator.comparing(Account::getMoney);
        //金钱降序
        Comparator<Account> moneyDesc = Comparator.comparing(Account::getMoney).reversed();
        //联合排序 金钱降序后id升序
        Comparator<Account> idAscMoneyDesc = moneyDesc.thenComparing(idAsc);
        list= list.stream().sorted(idAsc).collect(Collectors.toList());
        list.forEach(e-> System.out.println("id升序==="+e.getId()+e.getName()+e.getMoney()));
        System.out.println("-----分割线-----");

        list= list.stream().sorted(idDesc).collect(Collectors.toList());
        list.forEach(e-> System.out.println("id降序==="+e.getId()+e.getName()+e.getMoney()));
        System.out.println("-----分割线-----");

        list= list.stream().sorted(moneyAsc).collect(Collectors.toList());
        list.forEach(e-> System.out.println("金钱升序==="+e.getId()+e.getName()+e.getMoney()));
        System.out.println("-----分割线-----");

        list= list.stream().sorted(moneyDesc).collect(Collectors.toList());
        list.forEach(e-> System.out.println("金钱降序==="+e.getId()+e.getName()+e.getMoney()));
        System.out.println("-----分割线-----");

        list= list.stream().sorted(idAscMoneyDesc).collect(Collectors.toList());
        list.forEach(e-> System.out.println("联合排序 金钱降序后id升序==="+e.getId()+e.getName()+e.getMoney()));
    }

    @Test
    public void test12(){
        Account a1 = new Account(1,"小白",100D);
        Account a2 = new Account(2,"小黑",10000D);
        Account a3 = new Account(3,"小红",200D);
        Account a4 = new Account(4,"小蓝",100D);
        Set<Account> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        set.add(a3);
        set.add(a4);

        set.stream().sorted(Comparator.comparing(Account::getId).reversed())
                .forEach(e -> System.out.println("id降序=="+ e.getId()+e.getName()+e.getMoney()));
    }

    @Test
    public void test13(){
        Map<Integer, String> map = new HashMap<>();
        map.put(3, "A");
        map.put(1, "F");
        map.put(2, "C");
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(e-> System.out.println("key升序"+e.getKey()+e.getValue()));
    }

    @Test
    public  void test14(){
        Account a1 = new Account(1,"小白",100D);
        Account a2 = new Account(2,"小黑",10000D);
        Account a3 = new Account(3,"小红",200D);
        Account a4 = new Account(4,"小蓝",100D);
        List<Account> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);

        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        //set2.add(2);

        List<Account> collect = list.stream().filter(e -> {
            Integer id = e.getId();
            return set2.contains(id);
        }).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test15(){
        String type = "a";
        Set<String> rset = new HashSet<>();
        rset.add("aaa");
        rset.add("bbb");
        rset.add("ccc");
        rset.add("ddd");
        rset.add("eee");
        /*
        name.indexOf("c") > -1 &&("a".equals(type)||"b".equals(type))
        name.indexOf(type) > -1
         */
        rset.stream().filter(name ->{
                return name.indexOf("c") > -1 &&("a".equals(type)||"b".equals(type));
        }).forEach(name-> System.out.println(name));
    }

    @Test
    public void test16(){
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.forEach(e->stringBuilder.append(e).append(","));
        System.out.println(stringBuilder);
    }

    @Test
    public void test17(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.stream().collect(Collectors.joining(","));
        String string = list.toString();
        System.out.println(string);
    }

    @Test
    public void test18(){
        String name = "小白";
        List<Account> list = new ArrayList<>();
        Account a1 = new Account(1,"小白",100D);
        Account a2 = new Account(2,"小黑",10000D);
        Account a3 = new Account(3,"小红",200D);
        Account a4 = new Account(4,"小蓝",100D);
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);

        list=list.stream().filter(e->{
            String name1 = e.getName();
            return name.equals(name1);
        }).collect(Collectors.toList());
        for (Account account : list) {
            System.out.println(account.getName());
        }
    }


}
