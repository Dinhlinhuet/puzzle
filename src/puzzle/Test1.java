package puzzle;

public class Test1 {
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		//Algorithm.print(Algorithm.state1);
	//	Algorithm.print(Algorithm.state2);
		//Algorithm.state1.InitChild(Algorithm.state1);
		//State sta=Algorithm.state1.child.get(0);
//		for (int i = 0; i < Algorithm.state1.child.size(); i++) {
//			Algorithm.print(Algorithm.state1.child.get(i));
//		}
//		sta.InitChild(sta);
//		State stb=sta.child.get(0);
//		for (int i = 0; i < sta.child.size(); i++) {
//			Algorithm.print(sta.child.get(i));
//		}
		Algorithm.Astar(Algorithm.state1,Algorithm.state2,4);
		System.out.println("number of generated state: "+Algorithm.numOfGenerate);
		System.out.println("number of move: "+Algorithm.numOfMove);
		long endTime = System.currentTimeMillis();
		System.out.println("Took "+(endTime - startTime) + " ms");
		//Algorithm.print(Algorithm.state4);
		//Algorithm.readfile();
	}

}
