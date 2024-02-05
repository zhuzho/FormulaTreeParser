package com.tree.form.api.util;

import static com.tree.form.api.constant.InputTypeEnum.ENTITY_DATA_REFER;
import static java.util.stream.Collectors.toList;

import com.tree.form.api.dto.DataBaseDTO;
import com.tree.form.api.dto.FormElementDTO;
import com.tree.form.api.dto.InstanceCol;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author onep@tree.com
 * @date 2020-06-09 10:29
 * @description
 */
public final class FormValueUtil {

  public static List<Object> getAttrValueText(FormElementDTO fe,Object value){
    if (Objects.isNull(value)){
      return null;
    }
    if (Objects.isNull(fe)){
      return value instanceof Collection ? (List<Object>) value :Arrays.asList(value);
    }
    InstanceCol instanceCol = ObjectConvert.convert(value, InstanceCol.class);
    if (Objects.isNull(instanceCol)){
      return null;
    }
    if (Objects.isNull(instanceCol.getAttrValue()) || instanceCol.getAttrValue().size()==0){
      return null;
    }
    if (Objects.nonNull(instanceCol.getAttrValueText()) && instanceCol.getAttrValueText().size()!=0){
      return instanceCol.getAttrValueText();
    }

    return instanceCol.getAttrValue();
  }

  public static List<DataBaseDTO> getDataRefer(FormElementDTO fe,Object value){
    if (Objects.isNull(value)){
      return Collections.emptyList();
    }
    if (!Objects.equals(fe.getInputType(),ENTITY_DATA_REFER.code)){
      return Collections.emptyList();
    }
    if (value instanceof Collection){
      return (List<DataBaseDTO>) ((Collection) value).stream().map(t->{
        DataBaseDTO dto = ObjectConvert.convert(t,DataBaseDTO.class);
        return dto;
      }).collect(toList());
    }else{
      return Arrays.asList(ObjectConvert.convert(value,DataBaseDTO.class));
    }
  }
}
