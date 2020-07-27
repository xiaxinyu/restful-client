package com.xiaxinyu.restful.client.common;

import com.alibaba.fastjson.JSONObject;

/**
 * Json操作工具类
 *
 * @author XIAXINYU3
 * @date 2019.6.8
 */
public class JSONUtils {
    /**
     * 构造正确处理返回格式
     *
     * @param data 返回数据
     * @return
     */
    public static JSONObject getSuccess(Object data) {
        JSONObject ret = new JSONObject();
        ret.put("code", Constants.SUCCESS);
        ret.put("data", data);
        return ret;
    }

    /**
     * 构造错误处理返回格式
     *
     * @param msg 错误信息
     * @return
     */
    public static JSONObject getFailure(String msg) {
        JSONObject ret = new JSONObject();
        ret.put("code", Constants.FAILURE);
        ret.put("msg", msg);
        return ret;
    }

    /**
     * 构造无权限处理返回格式
     *
     * @param msg 错误信息
     * @return
     */
    public static JSONObject getUnAuthorized(String msg) {
        JSONObject ret = new JSONObject();
        ret.put("code", Constants.UNAUTHORIZED);
        ret.put("msg", msg);
        return ret;
    }

    public static boolean isSuccess(JSONObject obj) {
        Integer code = checkNullAndType(obj, "code", Integer.class);
        return Constants.SUCCESS == code.intValue();
    }

    public static JSONObject getData(JSONObject obj) {
        return obj.getJSONObject("data");
    }

    public static <T> T checkNullAndType(JSONObject obj, String attrName, Class<? extends T> cls) {
        if (obj != null) {
            if (obj.containsKey(attrName)) {
                Object value = obj.get(attrName);
                Class<?> realCls = value.getClass();
                if (realCls.isAssignableFrom(cls)) {
                    return (T) value;
                }
                throw new RuntimeException("属性(" + attrName + ")类型错误，期望的类型:" + cls.getName() + ",实际类型:" + realCls.getName());
            } else {
                throw new RuntimeException("属性(" + attrName + ")不存在!");
            }

        } else {
            throw new RuntimeException("第一个参数obj不能为空!");
        }
    }
}
