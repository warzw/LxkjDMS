package lxkj.train.com.utils;

/**
 * Created by dell on 2018/7/2.
 */


public class MillerCoordinate {
    public static int[] MillierConvertion(double lon, double lat)
    {
        double L = 6381372 * Math.PI * 2;//地球周长
        double W=L;// 平面展开后，x轴等于周长
        double H=L/2;// y轴约等于周长一半
        double mill=2.3;// 米勒投影中的一个常数，范围大约在正负2.3之间
        double x = lon * Math.PI / 180;// 将经度从度数转换为弧度
        double y = lat * Math.PI / 180;// 将纬度从度数转换为弧度
        y=1.25 * Math.log( Math.tan( 0.25 * Math.PI + 0.4 * y ) );// 米勒投影的转换
        // 弧度转为实际距离
        x = ( W / 2 ) + ( W / (2 * Math.PI) ) * x;
        y = ( H / 2 ) - ( H / ( 2 * mill ) ) * y;
        int[] result=new int[2];
        result[0]= ((int) x)/1000;
        result[1]= ((int) y)/1000;
        return result;

    }
}

