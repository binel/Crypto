package prng;

public class BlumBlumShub {
	private int state;
	private int M; 
	
	public BlumBlumShub() {
		this.M = 11 * 19; 
		this.state = 3; 
	}
	
	public int nextInt() {
		int num = 0x0000; 
		for(int i = 0; i < 32; i++) {
			this.state = (int) Math.pow(this.state, 2) % M; 
			num = (num << 1) | (this.state & 0x0001);
		}
		
		return num; 
	}
	
	public static void main(String args[]) {
		BlumBlumShub gen = new BlumBlumShub();
		for(int i = 0; i < 20; i++) {
			System.out.println(gen.nextInt());
		}
	}
}
