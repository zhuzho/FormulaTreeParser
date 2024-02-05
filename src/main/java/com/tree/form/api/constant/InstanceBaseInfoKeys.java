package com.tree.form.api.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author onep@tree.com
 * @date 2019-12-05 18:00
 * @description
 */
public interface InstanceBaseInfoKeys {

  String idx_tenant_unique="itq_";

  String entity_active="active";

  String entity_disable="disable";

  String tenant="tenant";

  String tenantType="tenantType";

  String platform = "platform";

  String enterpriseCustomEntity = "enterpriseCustomEntity";

  String entity_type_object = "object";
  String entity_type_workflow= "workflow";


  String enterprise = "enterprise";

  String createdAt="createdAt";

  String deletedAt="deletedAt";

  String deleted_at="deleted_at";

  String creator = "creator";

  String updatedAt="updatedAt";

  String is_required="is_required";

  String retrieve="retrieve";

  String need_extends="need_extends";

  String view = "view";

  String list_view="list_view";

  List<String> element_control=Arrays.asList(is_required,retrieve,need_extends,view,list_view);

  String refer_entity_type="refer_entity_type";


  String element_parent_input_type = "element_parent_input_type";

  String formula ="formula";

  String placeholder = "placeholder";

  String serial_prefix = "serial_prefix";



  String parentId="parentId";

  String rootId = "rootId";

  String colletionNamePrex = "form_ins_";

  String extendInfoKey = "extendedInfo";

  String instanceBaseInfo = "baseInfo";

  String point=".";

  String _id="_id";

  String id="id";



  Integer enterprise_common=0;

  Integer enterprise_fix=-1;

  Integer enterprise_all=-2;

  String extendForm = "extendForm";

  String tenant_common="0";
  String tenant_fix=String.valueOf(enterprise_fix);
  String tenant_all=String.valueOf(enterprise_all);

  String operator = "operator";

  String name ="_name";

  String data_attr_value = "attr_value";

  String element_row_id="element_row_id";

  String data_attr_text = "attr_value_text";

  String attr_value=point+data_attr_value;

  String attr_value_text=point+data_attr_text;

  String extendInfoKeyPoint=extendInfoKey+point ;

  String includeTenant="includeTenant";

  String baseInfoPoint = instanceBaseInfo+point;

  String baseInfo_update=baseInfoPoint+updatedAt;

  String baseInfo_operator=baseInfoPoint+operator;

  String baseInfo_operator_name=baseInfo_operator+name;

  String baseInfo_tenant = baseInfoPoint + tenant;

  String baseInfo_tenantType = baseInfoPoint + tenantType;

  String baseInfo_enterpriseCustomEntity = baseInfoPoint + enterpriseCustomEntity;

  String baseInfo_deletedAt = baseInfoPoint + deletedAt;

  String baseInfo_parentId = baseInfoPoint + parentId;

  String baseInfo_extendForm = baseInfoPoint + extendForm;

  String baseInfo_rootId = baseInfoPoint + rootId ;

  String baseInfo_createdAt = baseInfoPoint + createdAt;

  String baseInfo_includeTenant = baseInfoPoint + includeTenant;

  List<String> defualtKeys= Arrays.asList(
      _id
  );
  List<String> defualtBaseKeys= Arrays.asList(
      createdAt,
      parentId,
      creator
  );

  List<String> notElementCol= Arrays.asList(
      instanceBaseInfo,
      _id,
      extendInfoKey
  );

  String elementId="elementId";

  String[] serial_instance_elements =  new String[]{elementId};

}
