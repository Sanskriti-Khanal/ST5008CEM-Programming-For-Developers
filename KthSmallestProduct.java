import java.util.Arrays;

public class KthSmallestProduct {
    public static void main(String[] args) {
        // Example 1
        int[] returns1 = {2, 5};
        int[] returns2 = {3, 4};
        int k = 2;
        int[] products = new int[returns1.length * returns2.length]; 
        for(int i=0,idx=0;i<returns1.length;i++)for(int j=0;j<returns2.length;j++)products[idx++]=returns1[i]*returns2[j]; 
        Arrays.sort(products); 
        System.out.println("Example 1 Output: " + products[k-1]);

        // Example 2
        int[] returns1_2 = {-4, -2, 0, 3};
        int[] returns2_2 = {2, 4};
        int k2 = 6;
        int[] products2 = new int[returns1_2.length * returns2_2.length]; 
        for(int i=0,idx=0;i<returns1_2.length;i++)for(int j=0;j<returns2_2.length;j++)products2[idx++]=returns1_2[i]*returns2_2[j]; 
        Arrays.sort(products2); 
        System.out.println("Example 2 Output: " + products2[k2-1]);
    }
}
