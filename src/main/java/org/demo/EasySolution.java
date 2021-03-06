package org.demo;

import Model.LeetTreeNode;
import Model.ListNode;

import java.util.*;
import java.util.stream.Collectors;

public class EasySolution {

    /**
     * 判断x是否为回文数
     *
     * @param x 判断参数
     * @return 结果
     */
    public boolean isPalindrome(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int number = 0;
        while (x > number) {
            number = number * 10 + x % 10;
            x /= 10;
        }
        return x == number || x == number / 10;
    }

    /**
     * 两数之和
     *
     * @param nums
     * @param target 目标值
     * @return 返回数组下标
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (hashMap.containsKey(complement)) {
                return new int[]{hashMap.get(complement), i};
            }
            hashMap.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    //两数之和 有序数组
    public int[] twoSum0(int[] numbers, int target) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            if (numbers[l] + numbers[r] == target) {
                return new int[]{l++, r++};
            } else if (numbers[l] + numbers[r] < target)
                l++;
            else
                r--;
        }
        return null;
    }

    /**
     * 两数相加
     *
     * @param l1 链表1
     * @param l2 链表2
     * @return 相加结果
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode p = l1, q = l2, curr = result;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p == null) ? 0 : p.val;
            int y = (q == null) ? 0 : q.val;
            int sum = x + y + carry;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return result.next;
    }


    /**
     * 两个数的最小公倍数
     * m * n / 最大公约数
     *
     * @return
     */
    public int getMinNumber(int m, int n) {
        int max = getMaxNumber(m, n);
        return m * n / max;
    }

    /**
     * 多个数的最小公倍数
     *
     * @param s
     * @return
     */

    public int getMinNumbers(int s[]) {
        int result = getMinNumber(s[0], s[1]);
        ;
        for (int i = 2; i < s.length; i++) {
            result = getMinNumber(result, s[i]);
        }
        return result;
    }

    /**
     * 最大公约数
     *
     * @return
     */
    public int getMaxNumber(int m, int n) {
        if (n > m) {
            int x = m;
            m = n;
            n = x;
        }

        int result = 0;
        while (n != 0) {
            result = m % n;
            m = n;
            n = result;
        }
        return m;
    }

    /**
     * 多个数的最大公约数
     *
     * @param s
     * @return
     */
    public int getMaxNumbers(int s[]) {
        int max = getMaxNumber(s[0], s[1]);
        for (int i = 2; i < s.length; i++) {
            max = getMaxNumber(max, s[i]);
        }
        return max;
    }


