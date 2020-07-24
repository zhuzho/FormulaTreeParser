package com.yunsom.form.api.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-06-09 15:20
 * @description
 */
public class CollectionUtils {

  public static boolean isEmpty(Collection<?> collection) {
    return Objects.isNull(collection) || collection.isEmpty();
  }

  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  public static boolean isNotEmpty(Map map) {
    return !isEmpty(map);
  }
  public static boolean isEmpty(Map map) {
    return map==null||map.isEmpty();
  }
}
