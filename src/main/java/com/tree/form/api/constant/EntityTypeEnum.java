package com.yunsom.form.api.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhuzhong@yunsom.com
 * @date 2019-12-09 15:34
 * @description
 */
public enum  EntityTypeEnum {
  base_info("基础信息"),
  device("设备信息"),
  product("产品信息"),
  material("物料信息"),
  inspection_standard("检验标准"),
  workflow("流程表单(通用表单)"),
  object("对象表单"),
  questionnaire("问卷(通用表单)"),
  level_extra("层级扩展表单(层级表单附带的)"),
  business("业务字段(业务模板)"),
  enterprise_workflow("企业自定义流程父级表单(通用表单)"),
  enterprise_object("企业自定义对象父级表单"),
  ;
  public String desc;
  EntityTypeEnum(String desc) {
    this.desc = desc;
  }

  public static String getString(){
    StringBuffer stringBuffer = new StringBuffer();
    for (EntityTypeEnum entityType:EntityTypeEnum.values()) {
      if (stringBuffer.length()==0){
        stringBuffer.append(",");
      }
      stringBuffer.append(entityType.name()+":"+entityType.desc);
    }
    return stringBuffer.toString();
  }

  public static List<String> productPackage(){
    return Arrays.asList(object.name());
  }
}
