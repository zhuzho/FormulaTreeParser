package com.tree.form.api.constant;

import static com.tree.form.api.constant.CommonConstant.IN_LINE;
import static com.tree.form.api.constant.CommonConstant.OUT_LINE;

/**
 * @author onep@tree.com
 * @date 2020-05-20 16:16
 * @description
 */
public enum EntityOperationEnum {
  add(OUT_LINE,"add","新增",""),
  imp(OUT_LINE,"import","导入",""),
  exp(OUT_LINE,"export","导出",""),
  print_batch(OUT_LINE,"print_batch","批量打印",""),
  print_setting(OUT_LINE,"print_setting","打印设置",""),

  update(IN_LINE,"update","编辑",""),
  delete(IN_LINE,"delete","删除",""),
  view(IN_LINE,"view","详情",""),
  print(IN_LINE,"print","打印",""),

  ;

  public String code;
  public String group;
  public String name;
  public String url;

  EntityOperationEnum(String group, String code, String name,String url) {
    this.group = group;
    this.code = code;
    this.name = name;
    this.url = url;
  }}
