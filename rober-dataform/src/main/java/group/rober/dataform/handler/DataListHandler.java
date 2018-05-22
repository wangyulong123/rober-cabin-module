package group.rober.dataform.handler;

import group.rober.dataform.model.DataForm;
import group.rober.dataform.validator.ValidateResult;
import group.rober.sql.core.PaginationData;

import java.util.List;
import java.util.Map;

/**
 * 列表数据的查询,保存处理
 * Created by tisir<yangsong158@qq.com> on 2017-05-29
 */
public interface DataListHandler<T> extends DataObjectHandler {


    
    /**
     * 查询列表数据
     * @param dataForm
     * @param queryParameters 查询条件参数
     * @param filterParameters 筛选条件参数
     * @param sortMap 排序参数
     * @param pageSize 分页大小
     * @param pageIndex 分页索引
     * @return
     */
    PaginationData<T> query(DataForm dataForm, Map<String, ?> queryParameters, Map<String, ?> filterParameters, Map<String, ?> sortMap, int pageSize, int pageIndex);

    /**
     * 插入
     * @param dataForm
     * @param dataList
     * @return
     */
    public int insert(DataForm dataForm, List<T> dataList);

    /**
     * 更新
     * @param dataForm
     * @param dataList
     * @return
     */
    public int update(DataForm dataForm, List<T> dataList);

    /**
     * 保存列表数据
     * @param dataForm
     * @param dataList
     * @return
     */
    int save(DataForm dataForm,List<T> dataList);

    /**
     * 删除列表数据
     * @param dataForm
     * @param dataList
     * @return
     */
    Integer delete(DataForm dataForm,List<T> dataList);

    /**
     * 校验数据
     * @param dataForm
     * @param dataList
     * @return
     */
    List<ValidateResult> validate(DataForm dataForm,List<T> dataList);

}
