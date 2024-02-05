package com.tree.form.api.util;

import static com.tree.form.api.constant.CommonConstant.date_type;
import static com.tree.form.api.constant.CommonConstant.string_empty;
import static com.tree.form.api.constant.InputTypeEnum.DATE;
import static com.tree.form.api.constant.InputTypeEnum.NUMBER;
import static com.tree.form.api.constant.InputTypeEnum.TABLE;

import com.tree.form.api.dto.FormAttrExtendedJsonDTO;
import com.tree.form.api.dto.FormElementDTO;
import com.tree.form.api.dto.InstanceColDO;
import com.tree.form.api.dto.InstanceQueryConditionDTO;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * @author onep@tree.com
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
    if (Objects.isNull(t.getVal())){
      return null;
    }
    if (t.getVal() instanceof Collection){
      List<Object> result = new ArrayList<>();
      for (Object obj:(Collection)t.getVal()){
        Object val = parseNumber(obj);
        if (Objects.nonNull(val)){
          result.add(val);
        }
      }
      return result;
    }
    if (Objects.equals(t.getVal(),string_empty)){
      return null;
    }
    return parseNumber(t.getVal());
  }

  public static Object parseNumber(Object val){
    try {
      return  !(val instanceof Number) ? ParamParser.getDoubleOrLong(val.toString()) :val;
    }catch (Exception e){
    }
    return null;
  }

  public static List parseNumber(List val){
    try {
      List result = new ArrayList();
      for (java.lang.Object v:val){
        Object object = !(v instanceof Number) ? ParamParser.getDoubleOrLong(v.toString()) :v;
        if (Objects.nonNull(object)){
          result.add(object);
        }
      }
      return result ;
    }catch (Exception e){
    }
    return null;
  }

  public static Object paramDate(FormElementDTO element, InstanceQueryConditionDTO t){
    FormAttrExtendedJsonDTO json = element.getAttrExtendedJson();
    if (Objects.isNull(t.getVal())||Objects.equals(t.getVal(),string_empty)){
      return null;
    }
    String dateType = Objects.isNull(json)?date_type:json.getDateType();
    if (t.getVal() instanceof Collection){
      List<Object> objectList = new ArrayList<>();
      for (Object object :(Collection)t.getVal()){
        Object val = parseDate(object,dateType);
        if (Objects.nonNull(val)){
          objectList.add(val);
        }
      }
      return objectList;
    }
    try {
      return parseDate(t.getVal(),dateType);
    }catch (Exception e){
    }
    return  null;
  }

  public static Object parseDate(Object object,String dateType){
    try {
      Long date = object instanceof Long ? (Long) object :Long.parseLong(object.toString());
      Object value = Objects.nonNull(dateType) && Objects.equals(dateType,date_type)
          ?ParamParser.truncateTime(date,"yyyyMMdd")
          : ParamParser.truncateTime(date,"yyyyMMddHHmmss");
      return value;
    }catch (Exception e){
    }
    return null;
  }

  public static Object convert(Object value ){
    if (Objects.isNull(value)){
      return null;
    }
    if (value instanceof BigDecimal){
      return  value;
    }else if(value instanceof  Float ){
      return BigDecimal.valueOf((Float) value);
    }else if ( value instanceof  Double){
      return BigDecimal.valueOf((Double) value);
    }else if (value instanceof  Integer ){
      return BigDecimal.valueOf((Integer) value);
    }else if ( value  instanceof  Long){
      return BigDecimal.valueOf((Long) value);
    }if (value instanceof String){
      if(isElementVar(value)){
        return null;
      }
      try {
        Object object = ParamParser.getDoubleOrLong(value.toString());
        return convert(object);
      }catch (Exception e){
        return value;
      }
    }
    return null;
  }

  public static List<Object>  convert(Object[] objects){
    List<Object> bigDecimals = new ArrayList<>();
    for (Object obj:objects){
      if (obj instanceof Collection){
        bigDecimals.addAll(convert(((Collection)obj).toArray()));
      }else{
        bigDecimals.add(ParamParser.convert(obj));
      }
    }
    return bigDecimals;
  }

  public static boolean isElementVar(Object value){
    if (Objects.isNull(value)){
      return false;
    }
    return  value.toString().startsWith("$") && value.toString().endsWith("#");
  }

  public static String buildElementVar(Object value){
    if (Objects.isNull(value)){
      return null;
    }
    String res = value.toString();
    if (!res.startsWith("$") ){
      res= "$"+res;
    }
    if (!res.endsWith("#")){
      res= res+"#";
    }
    return  res;
  }

  public static Map<String,Object> convertFormInstanceToMathParam(
      Map<String,Object> params
  ){
    if (CollectionUtils.isEmpty(params)){
     return Collections.emptyMap();
    }
    Map<String,Object> paramNew = new HashMap<>();
    for (Entry<String,Object> entry:params.entrySet()){
      if (!(entry.getValue() instanceof Map)){
        paramNew.put(ParamParser.buildElementVar(entry.getKey()),entry.getValue());
        continue;
      }
      InstanceColDO colDO = ObjectConvert.convert(entry.getValue(), InstanceColDO.class);
      if (Objects.isNull(colDO) || Objects.isNull(colDO.getAttr_value())){
        continue;
      }
      Set<String> tableKeys = new HashSet<>();
      if (Objects.equals(colDO.getInput_type(),TABLE.code)){
        for (int i = 0 ;i<colDO.getAttr_value().size();i++){
//          if (Objects.equals(table,entry.getKey()) && !Objects.equals(expressRow,i)){
//            continue;
//          }
          Object tableRow = colDO.getAttr_value().get(i);
          if (!(tableRow instanceof Map)){
            continue;
          }
          Map<String,Object> row = (Map) tableRow;
          for (Entry<String,Object> cell:row.entrySet()){
            String cellVar = ParamParser.buildElementVar(cell.getKey());
            Object object = paramNew.get(cellVar);
            if (!tableKeys.contains(cellVar)){
              tableKeys.add(cellVar);
            }
            List<Object> colValue;
            if (Objects.isNull(object)){
              colValue = new ArrayList<>();
              paramNew.put(cellVar,colValue);
            }else {
              colValue = (List<Object>) object;
            }
            InstanceColDO cellDO = ObjectConvert.convert(cell.getValue(), InstanceColDO.class);
            if (Objects.nonNull(cellDO) && CollectionUtils.isNotEmpty(cellDO.getAttr_value())){
              colValue.addAll(cellDO.getAttr_value());
            }else{
              colValue.add(null);
            }
          }
        }
        paramNew.put(entry.getKey(),tableKeys);
      }else{
        List originalValues = colDO.getAttr_value();
        if (Objects.equals(colDO.getInput_type(),NUMBER.code)){
          List newValues = ParamParser.convert(originalValues.toArray());
          paramNew.put(ParamParser.buildElementVar(entry.getKey()),CollectionUtils.isNotEmpty(newValues)?newValues.get(0):null);
        } else if ( Objects.equals(colDO.getInput_type(),DATE.code)){
          List newValues = ParamParser.parseNumber(originalValues);
          paramNew.put(ParamParser.buildElementVar(entry.getKey()),CollectionUtils.isNotEmpty(newValues)?newValues.get(0):null);
        }else{
          paramNew.put(ParamParser.buildElementVar(entry.getKey()),CollectionUtils.isNotEmpty(originalValues)?originalValues.get(0):null);
        }
      }
    }
    return paramNew;
  }


}
