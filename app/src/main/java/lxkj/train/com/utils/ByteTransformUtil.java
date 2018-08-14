package lxkj.train.com.utils;

/**
 * Created by dell on 2018/7/16.
 */

public class ByteTransformUtil {

    /**
     * 切换大小端续
     */
    public static byte[] changeBytes(int data){
        byte[] a = intToBytes(data);
        byte[] b = new byte[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[b.length - i - 1];
        }
        return b;
    }
    /**
     * 将byte[]数值转换为int
     */
    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes2(int value)
    {
        byte[] src = new byte[4];
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }
    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }

    /**
     * 注释：short到字节数组的转换！
     *
     * @param
     * @return
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();//
                    temp = temp >> 8; // 向右移8位
        }
        return b;
    }
    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param
     * @return
     */
    public static short getShort(byte[] b) {
        return (short) (((b[1] << 8) | b[0] & 0xff));
    }
    public static long getLong(byte[] bb) {
        return ((((long) bb[ 0] & 0xff) << 56)
                | (((long) bb[ 1] & 0xff) << 48)
                | (((long) bb[ 2] & 0xff) << 40)
                | (((long) bb[ 3] & 0xff) << 32)
                | (((long) bb[ 4] & 0xff) << 24)
                | (((long) bb[ 5] & 0xff) << 16)
                | (((long) bb[ 6] & 0xff) << 8) | (((long) bb[ 7] & 0xff) << 0));
    }
    /**
     * 通过byte数组取到Double
     *
     *
     * @param
     * @return
     */
    public static double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }
}
