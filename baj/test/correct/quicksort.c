#include <stdio.h>
int i = 1;
#define LEN 10
int data[LEN] = {123,52,8,74,62,74,55,44,74,80};
void printArray(int a[]) {
  int i = 0;  char* str = "\n    Member ";
  while (i < LEN) {
    printf("%s", str); printf("%d", i); printf(" is ");
    printf("%d", a[i]); i=i+1;
  }
}
int partition(int a[], int low, int high) {
  int pivot = a[low], i = low, j = high;
  while (i < j) {
    while (i<j && a[j] >= pivot) j=j-1;
    a[i] = a[j];
    while (i<j && a[i] <= pivot) i=i+1;
    a[j] = a[i];
  }
  a[i] = pivot;
  return i;
} // end of partition
void readArray( ) {
  int i = 0;
  printf("Please input 10 integers to be sorted.\n");
  while (i < LEN) {
    scanf("%d", &data[i]); i=i+1;
  }
}
void qsort(int a[], int low, int high) {
  if (low >= high) return;
  int p = partition(a, low, high);
  qsort(a, low, p-1);
  qsort(a, p + 1, high);
}
void main() {
  int flag;
  while (1){
    if (i>1 ){
      printf("\nContinue? 1-yes, 0-no: ");
      scanf("%d", &flag);
      if (flag == 0 ) break;
      else if (flag!=1) continue;
      readArray( );
    }
    printf("\nExecuting No."); 
    printf("%d", i); 
    printf(" quicksort:\n  Before sorting:");
    printArray(data);
    qsort(data, 0, LEN-1);
    printf("\n  After sorting:");
    printArray(data);
    i = i+1;
  }
}