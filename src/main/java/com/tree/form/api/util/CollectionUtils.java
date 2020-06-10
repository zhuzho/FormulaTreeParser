package com.yunsom.form.api.util;

import java.util.Collection;
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
}
