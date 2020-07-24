package com.tree.form.api.util;

import com.alibaba.fastjson.JSON;
import java.lang.reflect.Type;
import java.util.Objects;

public class ObjectConvert {

    /**
     * 各种pojo之间的相互xo
     * @param source 源
     * @param clazz 目标类型
     * @param <T> 目标类型
     * @return 目标实例对象
     */
    public static <T> T convert(Object source, Class<T> clazz) {
        return Objects.isNull(source)?null:JSON.parseObject(JSON.toJSONString(source), clazz);
    }

    /**
     * 支持泛型转换
     * @param source 源
     * @param type 目标类型 new TypeReference<T>().getType;
     * For example:
     * <pre>
     * Type type = new TypeReference<Result<Foo>>(){}.getType();
     * </pre>
     * @return 目标实例对象
     */
    public static <T> T convert(Object source, Type type) {
        return JSON.parseObject(JSON.toJSONString(source), type);
    }

    public static <T> T convert(String data, Class<T> type) {
        return JSON.parseObject(data, type);
    }

    public static String toJson(Object data) {
        return JSON.toJSONString(data);
    }
}
