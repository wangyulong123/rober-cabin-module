package group.rober.dataform.service;

import group.rober.dataform.dto.CloneDataFormBean;
import group.rober.dataform.exception.DataFormException;
import group.rober.dataform.mapper.DataFormDBRowMapper;
import group.rober.dataform.mapper.DataFormElementDBRowMapper;
import group.rober.dataform.mapper.DataFormFilterDBRowMapper;
import group.rober.dataform.mapper.DataFormMapper;
import group.rober.dataform.mapper.DataFormValidatorRowMapper;
import group.rober.dataform.mapper.impl.DataFormMapperDBTableImpl;
import group.rober.dataform.model.DataForm;
import group.rober.dataform.model.DataFormElement;
import group.rober.dataform.model.DataFormFilter;
import group.rober.dataform.model.DataFormStamp;
import group.rober.dataform.model.types.ElementDataEditStyle;
import group.rober.dataform.model.types.ElementDataFormat;
import group.rober.dataform.model.types.ElementDataType;
import group.rober.runtime.kit.*;
import group.rober.runtime.lang.MapData;
import group.rober.runtime.lang.ValueObject;
import group.rober.sql.core.DataQuery;
import group.rober.sql.core.PaginationData;
import group.rober.sql.core.DataAccessor;
import group.rober.sql.core.PaginationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by luyu on 2017/12/25.
 */
@Service
public class DataFormAdminService {

//    @Autowired
//    private DataAccessor dataAccessor;
    @Autowired
    private DataQuery dataQuery;
    @Autowired
    private JdbcTemplate jdbcTemplate = null;
    @Autowired
    private DataFormDBRowMapper dataFormDBRowMapper;
    @Autowired
    private DataFormElementDBRowMapper dataFormElementDBRowMapper;
    @Autowired
    private DataFormFilterDBRowMapper dataFormFilterDBRowMapper;
    @Autowired
    private DataFormMapper dataFormMapper;

    @Autowired
    private DataFormValidatorRowMapper dataFormValidatorRowMapper;

    public List<DataForm> getDataForms() {
//        dataAccessor.getDataQuery().setRowMapper(dataFormDBRowMapper);
//        List<DataForm> dataForms = dataAccessor
//                .selectList(DataForm.class, "select * from FOWK_DATAFORM ORDER BY SORT_CODE ASC,PACK ASC,CODE ASC");
        List<DataForm> dataForms = dataQuery.exec(dataFormDBRowMapper,()->{
            return dataQuery
                    .selectList(DataForm.class, "select * from FOWK_DATAFORM ORDER BY SORT_CODE ASC,PACK ASC,CODE ASC");
        });

        return dataForms;
    }

    public List<DataForm> getCompleteDataForms() {
//        dataAccessor.getDataQuery().setRowMapper(dataFormDBRowMapper);
//        List<DataForm> dataForms = dataAccessor
//                .selectList(DataForm.class, "select * from FOWK_DATAFORM ORDER BY ID");

        List<DataForm> dataForms = dataQuery.exec(dataFormDBRowMapper,()->{
            return dataQuery.selectList(DataForm.class, "select * from FOWK_DATAFORM ORDER BY ID");
        });
        if (null == dataForms || dataForms.isEmpty()) return dataForms;

//        dataAccessor.getDataQuery().setRowMapper(dataFormElementDBRowMapper);
//        List<DataFormElement> dataFormElements = dataAccessor.selectList(DataFormElement.class,
//                "SELECT * FROM FOWK_DATAFORM_ELEMENT ORDER BY DATAFORM_ID, SORT_CODE");
        List<DataFormElement> dataFormElements = dataQuery.exec(dataFormElementDBRowMapper,()->{
            return dataQuery.selectList(DataFormElement.class,
                    "SELECT * FROM FOWK_DATAFORM_ELEMENT ORDER BY DATAFORM_ID, SORT_CODE");
        });
        fillDataForm(dataForms, dataFormElements, (df, dfElement) -> df.addElement((DataFormElement) dfElement));


//        dataAccessor.getDataQuery().setRowMapper(dataFormFilterDBRowMapper);
//        List<DataFormFilter> dataFormFilters = dataAccessor.selectList(DataFormFilter.class,
//                "SELECT * FROM FOWK_DATAFORM_FILTER ORDER BY DATAFORM_ID, SORT_CODE");
        List<DataFormFilter> dataFormFilters = dataQuery.exec(dataFormFilterDBRowMapper,()->{
            return dataQuery.selectList(DataFormFilter.class,
                    "SELECT * FROM FOWK_DATAFORM_FILTER ORDER BY DATAFORM_ID, SORT_CODE");
        });

        fillDataForm(dataForms, dataFormFilters, (df, dfFilter) -> df.addFilter((DataFormFilter) dfFilter));
        return dataForms;
    }

