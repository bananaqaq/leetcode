import java.util.HashMap;
import java.util.Map;

public class algorithm1_5 {
    public static void main(String[] args) {
        // 1.两数之和
        /*int[] nums = {1, 2, 3, 4, 5};
        int target = 4;
        int[] result = twoSum(nums, target);
        for (int i : result) {
            System.out.println(i);
        }*/

        // 2.两数相加
        /*ListNode l1 = new ListNode(2);
        ListNode l11 = new ListNode(4);
        ListNode l111 = new ListNode(3);
        l1.next = l11;
        l11.next = l111;
        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        ListNode l222 = new ListNode(4);
        l2.next = l22;
        l22.next = l222;

        ListNode ll = addTwoNumbers(l1, l2);
        System.out.println(ll.val + " " + ll.next.val + " " + ll.next.next.val);*/

        // 3.无重复字符的最长子串
        /*String str1 = "aabbccabde";
        String str2 = "bbbbb";
        String str3 = "pwwkew";
        String str4 = "abcxxabcdabcdef";
        System.out.println(lengthOfLongestSubstring(str1));
        System.out.println(lengthOfLongestSubstring(str2));
        System.out.println(lengthOfLongestSubstring(str3));
        System.out.println(lengthOfLongestSubstring(str4));*/

        // 5.寻找两个数组的中位数
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println(findMedianSortedArrays(nums1, nums2));

        int[] nums3 = {1, 2};
        int[] nums4 = {3, 4};
        System.out.println(findMedianSortedArrays(nums3, nums4));

    }

    // 1.两数之和
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    // 2.两数相加
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = p != null ? p.val : 0;
            int y = q != null ? q.val : 0;
            int sum = x + y + carry;
            curr.next = new ListNode(sum % 10);
            carry = sum / 10;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
            curr = curr.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    // 3.无重复字符的最长子串
    public static int lengthOfLongestSubstring(String s) {
        // my
        /*char[] charArr = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        ArrayList<Integer> diffList = new ArrayList<>();
        int result = 0;
        int length = 0;
        for (int i = 0; i < charArr.length; i++) {
            if(!map.containsKey(charArr[i])){
                length++;
                map.put(charArr[i], i);
            }else{
                if(length > result){
                    result = length;
                }
                diffList.add(length);
                length = 1;
                map = new HashMap<>();
                map.put(charArr[i], i);
            }
        }
        if(length > result){
            result = length;
        }


        return result;*/

        // 滑动窗口
        /*Set<Character> set = new HashSet<>();
        int n = s.length();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;*/

        // 滑动窗口优化 1
        /*int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;*/


        /*int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0, j = 0; j < n; j++){
            if(map.containsKey(s.charAt(j))){
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i);
            map.put(s.charAt(j),  j);
        }
        return ans;*/

        // 滑动窗口优化 2
        int n = s.length(), ans = 0;
        int[] index = new int[128];
        for (int i = 0, j = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    // 5.寻找两个数组的中位数
    public static double findMedianSortedArrays(int[] A, int[] B) {

        int m = A.length;
        int n = B.length;
        if (m > n) { // to ensure m<=n
            int[] temp = A;
            A = B;
            B = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i < iMax && B[j - 1] > A[i]) {
                iMin = i + 1; // i is too small
            } else if (i > iMin && A[i - 1] > B[j]) {
                iMax = i - 1; // i is too big
            } else { // i is perfect
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                } else {
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                int minRight = 0;
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                } else {
                    minRight = Math.min(B[j], A[i]);
                }

                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

}


class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
