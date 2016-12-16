package SuffixTree;
import java.util.List;

public class SearchResult {
	private long tiempo;
	private List<Integer> res;
	public SearchResult(List<Integer> result, long tiempo) {
		this.tiempo=tiempo;
		this.res=result;
	}
	public long getTime() {
		
		return tiempo;
	}
	public List<Integer> getResults(){
		return res;
	}
}