    private void fillDataForm(List<DataForm> dataForms, List<? extends DataFormStamp> properties,
                              BiConsumer<DataForm, ? super DataFormStamp> action) {
        DataForm currentDf;

        int lastJ = -1;
        for (int i = 0, j = lastJ + 1; i < dataForms.size(); ++i) {
            currentDf = dataForms.get(i);
            boolean dataformRefresh = true;
            for (; j < properties.size(); ++j) {
                String id = properties.get(j).getDataformId();
                if (null == id || id.isEmpty()) ;
                else if (id.equals(currentDf.getId())) {
                    lastJ = j;
                    action.accept(currentDf, properties.get(j));
                    dataformRefresh = false;
                } else {
                    if (!dataformRefresh)
                        break;
                }
            }
            if (j == properties.size() && j - lastJ > 1)
                j = lastJ + 1;
        }
    }

    public DataForm getDataForm(String id) {
        DataForm dataForm = dataFormMapper.getDataForm(id);
        return dataForm;
    }

    @Transactional
    public DataForm saveDataForm(DataForm dataForm, String oldDataFormId) {
        String newDataFormId = StringKit.format("{0}-{1}", dataForm.getPack(), dataForm.getCode());
        dataFormMapper.save(dataForm);
        if (!StringUtils.isEmpty(oldDataFormId) && !newDataFormId.equals(oldDataFormId)) {
            dataFormMapper.delete(oldDataFormId);
        }
        return dataForm;
    }

    @Transactional
    public DataForm cloneDataForm(CloneDataFormBean cloneDataFormBean) {
        String[] dataFormInfo = cloneDataFormBean.getNewDataFormId().split("-");
        if (dataFormInfo.length != 2) {
            throw new DataFormException("dataForm format error");
        }
        String dataFormSQL = DataFormMapperDBTableImpl.DATAFORM_SQL + "where ID=:id";
//        dataAccessor.getDataQuery().setRowMapper(dataFormDBRowMapper);
//        DataForm newDataForm = dataAccessor
//                .selectOne(DataForm.class, dataFormSQL, "id", cloneDataFormBean.getNewDataFormId());
        DataForm newDataForm = dataQuery.exec(dataFormDBRowMapper,()->{
            return dataQuery.selectOne(DataForm.class, dataFormSQL, "id", cloneDataFormBean.getNewDataFormId());
        });

        if (newDataForm != null) {
            throw new DataFormException("new dataFormId has existed");
        }

        DataForm oldDataForm = dataFormMapper.getDataForm(cloneDataFormBean.getOldDataFormId());
        DataForm cloneDataForm = BeanKit.deepClone(oldDataForm);
        cloneDataForm.setId(cloneDataFormBean.getNewDataFormId());
        cloneDataForm.setName("copyof" + oldDataForm.getName());
        dataFormMapper.save(cloneDataForm);
        return cloneDataForm;
    }

    public DataFormElement getDataFormElementDetail(String dataformId, String code) {
        DataForm dataForm = getDataForm(dataformId);
        return dataForm.getElement(code);
//        dataAccessor.getDataQuery().setRowMapperOnce(dataFormElementDBRowMapper);
//        DataFormElement elementOne = dataAccessor.selectOne(DataFormElement.class,
//                "SELECT * FROM FOWK_DATAFORM_ELEMENT where DATAFORM_ID=:dataFormId and CODE=:code",
//                "dataFormId", dataformId, "code", code);
//
//        dataAccessor.getDataQuery().setRowMapperOnce(dataFormValidatorRowMapper);
//        List<DataFormElement.FormElementValidator> validators = dataAccessor.selectList(DataFormElement.FormElementValidator.class,
//            DataFormMapperDBTableImpl.VALIDATOR_SELECT_SQL,"elementCode",elementOne.getCode());
//        elementOne.setValidatorList(validators);
//        return Optional.ofNullable(elementOne).orElseGet(DataFormElement::new);
    }

