package leetCode;

import org.junit.Test;

import java.util.*;


public class OneQuestionPerDay {
    /**
     * 01两数之和
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
     */
    @Test
    public void test1() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] aa = twoSum2(nums, target);
//        int[] aa = twoSum2(nums, target);
        for (int i : aa) {
            System.out.println(i);
        }
    }

    /**
     * 双for循环
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] a = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    a[0] = i;
                    a[1] = j;
                }
            }
        }
        return a;
    }

    /**
     * 两遍哈希表
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        return null;
    }

    /**
     * 02两数相加
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     * 解释：342 + 465 = 807
     */
    @Test
    public void test2() {
        // 创建两个链表 // 第一个链表: 2-> 4 -> 3  (在做加法运算代表的是342) ListNode l1 = new ListNode(3);
        // 这是第一个链表的第一个节点(不能用这个节点去往下加数据)
        // 必须有一个指针去往第一个节点上去加数据 ListNode p = l1;
        // 这个指针节点会从链表的第一个节点一直往下走(直至最后一个节点) p.next = new ListNode(4);
        ListNode l1 = new ListNode(3);
        ListNode p = l1;
        p.next = new ListNode(4);
        p = p.next;
        p.next = new ListNode(2);
        p = p.next;
        // 第二个链表 ListNode l2 = new ListNode(4);
        ListNode l2 = new ListNode(4);
        ListNode q = l2;
        q.next = new ListNode(6);
        q = q.next;
        q.next = new ListNode(5);
        ListNode re = addTwoNumbers2(l1, l2);
        while (re != null) {
            System.out.println(re.val);
            re = re.next;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
//        while (l1 != null) {
//            System.out.println("===" + l1.val);
//            l1 = l1.next;
//        }
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        //过10进位
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }

        return dummyHead.next;
    }

    /**
     * 03无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    @Test
    public void test3(){
        int length = lengthOfLongestSubstring("");
        System.out.println(length);
    }

    public int lengthOfLongestSubstring(String s) {
        //滑动窗口求解
        int length = 0;
        //起始结束下标
        int start = 0;
        int end = 0;
        Set<Character> set = new HashSet<>();
        while (start<s.length() && end <s.length()){
            //如果重复 窗口左侧右移
            if(set.contains(s.charAt(end))){
                set.remove(s.charAt(start++));
            }
            //不重复 窗口右侧右移
            else {
                set.add(s.charAt(end++));
                //比较当前长度与临时存储长度取最大值
                length = Math.max(length,end-start);
            }
        }

        return length;
    }

    /**
     * 04寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     */
    @Test
    public void test4(){
        int[] nums1= {1,2,3};
        int[] nums2= {4,5,6};
        double medianSortedArrays = findMedianSortedArrays(nums1, nums2);
        System.out.println(medianSortedArrays);
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //合并两个数组
        int[] newArray = Arrays.copyOf(nums1,nums1.length+nums2.length);
        for (int i = 0; i < nums2.length; i++) {
            newArray[nums1.length+i]=nums2[i];
        }
        //排序
        Arrays.sort(newArray);
        if(newArray.length ==0){
            return Double.valueOf(0);
        }else if(newArray.length ==1){
            return newArray[0];
        }else if(newArray.length % 2 ==1){
            return Double.valueOf(newArray[newArray.length/2]);
        }else {
            return Double.valueOf((newArray[newArray.length/2 ] + newArray[newArray.length/2 -1]) /2.0);
        }
    }

    /**
     * 05 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     */
    @Test
    public void test5(){
        String s = "a";
        String s1 = longestPalindrome(s);
        System.out.println(s1);
    }

    public String longestPalindrome(String s) {
        //中心扩展法，单字符左右依次延伸判断左右是否相同
        char[] chars = s.toCharArray();
        int max = 1;
        int start = 0;
        //end 初始化 1防止截取为空返回
        int end =1;
        //奇数回文串 例：aba
        for (int i = 0; i < s.length(); i++) {
            int left = i-1;
            int right = i +1;
            while (left>=0 &&right<s.length()&&chars[left]==chars[right]){
                if(right-left+1 >max){
                    start =left;
                    end = right+1;
                    max=right-left+1;
                }
                left--;
                right++;
            }
        }
        //偶数回文串 例：bb
        for (int i = 0; i < s.length(); i++) {
            int left = i;
            int right = i +1;
            while (left>=0 &&right<s.length()&&chars[left]==chars[right]){
                if(right-left+1 >max){
                    start =left;
                    end = right+1;
                    max=right-left+1;
                }
                left--;
                right++;
            }
        }
        return s.substring(start,end);
    }

    /**
     * 06 Z 字形变换
     * 输入：s = "PAYPALISHIRING", numRows = 3
     * 输出："PAHNAPLSIIGYIR"
     */
    @Test
    public void test6(){
        String s = "PAYPALISHIRING";
        int numRows = 4;
        String convert = convert(s, numRows);
        System.out.println(convert);
    }

    public String convert(String s, int numRows) {
        /*
            以下为最终排列结果，数字为原字符位置对应下标
            规律：以4行为例  第一行，最后一行 依次增加 2 * (numRows - 1)->6个位下标；
                            中间行 第一次增量 hf-2*i ->4 后续依次为 hf - mid
            P0    A4    H8      N12
            A1 P3 L5 S7 I9  I11 G13
            Y2    I6    R10

            4行排列：
            P0      I6       N12
            A1   L5 S7   I11 G13
            Y2 A4   H8  R10
            P3      I9
         */
        //单列或单行
        if(s.length()<=numRows||numRows==1){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        //首尾增量
        int hf = 2 * (numRows - 1);
        for (int i = 0; i < numRows; i++) {
            int index = i;
            //首尾行
            if(i==0||i==numRows-1){
                while (index<s.length()){
                    sb.append(s.charAt(index));
                    index = index + hf;
                }
            }else {
                //中间行增量
                int mid = hf-2*i;
                while (index < s.length()) {
                    sb.append(s.charAt(index));
                    index = index + mid;
                    mid = hf - mid;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 07 整数反转
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     * 输入：x = 123
     * 输出：321
     */
    @Test
    public void test7(){
        System.out.println(reverse(1534236469));
    }

    public int reverse(int x) {
        //注意捕获异常
        if(x>0){
            StringBuffer s=new StringBuffer(String.valueOf(x));
            s = s.reverse();
            try {
                return Integer.parseInt(String.valueOf(s));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        if(x<0){
            StringBuffer s=new StringBuffer(String.valueOf((0-x)));
            s =s.reverse();
            try {
                return (0-Integer.parseInt(String.valueOf(s)));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return x;
    }

    /**
     * 08 字符串转换整数 (atoi)
     * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     * 函数 myAtoi(string s) 的算法如下：
     * 读入字符串并丢弃无用的前导空格
     * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
     * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
     * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
     * 如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
     * 返回整数作为最终结果。
     * 输入：s = "42"
     * 输出：42
     * * 解释：加粗的字符串为已经读入的字符，插入符号是当前读取的字符。
     * 第 1 步："42"（当前没有读入字符，因为没有前导空格）
     *          ^
     * 第 2 步："42"（当前没有读入字符，因为这里不存在 '-' 或者 '+'）
     *          ^
     * 第 3 步："42"（读入 "42"）
     *           ^
     * 解析得到整数 42 。
     * 由于 "42" 在范围 [-231, 231 - 1] 内，最终结果为 42 。
     */
    @Test
    public void test8(){
        String s = "-91283472332";
        System.out.println(myAtoi(s));
    }
    public int myAtoi(String s) {
        //空字符串及null返回0
        if (s == null || s.length() == 0)return 0;
        //1 去掉空格
        s = s.trim();
        //为空返回0
        if(s.length() == 0)return 0;
        //正负符号
        int sign = 1;
        int index = 0;
        int res = 0;
        //2 判断正负
        if(s.charAt(0) =='+'||s.charAt(0) =='-'){
            sign =s.charAt(0)=='+'?1:-1;
            index++;
        }

        //3 提取数字
        //判断是否数字
        while(index < s.length() && Character.isDigit(s.charAt(index))){
            int c = s.charAt(index) - '0';
            //正数且越界
            if(sign ==1 && (res > Integer.MAX_VALUE/10 || (res == Integer.MAX_VALUE/10 && c > Integer.MAX_VALUE%10))){
                return Integer.MAX_VALUE;
            }
            //负数且越界
            if(sign ==-1 && (res + Integer.MIN_VALUE/10 > 0 || (res + Integer.MIN_VALUE/10 == 0 && c + Integer.MIN_VALUE%10 > 0))){
                return Integer.MIN_VALUE;
            }
            //获得当前转换数字
            res = res*10 + c;
            index++;
        }

        return (int)sign*res;

    }

    /**
     * 09 回文数
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。
     * 输入：x = 121
     * 输出：true
     */
    @Test
    public void test9(){
        boolean palindrome = isPalindrome(121);
        System.out.println(palindrome);
    }
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        //左右下标
        int l = 0;
        int r = s.length()-1;
        while (l<r){
            if(s.charAt(l) !=s.charAt(r)){
                return false;
            }
            l++;
            r--;

        }
        return true;
    }

    /**
     * 10 正则表达式匹配
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。

     * 输入：s = "aa" p = "a"
     * 输出：false
     * 解释："a" 无法匹配 "aa" 整个字符串。
     */
//    https://leetcode-cn.com/problems/regular-expression-matching
    @Test
    public void test10(){
        boolean match = isMatch("aa","ab*.");
        System.out.println(match);
    }

    public boolean isMatch(String s, String p) {
        int m = s.length() + 1, n = p.length() + 1;
        boolean[][] dp = new boolean[m][n];
        dp[0][0] = true;
        // 初始化首行
        for(int j = 2; j < n; j += 2) {
            dp[0][j] = dp[0][j - 2] && p.charAt(j - 1) == '*';
        }
        // 状态转移
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(p.charAt(j - 1) == '*') {
                    if(dp[i][j - 2]) {
                        dp[i][j] = true;
                    } else if(dp[i - 1][j] && s.charAt(i - 1) == p.charAt(j - 2)) {
                        dp[i][j] = true;
                    } else if(dp[i - 1][j] && p.charAt(j - 2) == '.') {
                        dp[i][j] = true;
                    }
                } else {
                    if(dp[i - 1][j - 1] && s.charAt(i - 1) == p.charAt(j - 1)) {
                        dp[i][j] = true;
                    } else if(dp[i - 1][j - 1] && p.charAt(j - 1) == '.') {
                        dp[i][j] = true;
                    }
                }
            }
        }
        return dp[m - 1][n - 1];
    }


}