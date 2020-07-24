/**
 *
 */
package com.yunsom.form.api.constant;

import com.yunsom.form.api.dto.InputTypeViewDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum InputTypeEnum {
  TEXT_SINGLE((byte) 1, "单行文本输入框","text_single",true,false),
  TEXT_MULTIPLE((byte) 2, "多行文本输入框","text_multiple",true,false),
  SELECT((byte) 3, "下拉","select",true,false),
  SELECT_SINGLE((byte) 3, "下拉单选","select_single",true,false),
  SELECT_MULTIPLE((byte) 4, "多选","select_multiple",true,false),
  IMAGE((byte) 5, "相册+相机","image",true,false),
  DATE((byte) 6, "日期选择框","date",true,false),
  TIME((byte) 7, "时间选择框","time",true,false),
  NUMBER((byte) 8, "数字输入框","number",true,true),
  CAMERA((byte) 9, "相机","camera",false,false),
  /**正负数*/
  POSITIVE_NEGATIVE((byte) 10, "正负数字输入框","positive_negative",true,true),
  /**音频*/
  AUDIO_FREQUENCY((byte) 11, "音频","audio_frequency",false,false),
  TABLE((byte) 12, "表格","table",false,false),
  SELECT_SIMPLE((byte) 13, "快捷单选","select_simple",true,false),
  INTEGER_NUMBER((byte) 14, "整型输入框","integer_number",true,true),
  SELECT_SIMPLE_MULTIPLE((byte) 15, "快捷多选","select_simple",true,false),
  FILE((byte) 17, "文件上传","file",true,false),
  SELECT_DATASOURCE((byte) 18, "下拉数据列表","select_datasource",false,false),
  GUIDE_PICTURE((byte)19,"引导图片","guide_picture",true,false),
  ORGANIZATIONAL_STRUCTURE((byte)20,"企业组织架构","organizational_structure",true,false),
  SELECT_POSITIVE_NEGATIVE_MULTIPLE((byte)21,"正反分组多选","select_positive_negative_multiple",false,false),
  SELECT_POSITIVE((byte)22,"正","select_positive",false,false),
  SELECT_NEGATIVE((byte)23,"反","select_negative",false,false),
  SELECT_POSITIVE_NEGATIVE_SINGLE((byte)24,"正反分组单选","select_positive_negative_single",false,false),
  LOCATION((byte)25,"地点{attr_value:'坐标',attr_value_text:'地址'}","location",false,false),
  /**关联备件*/
  EQUIPMENT_PARTS((byte) 40, "关联备件","equipment_parts",false,false),
  SELECT_DATALIST((byte)41,"数据列表","select_datalist",false,false),
  SELECT_GROUP((byte)42,"分组下拉多选","select_group",true,false),

  SELECT_GROUP_TITLE((byte)43,"下拉分组标题","select_group_title",false,false),
  OPTION((byte)44,"选项","option",false,false),
  // 布局元素
  GROUP((byte)51,"分组","group",false,false),
  PAGE((byte)52,"分页","page",false,false),
  MODULE((byte)53,"模块","module",false,false),

  MODULE_HIDE((byte)54,"隐藏模块","module_hide",false,false),

  ENTITY_DATA_REFER((byte)55,"数据引用","entity_data_refer",false,false),

  ENTITY_DATA_LINK((byte)56,"数据联动","entity_data_link",false,false),

  USER((byte)58,"用户","user",false,false),
  SERIAL_NUMBER((byte)59,"流水号","serial_number",false,false),
  ENTITY_VIEW_REFER((byte)60,"关联属性","entity_view_refer",false,false),

  ;

  /**编号*/
  public final byte code;
  /**描述*/
  public final String desc;
  /**简称*/
  public final String shortName;
  /**是否支持配置默认值*/
  public final boolean allowDefault;
  /**是否支持计算逻辑*/
  public final boolean allowExpre;
  InputTypeEnum(byte code, String desc,String shortName,boolean allowDefault,boolean allowExpre) {
    this.code = code;
    this.desc = desc;
    this.shortName = shortName;
    this.allowDefault = allowDefault;
    this.allowExpre = allowExpre;
  }

  public static InputTypeEnum get(byte code) {
    for (InputTypeEnum inputTypeEnum : InputTypeEnum.values()) {
      if (inputTypeEnum.code == code) {
        return inputTypeEnum;
      }
    }
    return null;
  }

  public static InputTypeEnum getByDesc(String desc) {
    for (InputTypeEnum inputTypeEnum : InputTypeEnum.values()) {
      if (inputTypeEnum.desc.equals(desc)) {
        return inputTypeEnum;
      }
    }
    return null;
  }
  public static final List<Byte> canEditInput= Arrays.asList(
        TEXT_SINGLE .code,
        TEXT_MULTIPLE .code,
        SELECT_SINGLE.code ,
        SELECT_MULTIPLE.code,
        IMAGE.code ,
        DATE .code,
        TIME .code,
        NUMBER .code,
        CAMERA .code,
        POSITIVE_NEGATIVE.code ,
        AUDIO_FREQUENCY.code ,
        SELECT_SIMPLE .code,
        INTEGER_NUMBER.code ,
        FILE.code,
        EQUIPMENT_PARTS .code,
        SELECT_DATALIST.code ,
        SELECT_GROUP .code,
        ORGANIZATIONAL_STRUCTURE.code
  );
  public static List<Byte> singleTextInput(){
    return  Arrays.asList(
            TEXT_SINGLE.code,
            TEXT_MULTIPLE.code,
            INTEGER_NUMBER.code,
            NUMBER.code
    );
  }

  public static List<Byte> notDisplayInput(){
    return  Arrays.asList(
        TABLE.code
    );
  }

  public static List<Byte> instanceTitleNotInclude(){
    return  Arrays.asList(
        CAMERA.code,
        AUDIO_FREQUENCY.code,
        FILE.code,
        TEXT_MULTIPLE.code,
        TABLE.code
    );
  }

  public static List<Byte> instanceSummaryNotInclude(){
    return  Arrays.asList(
        AUDIO_FREQUENCY.code,
        FILE.code,
        TEXT_MULTIPLE.code,
        TABLE.code
    );
  }

  public  static List<InputTypeViewDTO> version(String formVersion){
    List<InputTypeViewDTO> list = new ArrayList<>();
    if ("v3".equals(formVersion)){
      return v3().stream().map(t->{
        InputTypeViewDTO typeView = new InputTypeViewDTO();
        typeView.setCode(t.code);
        typeView.setDesc(t.desc);
        return typeView;
      }).collect(Collectors.toList());
    }
    return list;
  }

  static List<InputTypeEnum> v3(){
    return Arrays.asList(
        TEXT_SINGLE, TEXT_MULTIPLE, SELECT, NUMBER,
        IMAGE, DATE, FILE,AUDIO_FREQUENCY,ORGANIZATIONAL_STRUCTURE,
        USER,TABLE,OPTION,LOCATION,ENTITY_DATA_REFER,SELECT_GROUP_TITLE,
        SERIAL_NUMBER
    );
  }
}