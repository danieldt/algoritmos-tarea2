
public class EndIndex {
	
	private int end;
	
	public EndIndex(int i){
		setEnd(i);
	}
	
	public int getEnd(){
		return end;
	}
	
	public void setEnd(int i){
		end = i;
	}
	
	public int plusPlus(){
		return end++;
	}

}
