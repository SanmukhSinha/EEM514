#define F(A,B) A*B
int z= 20;
#define MAX 10
int y;
#define MIN -100
int x;
int f1 () {
int x,y;
x = y;
y++;
return y;
}
int main ( )
{
printf("%d",f1());
int a[3]={MIN , MAX, F(10,20)};
return 0;
}
