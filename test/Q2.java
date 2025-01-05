public class Q2 {
  public static int findMissingNumber(int[] nums) {
    int totalSum = (nums.length + 1) * (nums.length + 2) / 2; // Tổng các số từ 1 đến n+1
    int arraySum = 0;
    // Tính tổng các phần tử trong mảng
    for (int num : nums) {
      arraySum += num;
    }
    // Số bị thiếu là hiệu giữa tổng lý thuyết và tổng thực tế
    return totalSum - arraySum;
  }
  public static void main(String[] args) {
    int[] nums = { 3, 7, 1, 2, 6, 4 }; // Mảng đầu vào
    int missingNumber = findMissingNumber(nums);
    System.out.println("So con thieu la: " + missingNumber);
  }

}
