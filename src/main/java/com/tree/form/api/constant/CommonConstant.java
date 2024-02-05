package com.tree.form.api.constant;

/**
 * @author onep@tree.com
 * @date 2019-12-09 9:50
 * @description
 */
public interface CommonConstant {

  int decimal_places_result = 4;

  int decimal_places_processing = 6;

  String enterprise_="enterprise_";

  String string_empty="";

  String instance_add="add";

  String instance_update="update";

  String instance_delete="delete";


  String date_type = "date";
  String time_type = "time";

  //custom,logic,math,link 单词写错了。。尴尬


  String default_custom = "custom";

  String default_logic = "logic";

  String default_math = "math";

  String default_link= "link";

  String default_type_current  = "current";

  String defualt_custom = default_custom;

  String defualt_logic = default_logic;

  String defualt_math = default_math;

  String defualt_link= default_link;



  String data_structure_tree="TREE";

  String data_structure_list="LIST";

  String data_structure_detail="DETAIL";

  String primary_level="level";

  String primary_global="global";

  String multiple="multiple";

  String single="single";

  String domain_platform="PLATFORM";

  String domain_template="TEMPLATE";

  String domain_tenant="TENANT";

  String domain_extra="EXTRA";

  byte YES = 1;
  byte NO = 2;

  String S_YES="yes";

  String S_NO="no";

  Integer INT_YES = (int)YES;

  Integer INT_NO = (int)NO;

  String TOP="-1";

  String TOP_ELEMENT=TOP;

  Integer perPage=10;

  Integer page = 1;

  String OUT_LINE = "out_line";

  String[] OPERATIONS_OUT_LINE = {"add","import","export"};

  String  IN_LINE = "in_line";

  String[] OPERATIONS_IN_LINE = {"update","delete","view"};

  String idx_unique = "idx_unique";

  Integer user_admin = -1;
  String user_admin_name = "系统管理员";


}
