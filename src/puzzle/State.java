package puzzle;

import java.util.ArrayList;
import java.util.Arrays;

public class State {
	Tile [][]Board = new Tile[Algorithm.n][Algorithm.n];
	int [][] Array= new int [Algorithm.n][Algorithm.n];
	Tile blank;
	State father;
	int func,g,h;
	ArrayList<State> child = new ArrayList<State>();
	public State(int [][]tl) {
		// TODO Auto-generated constructor stub
		for(int i=0;i<Algorithm.n;i++) {
			for(int j=0;j<Algorithm.n;j++) {
				Array[i][j]= tl[i][j];
				Board[i][j]= new Tile(i,j,Array[i][j]);
				if(Board[i][j].value==0) {
					blank= new Tile(i, j, tl[i][j]);
				}
			}
			
		}
	}
	public void initArray(Tile [][] board) {
		for(int i=0;i<Algorithm.n;i++) {
			for(int j=0;j<Algorithm.n;j++) {
			Array[i][j]= board[i][j].value;
			}
		}
	}
	static void SwapTile(Tile tile1,Tile tile2) {
		int temp;
		temp= tile1.value;
		tile1.value = tile2.value;
		tile2.value = temp;
	}
	static boolean checkBack(State st,State st1) {
		State temp = new State(st1.Array);
		temp.father=st1.father;
		while(temp.father!=null) {
			if(Algorithm.Equal(st, temp.father)) {
				return true;
			}
			temp=temp.father;
		}
		return false;
	}
	void InitChild(State st) {
		Algorithm.print(st);
//		boolean checkFather = Algorithm.Equal(st, Algorithm.state1);
//		if(checkFather) st.father=null;
		State newchild = new State(st.Array);
		State newchild4 = new State(st.Array);
		int a=st.blank.x;
		int b=st.blank.y;
		if(a==Algorithm.n-1) {
			newchild.blank.x--;
			SwapTile(newchild.Board[a][b],
					newchild.Board[newchild.blank.x][newchild.blank.y]);
			newchild.initArray(newchild.Board);
			if(!checkBack(newchild, st)){
				st.child.add(newchild);
				newchild.father=st;	
			}
		}
		if(a==0) {
			newchild.blank.x++;
			SwapTile(newchild.Board[a][b],
					newchild.Board[newchild.blank.x][newchild.blank.y]);
			newchild.initArray(newchild.Board);		
			if(!checkBack(newchild, st)){
						st.child.add(newchild);
						newchild.father=st;	
			}
		}
		if(a>0&&a<Algorithm.n-1) {			
			newchild.blank.x++;
			SwapTile(newchild.Board[a][b],
					newchild.Board[newchild.blank.x][newchild.blank.y]);
			newchild.initArray(newchild.Board);
			if(!checkBack(newchild, st)){
				st.child.add(newchild);
				newchild.father=st;	
			}
			
			newchild4.blank.x--;
			SwapTile(newchild4.Board[a][b],
					newchild4.Board[newchild4.blank.x][newchild4.blank.y]);
			newchild4.initArray(newchild4.Board);
			if(!checkBack(newchild4, st)){
				st.child.add(newchild4);
				newchild4.father=st;	
			}
		}
		
		State newchild1 = new State(st.Array);
		if(b==Algorithm.n-1) {
			newchild1.blank.y--;
			SwapTile(newchild1.Board[a][b],
					newchild1.Board[a][newchild1.blank.y]);
			newchild1.initArray(newchild1.Board);
			if(!checkBack(newchild1, st)){
				st.child.add(newchild1);
				newchild1.father=st;	
			}
		}
		if(b==0) {
			newchild1.blank.y++;
			SwapTile(newchild1.Board[a][b],
					newchild1.Board[a][newchild1.blank.y]);
			newchild1.initArray(newchild1.Board);
			if(!checkBack(newchild1, st)){
				st.child.add(newchild1);
				newchild1.father=st;	
			}
		}
		if(b>0&b<Algorithm.n-1) {
			newchild1.blank.y++;
			SwapTile(newchild1.Board[a][b],
					newchild1.Board[newchild1.blank.x][newchild1.blank.y]);
			newchild1.initArray(newchild1.Board);
			if(!checkBack(newchild1, st)){
				st.child.add(newchild1);
				newchild1.father=st;	
			}
			
			State newchild3 = new State(st.Array);			
			newchild3.blank.y--;
			SwapTile(newchild3.Board[a][b],
					newchild3.Board[newchild3.blank.x][newchild3.blank.y]);
			newchild3.initArray(newchild3.Board);
			if(!checkBack(newchild3, st)){
				st.child.add(newchild3);
				newchild3.father=st;	
	
			}
		}
	}
}
