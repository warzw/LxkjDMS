package lxkj.train.com.mvp.model.model_interface;

import java.util.Map;

/**
 * Created by dhc on 2017/6/12.
 *  接口中两个方法，实现以后，用哪个就调用那个，不用的那个方法可以放那不用管
 */

public interface DataModel {
    <T> void getData(Object data);
}
