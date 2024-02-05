package com.tree.form.api.util;

import static com.tree.form.api.constant.CommonConstant.INT_YES;

import com.tree.common.base.exception.BusinessException;
import com.tree.form.api.constant.FuncList;
import com.tree.form.api.constant.InputTypeEnum;
import com.tree.form.api.constant.ResultCodeEnum;
import com.tree.form.api.dto.InstanceQueryConditionDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author onep@tree.com
 * @date 2020-05-28 14:03
 * @description
 */
public class FormQueryParamBuilder {

  public static List<List<InstanceQueryConditionDTO>> buildRuleParam(List<List<InstanceQueryConditionDTO>> targetConditions ) {
    if (CollectionUtils.isEmpty(targetConditions)){
      return Collections.emptyList();
    }
    List<List<InstanceQueryConditionDTO>> result = new ArrayList<>();
    for (List<InstanceQueryConditionDTO> targetCondition : targetConditions) {
      if (CollectionUtils.isEmpty(targetCondition)){
        continue;
      }
      List<InstanceQueryConditionDTO> orParam = new ArrayList<>();

      for (InstanceQueryConditionDTO matchCondition : targetCondition) {
        if (!StringUtils.isEmpty(matchCondition.getFunc())){
          orParam.add(matchCondition);
          continue;
        }
        if (Objects.isNull(matchCondition.getVal())
            || (
                  matchCondition.getVal() instanceof Collection
            && com.tree.form.api.util.CollectionUtils.isEmpty( (Collection)matchCondition.getVal())
            )
            || matchCondition.getVal().toString().length()==0
        ){
          continue;
        }
        if (Objects.equals(matchCondition.getInputType(), InputTypeEnum.TEXT_SINGLE.code)
          || Objects.equals(matchCondition.getInputType(), InputTypeEnum.SERIAL_NUMBER.code)
          || Objects.equals(matchCondition.getInputType(), InputTypeEnum.LOCATION.code)
        ){
          matchCondition.setFunc(matchCondition.getMatchAccurate()==INT_YES? FuncList.equal:FuncList.like);
          if (matchCondition.getVal() instanceof List){
            List data = (List)matchCondition.getVal();
            if (CollectionUtils.isEmpty(data)){
              continue;
            }
            matchCondition.setVal(data.get(0));
          }else{
            matchCondition.setVal(matchCondition.getVal());
          }
          orParam.add(matchCondition);
        }else if (
            Objects.equals(matchCondition.getInputType(), InputTypeEnum.SELECT.code)
                ||Objects.equals(matchCondition.getInputType(), InputTypeEnum.USER.code)
                ||Objects.equals(matchCondition.getInputType(), InputTypeEnum.ORGANIZATIONAL_STRUCTURE.code)
        ){
          matchCondition.setFunc(FuncList.in);
          matchCondition.setVal( matchCondition.getVal() instanceof Collection ?matchCondition.getVal(): Arrays
              .asList(matchCondition.getVal()));
          orParam.add(matchCondition);
        }else if (
            (Objects.equals(matchCondition.getInputType(), InputTypeEnum.NUMBER.code)
                ||Objects.equals(matchCondition.getInputType(), InputTypeEnum.DATE.code)
            )
                && matchCondition.getVal() instanceof List
        ){
          List collection = (List) matchCondition.getVal();
          if (collection.size()==2){
            Number paramMin = null;
            Number paramMax = null;
            if (Objects.nonNull(collection.get(0))){
              InstanceQueryConditionDTO areaCond  = ObjectConvert.convert(matchCondition,InstanceQueryConditionDTO.class);
              areaCond.setFunc(FuncList.ge);
              paramMin = collection.get(0) instanceof Number ?(Number) collection.get(0):Double.parseDouble(collection.get(0).toString());
              areaCond.setVal(paramMin);
              orParam.add(areaCond);
            }
            if (Objects.nonNull(collection.get(1)) && !StringUtils.isEmpty(collection.get(1).toString())){
              InstanceQueryConditionDTO areaCond  = ObjectConvert.convert(matchCondition,InstanceQueryConditionDTO.class);
              areaCond.setFunc(Objects.equals(matchCondition.getInputType(), InputTypeEnum.DATE.code)?FuncList.lt:FuncList.le);
              paramMax = collection.get(1) instanceof Number ?(Number) collection.get(1):Double.parseDouble(collection.get(1).toString());
              areaCond.setVal(paramMax);
              orParam.add(areaCond);
            }
            if (Objects.nonNull(paramMin) && Objects.nonNull(paramMax)){
              if (paramMin.doubleValue()>paramMax.doubleValue()){
                //
                throw new BusinessException(
                    matchCondition.getElementId(),
                    ResultCodeEnum.COMMON_INVALID_ARG.code);
              }
            }
          }else if (collection.size()==1){
            InstanceQueryConditionDTO areaCond  = ObjectConvert.convert(matchCondition,InstanceQueryConditionDTO.class);
            Object value = collection.get(0);
            areaCond.setFunc(FuncList.ge);
            areaCond.setVal( value instanceof Number ?value:Double.parseDouble(value.toString()));
            orParam.add(areaCond);
          }
        }
      }
      if (!CollectionUtils.isEmpty(orParam)){
        result.add(orParam);
      }
    }
    return result;
  }
}
