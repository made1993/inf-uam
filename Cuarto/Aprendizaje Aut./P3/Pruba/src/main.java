import java.util.BitSet;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BitSet a = new BitSet(3);
		a.set(0);
		a.set(2);
		BitSet b = new BitSet(3);
		b.set(1);
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.intersects(b));

	}

}
