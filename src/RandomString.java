
	public class RandomString {
	String str = "";
	public String getRandomString(String s) {
		StringBuffer strBuffer = new StringBuffer(s);
		int m = strBuffer.length();
		for (int k = 0; k < m; k++) {
			int index = (int) (Math.random() * strBuffer.length());
			char c = strBuffer.charAt(index);
			str = str + c;
			strBuffer = strBuffer.deleteCharAt(index);
		}
		return str;
	}
}
