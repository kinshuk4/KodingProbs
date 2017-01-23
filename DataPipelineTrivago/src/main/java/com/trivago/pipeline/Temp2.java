package com.trivago.pipeline;

import java.util.Arrays;

public class Temp2 {
	public static int[] maximize(int[] arr, int swapsAll, int startIndex) {
		int maxIndex = 0;
		int maxi = 0;
		for (int i = startIndex; i < min(arr.length - startIndex, swapsAll) + startIndex + 1; i++) {
			if (arr[i] > maxi) {
				maxi = arr[i];
				maxIndex = i;
			}

		}

		int count = swapsAll;
		while (count > 0 && maxIndex > startIndex) {
			swap(arr, maxIndex, maxIndex - 1);
			System.out.println(arr[maxIndex] + "," + arr[maxIndex - 1]);
			count--;
			maxIndex--;
		}
		if (count >= 0 && startIndex + 1 < arr.length)
			arr = maximize(arr, count, startIndex + 1);
		return arr;
	}
	
//	public static int min(int i, int j){
//		if(i<j){
//			return i;
//		}else{
//			return j;
//		}
//	}

	public static void swap(int[] str, int i, int j) {
		int temp = str[i];
		str[i] = str[j];
		str[j] = temp;
	}

	public static void main(String[] args) {
		int[] op = maximize(new int[] { 1, 2, 3 }, 2, 0);
		System.out.println(Arrays.toString(op));
	}

}
