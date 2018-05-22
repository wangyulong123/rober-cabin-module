package group.rober.dataform.handler.impl;

import group.rober.dataform.entity.Person;
import group.rober.dataform.model.DataForm;
import group.rober.dataform.model.types.ElementDataDictCodeMode;
import group.rober.dataform.model.types.ElementDataEditStyle;
import group.rober.runtime.lang.MapData;
import group.rober.runtime.lang.ValueObject;
import group.rober.sql.core.PaginationData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PersonBeanDataListHandler extends BeanDataListHandler<Person> {
    public int save(DataForm dataForm, List<Person> dataList) {
        System.out.println("<<<<<<<<-------before-save------>>>>>>>>");
        int ret = super.save(dataForm, dataList);
        System.out.println("<<<<<<<<------- after-save------>>>>>>>>");
        return ret;
    }

    public Person createNewPerson(DataForm dataForm, MapData param){
        Person person = new Person();

        person.setCode(param.getValue("code").strValue());
        person.setChnName("程序设置的中文名称");

        return person;
    }

    public PaginationData<Person> query(DataForm dataForm, Map<String, ?> queryParameters, Map<String, ?> filterParameters, Map<String, ?> sortMap, int pageSize, int pageIndex) {
        ValueObject status = getFilterValue(dataForm,filterParameters,"status");
        System.out.println("--------->"+status);
        //1.把where条件处理成没有
        dataForm.getQuery().setWhere("");
        //2.修改下拉框
        dataForm.getElement("companyIndustry")
                .getElementUIHint().setEditStyle(ElementDataEditStyle.DictCodePicker)
                .setDictCodeMode(ElementDataDictCodeMode.SQLQuery)
                .setDictCodeExpr("SELECT CODE as CODE,NAME as NAME FROM FOWK_DICT_ITEM WHERE DICT_CODE='Currency'");

        return super.query(dataForm, queryParameters, filterParameters, sortMap, pageSize, pageIndex);
    }
}
