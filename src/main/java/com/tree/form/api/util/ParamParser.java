package com.tree.form.api.util;

import static com.tree.form.api.constant.CommonConstant.date_type;
import static com.tree.form.api.constant.CommonConstant.string_empty;

import com.tree.form.api.dto.FormAttrExtendedJsonDTO;
import com.tree.form.api.dto.FormElementDTO;
import com.tree.form.api.dto.InstanceQueryConditionDTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author zhuzhong@yunsom.com
 * @date 2020-05-29 11:38
 * @description
 */
public class ParamParser {

  public  static Object getDoubleOrLong(String value){
    if (Objects.isNull(value) || value.length()==0){
      return null;
    }
    int pointIndex = value.indexOf(".");
    Object object;
    if (pointIndex!=-1){
      object = Double.parseDouble(value);
    }else{
      object = Long.parseLong(value);
    }
    return object;
  }

  public static Long truncateTime(Long time,String pattern){
    if (Objects.isNull(time)){
      return null;
    }
    DateFormat df = new SimpleDateFormat(pattern);
    try {
      return  df.parse(df.format(new Date(time))).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static Object paramNumber(InstanceQueryConditionDTO t){
    if (Objects.isNull(t.getVal())||Objects.equals(t.getVal(),string_empty)){
      return null;
    }
    try {
      return  !(t.getVal() instanceof Number) ? ParamParser.getDoubleOrLong(t.getVal().toString()) :t.getVal();
    }catch (Exception e){
    }
    return null;
  }

  public static Object paramDate(FormElementDTO element, InstanceQueryConditionDTO t){
    FormAttrExtendedJsonDTO json = element.getAttrExtendedJson();
    if (Objects.isNull(t.getVal())||Objects.equals(t.getVal(),string_empty)){
      return null;
    }
    try {
      Long date = t.getVal()instanceof Long ? (Long) t.getVal()
          :Long.parseLong(t.getVal().toString());
      Object value = Objects.nonNull(json) && Objects.equals(json.getDateType(),date_type)
          ?ParamParser.truncateTime(date,"yyyyMMdd")
          : ParamParser.truncateTime(date,"yyyyMMddHHmmss");
      return value;
    }catch (Exception e){
    }
    return  null;
  }
}
