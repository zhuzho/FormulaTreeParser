package test.method;

import com.tree.form.api.constant.ResultCodeEnum;

/**
 * @date 2020-01-19 9:58
 * @description
 */
public class TestStringFomart {

  public static void main(String[] args) {
    Integer object=100;
    System.out.println(String.format(ResultCodeEnum.DEFINE_FORM_LIST_ELE_LIMIT.message,object));
  }
}