    @Transactional
    public DataFormElement saveDataFormElement(DataFormElement element, String dataformId) {
        dataFormMapper.saveDataFormElement(element);
        return element;
    }

    public PaginationData<DataForm> getPageDataForms(Integer size, Integer index,
                                                     Map<String, ?> filterMap, Map<String, Object> sortMap) {
        StringBuffer sqlBuffer = new StringBuffer("select * from FOWK_DATAFORM");
        Map<String, ?> queryParameters = MapKit.newHashMap();
        String filterSql = this.parseFilterFillParam(queryParameters, filterMap);
        sqlBuffer.append(filterSql);
        String orderSql = this.parseOrderBySql(sortMap);
        sqlBuffer.append(orderSql);

        PaginationQuery query = new PaginationQuery();
        query.setQuery(sqlBuffer.toString());
        query.setIndex(index);
        query.setSize(size);
        query.getParameterMap().putAll(queryParameters);
//        dataAccessor.getDataQuery().setRowMapper(dataFormDBRowMapper);
//        return dataAccessor.selectListPagination(DataForm.class, query);

        return dataQuery.exec(dataFormDBRowMapper,()->{
            return dataQuery.selectListPagination(DataForm.class, query);
        });
    }

    private String parseOrderBySql(Map<String, Object> sortMap) {
        StringBuffer sqlBuffer = new StringBuffer();
        List<String> orderBySqlList = ListKit.newArrayList();
        if (sortMap != null && sortMap.size() > 0) {
            sqlBuffer.append(" order by ");
            Iterator<String> iterator = sortMap.keySet().iterator();
            while (iterator.hasNext()) {
                String code = iterator.next();
                ValueObject value = ValueObject.valueOf(sortMap.get(code));
                String condItem = buildOrderBySQL(code, value);
                orderBySqlList.add(condItem);
            }
        }
        return sqlBuffer.append(orderBySqlList.stream().reduce((s1, s2) -> s1 + "," + s2).get()).toString();
    }

    private String buildOrderBySQL(String code, ValueObject value) {
        StringBuffer filterBuffer = new StringBuffer();
        filterBuffer.append(underLining(code)).append(" " + value.strValue());
        return filterBuffer.toString();
    }

    private String parseFilterFillParam(Map<String, ?> param, Map<String, ?> filterParameters) {
        StringBuffer filterBuffer = new StringBuffer();
        filterParameters.remove("_");
        if (filterParameters != null && filterParameters.size() > 0) {
            filterBuffer.append(" Where ");
            Iterator<String> iterator = filterParameters.keySet().iterator();
            List<String> condItems = ListKit.newArrayList();
            while (iterator.hasNext()) {
                String code = iterator.next();
                ValueObject value = ValueObject.valueOf(filterParameters.get(code));
                String condItem = buildFilterSQL(code, value, param);
                condItems.add(condItem);
            }
            String FilterSql = condItems.stream().reduce((s1, s2) -> s1 + " and " + s2).get();
            filterBuffer.append(FilterSql);
        }
        return filterBuffer.toString();
    }

    private String buildFilterSQL(String code, ValueObject value, Map<String, ?> param) {
        Map<String, Object> paramMap = (Map<String, Object>) param;
        StringBuffer filterBuffer = new StringBuffer();
        filterBuffer.append(underLining(code)).append(" LIKE ").append(":").append(code);
        paramMap.put(code, "%" + value.strValue() + "%");
        return filterBuffer.toString();
    }

    public void deleteDataForm(String dataFormId) {
        dataFormMapper.delete(dataFormId);
    }

    public void deleteAll() {
        dataFormMapper.deleteAll();
    }

