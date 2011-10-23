#include<iostream>
#include<stdlib.h>
#include<stdio.h>
using namespace std;

int main()
{
	int map[10][10];
	int qx[100],qy[100],head = 0, tail = 0;

	for(int i=0;i<10;i++)
		for(int j=0;j<10;j++)
			map[i][j]=1000;
	for(int i=0;i<20;i++){
		int x = rand() % 10;
		int y = rand() % 10;
		map[x][y] = -1;
	}

	qx[0] = 5; qy[0] = 2;//start
	map[qx[0]][qy[0]] = 0;
	while(head<=tail){
		int x = qx[head];
		int y = qy[head];
		head++;
		
		int stepx[] = {-1,0,1,0};
		int stepy[] = {0,1,0,-1};
		for(int i=0;i<4;i++){
			int nx = x + stepx[i];
			int ny = y + stepy[i];
			if (nx<0||nx>=10||ny<0||ny>=10||map[nx][ny]<=map[x][y]+1) continue;//||map[nx][ny]==-1
			map[nx][ny] = map[x][y]+1;
			tail++;
			qx[tail] = nx;
			qy[tail] = ny;
		}
	}

	for(int i=0;i<10;i++){
		for(int j=0;j<10;j++)
			printf("%3d ",map[i][j]);
		printf("\n");
	}
	printf("%d",tail);
	
	return 0;
}
