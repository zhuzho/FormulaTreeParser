package test.method;

import com.yunsom.form.api.constant.ResultCodeEnum;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-01-19 9:58
 * @description
 */
public class TestStringFomart {

  public static void main(String[] args) {
    Integer object=100;
    System.out.println(String.format(ResultCodeEnum.DEFINE_FORM_LIST_ELE_LIMIT.message,object));
  }
}