    /// <summary>
    /// KMP算法查找字符串
    /// </summary>
    /// <param name="haystack">操作字符串</param>
    /// <param name="needle">要查找的字符串</param>
    /// <returns>字符串第一次出现的位置索引</returns>
    public static int arithmetic_KMP(String haystack, String needle) {
        int index = -1;   //正确匹配的开始索引
        int[] tableValue = getPartialMatchTable(needle);
        int i = 0, j = 0; //操作字符串和匹配字符串 索引迭代
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) //当第一个字符匹配上，接着匹配第二、、、
            {
                if (j == 0) index = i;       //记录第一个匹配字符的索引
                j++;
                i++;
            } else  //当没有匹配上的时候
            {
                if (j == 0) //如果第一个字符就没匹配上
                {
                    i = i + j + 1 - tableValue[j]; //移动位数 =已匹配的字符数 - 对应的部分匹配值
                } else {
                    i = index + j - tableValue[j - 1]; //如果已匹配的字符数不为零，则重新定义i迭代
                }
                j = 0; //将已匹配迭代置为0
            }
        }
        return index;
    }

    /// <summary>
    /// 产生 部分匹配表
    /// </summary>
    /// <param name="str">要查找匹配的字符串</param>
    /// <returns></returns>
    private static int[] getPartialMatchTable(String str) {
        String[] left, right; //前缀、后缀
        int[] result = new int[str.length()]; //保存 部分匹配表
        for (int i = 0; i < str.length(); i++) {
            left = new String[i]; //实例化前缀 容器
            right = new String[i]; //实例化后缀容器
            //前缀
            for (int j = 0; j < i; j++) {
                if (j == 0)
                    left[j] = String.valueOf(str.charAt(j));
                else
                    left[j] = left[j - 1] + str.charAt(j);
            }
            //后缀
            for (int k = i; k > 0; k--) {
                if (k == i)
                    right[k - 1] = String.valueOf(str.charAt(k));
                else
                    right[k - 1] = str.charAt(k) + right[k];
            }
            //找到前缀和后缀中相同的项，长度即为相等项的长度（相等项应该只有一项）
            int num = left.length - 1;
            for (int m = 0; m < left.length; m++) {
                if (right[num] == left[m]) {
                    result[i] = left[m].length();
                }
                num--;
            }
        }
        return result;
    }

    //移除元素
    public int removeElement(int[] nums, int val) {
        int i = 0;
        int len = nums.length;
        while (i < len) {
            if (nums[i] == val) {
                nums[i] = nums[--len];
            } else {
                i++;
            }
        }
        return len;
    }

    //删除链表重复项
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }


    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * public int val;
     * public ListNode next;
     * public ListNode(int x) { val = x; }
     * }
     */
    //合并两个有序链表
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode p = new ListNode(0);
        ListNode d = p;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                d.next = new ListNode(l2.val);
                l2 = l2.next;
            } else {
                d.next = new ListNode(l1.val);
                l1 = l1.next;
            }
            d = d.next;
        }

        if (l1 != null) {
            d.next = l1;
        } else if (l2 != null) {
            d.next = l2;
        }
        return p.next;
    }

    //最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        for (int i = 0; i < strs[0].length(); ) {
            boolean p = true;
            char msg = strs[0].charAt(i);
            for (int k = 1; k < strs.length; k++) {
                if (strs[k].length() == 0) return "";
                if (strs[k].length() - 1 < i) p = false;
                p = p && strs[k].charAt(i) == msg;
            }
            i++;
            if (!p || strs[0].length() - 1 < i) return strs[0].substring(0, p ? i : i - 1);
        }
        return "";
    }

    //搜索插入位置
    public int searchInsert(int[] nums, int target) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) return i;

            if (nums[i] < target) index++;

            if (nums[i] > target) return index;
        }
        return index;
    }

    //最大子序和
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum > 0)
                sum += nums[i];
            else
                sum = nums[i];
            res = res > sum ? res : sum;
        }
        return res;
    }

    //最后一个单词的长度
    public int lengthOfLastWord(String s) {
        return s.trim().split(" ")[s.trim().split(" ").length - 1].length();
    }

    //加一
    public int[] plusOne(int[] digits) {
        boolean p = true;
        for (int i = digits.length - 1; i >= 0 && p; i--) {
            if (digits[i] == 9) {
                p = true;
                digits[i] = 0;
                if (i == 0) {
                    int[] res = new int[digits.length + 1];
                    res[0] = 1;
                    System.arraycopy(digits, 0, res, 1, digits.length);
                    return res;
                }
            } else {
                p = false;
                digits[i] += 1;
            }
        }
        return digits;
    }

    /**
     * 二进制求和
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        a = new StringBuilder(a).reverse().toString();
        b = new StringBuilder(b).reverse().toString();
        StringBuilder result = new StringBuilder();
        int temp = 0;
        for (int i = 0; i < Math.max(a.length(), b.length()); i++) {
            int x = (i >= a.length()) ? 0 : Integer.valueOf(String.valueOf(a.charAt(i)));
            int y = (i >= b.length()) ? 0 : Integer.valueOf(String.valueOf(b.charAt(i)));
            int sum = x + y + temp;
            temp = sum / 2;
            result.append(String.valueOf(sum % 2));
        }
        if (temp == 1)
            result.append(String.valueOf(temp));
        return result.reverse().toString();
    }

    //x的平方根
    //神奇的 0x5f3759df
    public int mySqrt(int x) {
        long t = x;
        t = 0x5f3759df - (t >> 1);
        while (!(t * t <= x && (t + 1) * (t + 1) > x))
            t = (x / t + t) / 2;
        return (int) t;
    }

    //爬楼梯
    //假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
    //每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    //本质就是斐波那契数列
    public int climbStairs(int n) {
        if (n == 1 || n == 0) return 1;
        else if (n == 2) return 2;
        else {
            int f1 = 1;
            int f2 = 2;
            int f3 = 0;
            for (int i = 2; i < n; i++) {
                f3 = f2 + f1;
                f1 = f2;
                f2 = f3;
            }
            return f3;
        }
    }

    //删除排序列表的重复元素
    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.next.val == current.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }

    //删除未排序的List
    public ListNode deleteDuplicates(ListNode head, boolean continuous) {
        if (continuous) {
            return deleteDuplicates(head);
        } else {
            if (head == null) return head;
            Set<Integer> set = new HashSet<Integer>();
            set.add(head.val);
            ListNode next = head;
            while (next != null && next.next != null) {
                if (!set.add(next.next.val)) {
                    next.next = next.next.next;
                } else {
                    next = next.next;
                }
            }
            return head;
        }
    }

    //两颗二叉树是否相同
    public boolean isSameTree(LeetTreeNode p, LeetTreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p != null && q != null && p.val == q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            return false;
        }
    }

    /**
     * 对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(LeetTreeNode root) {
        return isMirror(root, root);
    }

    private boolean isMirror(LeetTreeNode l1, LeetTreeNode l2) {
        if (l1 == null && l2 == null) return true;
        if (l1 == null || l2 == null) return false;
        return (l1.val == l2.val) && isMirror(l1.left, l2.right) && isMirror(l1.right, l2.left);
    }

    //合并两个有序数据
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p = m + n - 1;
        m--;
        n--;
        while (m >= 0 && n >= 0) {
            nums1[p] = nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
            p--;
        }

        while (n >= 0) {
            nums1[p] = nums2[n];
            p--;
            n--;
        }
    }

    //二叉树的最大深度
    public int maxDepth(LeetTreeNode root) {
        return root == null ? 0 : Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    //反转字符串中的每个单词
    public String reverseWords(String s) {
        return Arrays.stream(s.split(" ")).map(x -> {
            return new StringBuilder(x).reverse();
        }).collect(Collectors.joining(" "));
    }

    //Nim游戏 如果堆中石头的数量 nnn 不能被 444 整除，那么你总是可以赢得 Nim 游戏的胜利。巴什博奕
    public boolean canWinNim(int n) {
        return (n % 4 != 0);
    }


    //存在重复元素
    public boolean containsDuplicate(int[] nums) {
        Set set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    //只出现一次的数字
    public int singleNumber(int[] nums) {
        int num = 0;
        for (int i = 0; i < nums.length; i++) {
            num ^= nums[i];
        }
        return num;
    }

    //2的幂 特性为二进制  二进制为 10000  头位1 后面都是0的数字
    //例：二进制为
    // 16   10000
    // 15   01111
    // &    00000
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        if ((n & n - 1) == 0) return true;
        return false;
    }

    //众数 众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
    //你可以假设数组是非空的，并且给定的数组总是存在众数。
    public int majorityElement(int[] nums) {
        int count = 1;
        int maj = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (maj == nums[i])
                count++;
            else {
                count--;
                if (count == 0) {
                    maj = nums[i + 1];
                }
            }
        }
        return maj;
    }

    //反转链表
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return head;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode prev = dummy.next;
        ListNode pCur = prev.next;
        while (pCur != null) {
            prev.next = pCur.next;
            pCur.next = dummy.next;
            dummy.next = pCur;
            pCur = prev.next;
        }
        return dummy.next;
    }

    //阶乘后的0
    //首先题目的意思是末尾有几个0
    //    比如6! = 【1* 2* 3* 4* 5* 6】
    //    其中只有2*5末尾才有0，所以就可以抛去其他数据 专门看2 5 以及其倍数 毕竟 4 * 25末尾也是0
    //    比如10！ = 【2*4*5*6*8*10】
    //    其中 4能拆成2*2  10能拆成2*5
    //    所以10！ = 【2*（2*2）*5*（2*3）*（2*2*2）*（2*5）】
    //    一个2和一个5配对 就产生一个0 所以10！末尾2个0
    //    转头一想 2肯定比5多 所以只数5的个数就行了
    //    假若N=31 31里能凑10的5为[5, 2 * 5, 3 * 5, 4 * 5, 25, 6 * 5] 其中 25还能拆为 5**2
    //    所以 里面的5的个数为 int (31/(5**1)) +  int (31/(5**2))
    //    所以 只要先找个一个 5** x<n 的x的最大数 然后按上面循环加起来
    public int trailingZeroes(int n) {
        int count = 0;
        while (n / 5 > 0) {
            count += n / 5;
            n /= 5;
        }
        return count;
    }

    //不使用+ -计算两数之和
    //两个整数a, b; a ^ b是无进位的相加； a&b得到每一位的进位；让无进位相加的结果与进位不断的异或， 直到进位为0；
    public int getSum(int a, int b) {
        return b == 0 ? a : getSum(a ^ b, (a & b) << 1);
    }

    //降维打击
    //各位相加
    //输入: 38
    //输出: 2
    //解释: 各位相加的过程为：3 + 8 = 11, 1 + 1 = 2。 由于 2 是一位数，所以返回 2。
    public int addDigits(int num) {
        return 1 + (num - 1) % 9;
    }

    //从1到N缺失的数字
    public int missingNumber(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        return ((1 + nums.length) * nums.length / 2) - sum;
    }

    //字符串中的第一个唯一字符
    public int firstUniqChar(String s) {
        int x;
        int y;
        for (int i = 0; i < s.length(); i++) {
            x = s.indexOf(s.charAt(i));
            y = s.lastIndexOf(s.charAt(i));
            if (x == y) {
                return i;
            }
        }
        return -1;
    }

    //有效的完全平方数
    //1+3+5+7+9+…+(2n-1)=n^2，即完全平方数肯定是前n个连续奇数的和
    public boolean isPerfectSquare(int num) {
        int sumnum = 1;
        while (num > 0) {
            num -= sumnum;
            sumnum += 2;
        }

        return num == 0;
    }

    //数字的补数
    //给定一个正整数，输出它的补数。补数是对该数的二进制表示取反。
    public int findComplement(int num) {
        int temp = num, c = 0;
        while (temp > 0) {
            temp >>= 1;
            c = (c << 1) + 1;
        }
        return num ^ c;
    }

    //相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        /**
         定义两个指针, 第一轮让两个到达末尾的节点指向另一个链表的头部, 最后如果相遇则为交点(在第一轮移动中恰好抹除了长度差)
         两个指针等于移动了相同的距离, 有交点就返回, 无交点就是各走了两条指针的长度
         **/
        if (headA == null || headB == null) return null;
        ListNode pA = headA, pB = headB;
        // 在这里第一轮体现在pA和pB第一次到达尾部会移向另一链表的表头, 而第二轮体现在如果pA或pB相交就返回交点, 不相交最后就是null==null
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    //路径总和  是否满足sum的值
    public boolean hasPathSum(LeetTreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return sum - root.val == 0;
        }
        return hasPathSum(root.left, sum - root.val)
                || hasPathSum(root.right, sum - root.val);
    }

}