    public List<DataFormElement> parseElementsFromTables(String dataFromId,String... tables) {
        List<DataFormElement> retList = ListKit.newArrayList();
        boolean singleTable = tables.length==1;
        for (String table : tables) {
            Connection connection = null;
            String tableName = table;
            String tableAlias = "";

            String tableExpr = StringKit.trim(table);
            String[] tableExprs = tableExpr.split("\\s+");
            if(tableExprs.length==2){
                tableName = tableExprs[0];
                tableAlias = tableExprs[1];
            }
            if(tableExprs.length==3){
                tableName = tableExprs[0];
                tableAlias = tableExprs[2];
            }

            try {
                connection = jdbcTemplate.getDataSource().getConnection();
                List<MapData> dataList = SQLKit.getTableMeta(connection,tableName);
//                System.out.println(JSONKit.toJsonString(dataList));
                fillElement(dataFromId,tableAlias,retList,dataList);

            } catch (SQLException e) {
                throw new DataFormException(e);
            } finally {
                IOKit.close(connection);
            }
        }

        //重新处理排序号
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        if(retList.size()>100){
            decimalFormat = new DecimalFormat("00000");
        }
        for(int i=0;i<retList.size();i++){
            DataFormElement element = retList.get(i);
            int sortNumber = i * 10;
            String sortCode = decimalFormat.format(sortNumber);
//            System.out.println(sortCode);
            element.setSortCode(sortCode);
        }

        return retList;
    }



    private void fillElement(String dataFromId,String tableAlias,List<DataFormElement> retList,List<MapData> dataList) throws SQLException {
        for(int i=0;i<dataList.size();i++){
            MapData row = dataList.get(i);

            DataFormElement element = new DataFormElement();
            element.setTable(tableAlias);

            String columnName = row.getValue("columnName").strValue();
            String comment = row.getValue("comment").strValue();
            int dataType = row.getValue("dataType").intValue();

            element.setDataformId(dataFromId);
            element.setColumn(columnName.toUpperCase());
            element.setCode(StringKit.underlineToCamel(columnName));
            element.setName(comment);
            fillElementDataType(element,dataType);

            retList.add(element);
        }
    }

    /**
     * 填充数据类型，数据格式，UI样式
     * @param element
     * @param columnType
     */
    private void fillElementDataType(DataFormElement element,int columnType){
        element.setDataType(ElementDataType.String);
        element.getElementUIHint().setDataFormat(ElementDataFormat.String);

        switch (columnType){
            case Types.VARCHAR:
            case Types.CHAR:
            case Types.NCHAR:
            case Types.LONGNVARCHAR:
            case Types.LONGVARBINARY:
            case Types.NVARCHAR:
            case Types.CLOB:
            case Types.NCLOB:
            case Types.NULL:
                break;
            case Types.BOOLEAN:
                element.setDataType(ElementDataType.Boolean);break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                element.setDataType(ElementDataType.Date);
                element.getElementUIHint().setEditStyle(ElementDataEditStyle.DatePicker);
                element.getElementUIHint().setDataFormat(ElementDataFormat.Date);
                break;
            case Types.INTEGER:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.BIGINT:
                element.setDataType(ElementDataType.Integer);
                element.getElementUIHint().setEditStyle(ElementDataEditStyle.Integer);
                element.getElementUIHint().setDataFormat(ElementDataFormat.Integer);
                break;
            case Types.DOUBLE:
            case Types.DECIMAL:
            case Types.NUMERIC:
            case Types.FLOAT:
                element.setDataType(ElementDataType.Double);
                element.getElementUIHint().setEditStyle(ElementDataEditStyle.Double);
                element.getElementUIHint().setDataFormat(ElementDataFormat.Double);
                break;
        }
    }

    private String underLining(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        String returnValue = str.substring(0, 1).toLowerCase() + str.substring(1);

        String[] ss = str.split("(?<!^)(?=[A-Z])");
        if (ss.length > 1) {
            List<String> list = Arrays.asList(ss);
            returnValue = list.stream()
                    .reduce((s1, s2) -> lowerCaseFirstLetter(s1) + "_" + lowerCaseFirstLetter(s2))
                    .get();
        }
        return returnValue;
    }

    public static String lowerCaseFirstLetter(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }
}
