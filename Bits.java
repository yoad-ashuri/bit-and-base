//Yoad_Ashuri 311162606, Gilad_Efrati 305687809
public class Bits {

	/**
	 * Given an 8-byte long composed of the bytes B_1, B_2, ... , B_8, return the long with byte order reversed:
	 * B_8, B_7, ..., B_1
	 * The implementation of this method shouldn't use any function calls.
	 * @param a the number to reverse
	 * @return
	 */
	public static long byteReverse(long a) {
		long eightOnes = 0xff;
		long ans = 0;
		for (int i = 0; i < 8; i++) {
			long temp = (a>>(i*8));
			// extracting last 8 bits;
			temp &= eightOnes;
			// make a room for the new 8 bits and insert them
			ans =  ans << 8;
			ans |= temp;
		}
		return ans;
	}

	/**
	 * Given a 32-bit integer composed of 32 bits: b_31,b_30,...b_1,b_0,  return the integer whose bit representation is
	 * b_{31-n},b_{30-n},...,b_1,b_0,b_31,...,b_{31-n+1}.
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls.
	 * @param a the integer that we are rotating left (ROLing)
	 * @param n the number of bits to rotate.
	 * @return the ROL of a
	 */
	public static int rol(int a, int n) {
		return (a<<32-n)|(a>>>n);
	}

	/**
	 * Given two 32-bit integers a_31,...,a_0 and b_31,...,b_0, return the 64-bit long that contains their bits interleaved:
	 * a_31,b_31,a_30,b_30,...,a_0,b_0.
	 * The implementation of this method shouldn't use any function calls.
	 * @param a
	 * @param b
	 * @return
	 */
	public static long interleave(int a, int b) {
		int mask = 0x80000000;
		long ans = 0;
		for (int i=0; i<32; i++) {
			// on each iteration, we isolated a bit from location i, both in a and b. and then inserted to the bit
			ans <<= 1;
			int tempA = a & mask;
			tempA = tempA >>> (31 - i);
			ans|= tempA;
			ans <<= 1;
			int tempB = b & mask;
			tempB>>>= (31 - i);
			ans|= tempB;
			mask>>>=1;
		}
		return ans;
	}

	/**
	 * Pack several values into a compressed 32-bit representation.
	 * The packed representation should contain
	 * <table>
	 * <tr><th>bits</th>	<th>value</th></tr>
	 * <tr><td>31</td>		<td>1 if b1 is true, 0 otherwise</td></tr>
	 * <tr><td>30-23</td>	<td>the value of the byte a</td></tr>
	 * <tr><td>22</td>		<td>1 if b2 is true, 0 otherwise</td></tr>
	 * <tr><td>21-6</td>	<td>the value of the char c</td></tr>
	 * <tr><td>5-0</td>		<td>the constant binary value 101101</td></tr>
	 * </table>
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls
	 * (you may use the conditional operator "?:").
	 * @param a
	 * @param b1
	 * @param b2
	 * @param c
	 * @return
	 */
	public static int packStruct(byte a, boolean b1, boolean b2, char c) {
		// 1 in the beginning as a default, will be checked in the end
		int ans = 1;
		ans <<=31;
		// converting 'a' to an int and locating at the right position
		int newA = a & 0xff;
		newA <<= 23;
		ans |=newA;
		//simple b2 logic
		int newB = b2? 1 : 0;
		newB <<= 22;
		ans |=newB;
		//converting c to an int and locating at the right position
		int C = c & 0xffff;
		C <<= 6;
		ans |=C;
		//simple adding the binary string
		ans |= 0b101101;
		// b1 logic
		return b1? ans : 0x80000000^ans;
	}

	/**
	 * Given a packed struct (with the same format as {@link #packStruct(byte, boolean, boolean, char)}, update
	 * its byte value (bits 23-30) to the new value a.
	 * The implementation of this method shouldn't use any control structures (if-then, loops) or function calls.
	 * @param struct
	 * @param a
	 * @return
	 */
	public static int updateStruct(int struct, byte a) {
		//creating a int mask where zeros in bits 23-30
		int mask = 0x80ffffff;
		// initiating the byte that need to be update
		struct &= mask;
		//make 'a' as an int and locating in the right position
		int A = a & 0xff;
		A<<=23;
		// merging the ints
		struct|=A;
		return struct;
	}
}
