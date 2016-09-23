
public class testDocPro {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DocumentProcessing test1 = new DocumentProcessing();
		String a = "$$$%`~GunDamWing*^></$$$";
		String b = "Naruto's-Shippuden";
		String c = "'Bleach'Infinity'Stratus'";
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println();
		System.out.println(test1.NormalizeToken(a));
		System.out.println(test1.NormalizeToken(c));
		System.out.println(test1.SplitHyphenWord(b));
	}

}
