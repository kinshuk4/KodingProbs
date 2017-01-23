package com.trivago.pipeline;

import java.util.Arrays;

public class Temp {
	public static void main(String[] args){
		int[] op = findMaximumNumber(new int[] {1,2,3}, 2);
		System.out.println(Arrays.toString(op));
	}
    static int[] findMaximumNumber(int[] array, int moves) {
    	String str = toStr(array);
    	
    	String finalOp = "";
    	findMaximumNum(str, array, moves,finalOp);
    	
    	int[] intArray = new int[array.length];
        for(int i=0;i<array.length;i++) {
            if (!Character.isDigit(finalOp.charAt(i))) {
              System.out.println("Contains an invalid digit");
              break;
            }
            intArray[i] = Integer.parseInt(String.valueOf(finalOp.charAt(i)));
          }
        return intArray;

    }
    
	static void findMaximumNum(String str, int[] arr, int k, String max)
	{	
	    // return if no swaps left
	    if(k == 0)
	        return;
	 
	     int n = str.length();
	    // consider every digit
	    for (int i = 0; i < n-1; i++)
	    {
	      
	        // and compare it with all digits after it
	        for (int j = i + 1; j < n; j++)
	        {
	            // if digit at position i is less than digit
	            // at position j, swap it and check for maximum
	            // number so far and recurse for remaining swaps
	            if (arr[i] < arr[j])
	            {
	                // swap str[i] with str[j]
	                swap(arr, i, j);
	 
	                // If current num is more than maximum so far
	                max = toStr(arr);
	                if (str.compareTo(max) > 0)
	                    max = str;
	 
	                // recurse of the other k - 1 swaps
	                findMaximumNum(str, arr, k - 1, max);
	 
	                // backtrack
	                swap(arr, i, j);
	            }
	        }
	    }
	}
	public static String toStr(int[] arr){
		String s = "";
		for(int i:arr){
			s = s+i;
		}
		return s;
	}
	public static void swap(int[] str, int i, int j){
		int temp = str[i];
		str[i] = str[j];
		str[j] = temp;
	}
}

