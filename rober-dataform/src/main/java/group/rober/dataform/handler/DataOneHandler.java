package group.rober.dataform.handler;

import group.rober.dataform.model.DataForm;
import group.rober.dataform.validator.ValidateResult;

import java.util.Map;

/**
 * 单一记录的查询,保存处理
 * Created by tisir<yangsong158@qq.com> on 2017-05-29
 */
public interface DataOneHandler<T> extends DataObjectHandler {

    /**
     * 根据DataForm创建数据对象
     * @param dataForm
     * @return
     */
    T createDataObject(DataForm dataForm);

    /**
     * 查询单个数据对象
     * @param dataForm
     * @param queryParameters
     * @return
     */
    T query(DataForm dataForm, Map<String, ?> queryParameters);

    /**
     * 插入
     * @param dataForm
     * @param object
     * @return
     */
    int insert(DataForm dataForm, T object);

    /**
     * 更新
     * @param dataForm
     * @param object
     * @return
     */
    int update(DataForm dataForm, T object);

    /**
     * 保存列表数据
     * @param dataForm
     * @param object
     * @return
     */
    int save(DataForm dataForm, T object);

    /**
     * 删除列表数据
     * @param dataForm
     * @param object
     * @return
     */
    int delete(DataForm dataForm,T object);

    /**
     * 校验数据
     * @param dataForm
     * @param object
     * @return
     */
    ValidateResult validate(DataForm dataForm, T object);
}
