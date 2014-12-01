package puzzle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class Algorithm {
	static int init[][]= {{1 ,14, 5, 13 },{15, 2, 0, 4},{8, 3 ,11, 7},{9, 10, 6 ,12}};
	static int goal[][]= {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
	static int tri [][]= {{1,4,6},{0,8,5},{3,7,2}};
	static int n=4;
	static State state1= new State(init);
	static State state2= new State(goal);
	static int choosen;
	static int numOfMove = 0;
	static int numOfGenerate=0;
	static ArrayList<State> arrState = new ArrayList<State>();
	static void print(State st) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(st.Board[i][j].value+" ");
			}
			System.out.println();
		}
		System.out.println("=====");
	}
	static boolean Equal(State st1,State st2) {
		if(Arrays.deepEquals(st1.Array,st2.Array)) {
			return true;
		}
		return false;
		
	}
	static int misplacing(State st,State st1) {
		int numOfMisplaces=0;
		int tempX=0,tempY=0,tempZ=0,tempT=0;
		int count;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				
				if(st.Board[i][j].value!=st1.Board[i][j].value){
					numOfMisplaces++;
					if(i==0) {
						tempX++;
						tempY++;
					}
					if(i==n-1) {
						tempZ++;
						tempT++;
					}
					if(j==0) {
						tempX++;
						tempZ++;
					}
					if(i==0) {
						tempY++;
						tempT++;
					}
				}
			
			}
			
		}
		if(tempX==4)
			numOfMisplaces++;
		if(tempY==4)
			numOfMisplaces++;
		if(tempZ==4)
			numOfMisplaces++;
		if(tempT==4)
			numOfMisplaces++;
		return numOfMisplaces;
	}
	static int mahatan(State st,State st1) {
		int sum= 0;
		int mahatan = 0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				for(int k=0;k<n;k++) {
					for(int l=0;l<n;l++) {
						
						if(st.Board[i][j].value==st1.Board[k][l].value){
							mahatan= Math.abs(i-k)+Math.abs(j-l);
							break;
						}
					
					}
					
				}
				sum+=mahatan;
			}
			
		}

		return sum;
	}
	static int inventedHeuristic(State st,State st1) {
		int sum= 0,sum1=0,sum2=0,sum3=0,x = 0;
		for(int i=0;i<n-1;i++) {
				if(st.Array[i][0]==st1.Array[i+1][0]) {
					sum++;
				}
				if(st.Array[0][i]==st1.Array[0][i+1]) {
					sum1++;
				}
				
		}
		if(st.Array[n-1][0]==st1.Array[n-1][1])
			sum++;
		if(st.Array[0][n-1]==st1.Array[1][n-1])
			sum1++;
		for(int j=0;j<n;j++) {
			if(st.Array[0][j]==st1.Array[n-1][j]) {
				sum2++;
			}
			if(st.Array[j][0]==st1.Array[n-1][j]) {
				sum3++;
			}
		}
		if(sum==n)
			x+=n;
		if(sum1==n)
			x+=n;
		if(sum2==n)
			x+=n;
		if(sum3==n)
			x+=n;
		//x+=sum4;
			return x;
	}
	static int invented2(State st,State st1){
		int point = 0;
		for (int i = 0; i<n; i++) {
			for (int j = 0; j < n; j++) {
				if(st.Array[i][j]!=st1.Array[i][j])
					point+=(i*n+j);
			}
		}
		return point;
	}
	static boolean Contain(State st) {
		for(int i=0;i<arrState.size();i++) {
			if(Equal(st,arrState.get(i)))
				return true;
		}
		return false;
	}
	static int  NumofMove(State st) {
	
		while(st.father!=null) {
			st=st.father;
			numOfMove++;
		}
		return numOfMove;
	}
	public static void Astar(State Start,State Goal,int Choosen){
		long timerun = System.currentTimeMillis();
		arrState.add(Start);
		Start.father=null;
		if(arrState.isEmpty()) {
			System.out.println("fail found");
			
		}
		while(!arrState.isEmpty()) {
			State temp = arrState.get(0);
			if(Equal(temp,Goal)) {
				System.out.println("success found");
				NumofMove(temp);
				break ;
			}
			temp.InitChild(temp);
			arrState.remove(0);
			numOfGenerate++;
			for(int i=0;i<temp.child.size();i++) {
				if(Equal(temp.child.get(i),Goal)) {
					System.out.println("success found");
					NumofMove(temp);
					return ;
				}
				if(Choosen==1) {
					temp.child.get(i).g= misplacing(Start,temp)+2;
					temp.child.get(i).h= misplacing(temp.child.get(i), Goal);
					temp.child.get(i).func=temp.child.get(i).h+temp.child.get(i).g;
				}
				if(Choosen==2) {
					temp.child.get(i).g= mahatan(Start,temp)+2;
					temp.child.get(i).h= mahatan(temp.child.get(i), Goal);
					temp.child.get(i).func=temp.child.get(i).h+temp.child.get(i).g;
				}
				if(Choosen==3) {
					temp.child.get(i).g= inventedHeuristic(Start,temp)+2;
					temp.child.get(i).h= inventedHeuristic(temp.child.get(i), Goal);
					temp.child.get(i).func=temp.child.get(i).h+temp.child.get(i).g;
				}
				if(Choosen==4) {
					temp.child.get(i).g= invented2(Start,temp)+invented2(temp,temp.child.get(i));
					temp.child.get(i).h= invented2(temp.child.get(i), Goal);
					temp.child.get(i).func=temp.child.get(i).h+temp.child.get(i).g;
				}
				if(!Contain(temp.child.get(i)))
				arrState.add(temp.child.get(i));
			}
			Collections.sort(arrState, new Comparator<State>() {
		        @Override
		        public int compare(State  st1, State  st2)
		        {
		            return  st1.func-st2.func;
		        }
		    });
			System.out.println("arrState: "+arrState.size());
			System.out.println("numOfgenerate: "+numOfGenerate);
			long TimeEnd=System.currentTimeMillis();
			long aboutTime = TimeEnd-timerun;
//			if(aboutTime==5000) {
//				System.out.println("no solution");
//				break;
//			}
			
		}
		print(Goal);
	}
	static void readfile() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		String filePath = new File("").getAbsolutePath();
        try
        {    
        	BufferedReader reader = new BufferedReader(new FileReader(filePath + "/nPuzzle3.inp"));                       
            String line = "";  
            int x,i,j;
            while ((line=reader.readLine()) != null)
            { 
            	line=line.trim();
            	if(!line.isEmpty()) {
	            	String[] a= line.split(" ");
	            	for(String s:a) {
	            		array.add(Integer.parseInt(s));
	            	}
            	}
            }   
            System.out.println(array.size());
            n=array.get(0);
            choosen=array.get(1);
            init= new int [n][n];
            goal= new int [n][n];
            for(i =0;i<n;i++) {
            	for(j=0;j<n;j++) {
            		init[i][j]=array.get(i*n+j+2);
            	}
            }
            for(i =0;i<n;i++) {
            	for(j=0;j<n;j++) {
            		goal[i][j]=array.get(n*n+2+i*n+j);
            	}
            }
            state1= new State(init);
            state2= new State(goal);
        }
        catch (IOException ex)
        {
        	ex.printStackTrace();
            System.out.println("read file hasn't failed");;
        }                     
	}
	static void writefile() {
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("nPuzzle.out"), "utf-8"));
		    writer.write(numOfMove+"  "+numOfGenerate);
		} catch (IOException ex) {
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	}
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		readfile();
		Scanner sc = new Scanner(System.in);
		System.out.println("nhap vao ham heuristic: ");
		System.out.println("1:h1= num of misplaced tiles"+'\n'+"2:h2= Mahatan distances"+'\n'
		+"3:new invented heuristics ");
		choosen=sc.nextInt();

		long startTime = System.currentTimeMillis();
		Astar(state1,state2,choosen);
		long endTime = System.currentTimeMillis();
		long period = endTime-startTime;
		System.out.println("number of generated state: "+numOfGenerate);
		System.out.println("number of move: "+numOfMove);
		System.out.println("Took "+(endTime - startTime) + "ms"); 
		writefile();
	}
	

}
