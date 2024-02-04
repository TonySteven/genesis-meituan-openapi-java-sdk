package com.genesis.org.cn.genesismeituanopenapijavasdk.database.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.function.Function;

/**
 * 批量修改封装接口
 * @author LP
 */
public interface IBaseService<T> extends IService<T> {
    /**
     * 批量修改 以wrapper.set内容修改
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @return 影响行数
     */
    int updateBatch(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function);

    /**
     * 批量修改 以实体类内容修改
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @return 影响行数
     */
    int updateBatchNoneEntity(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function);

    /**
     * 批量更新封装返回影响行数
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @param batchSize 批量修改分批次 每批次条数
     * @return 影响行数
     */
    int updateBatch(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function, int batchSize);


    /**
     * 批量修改 以实体类内容修改
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @param batchSize 批量修改分批次 每批次条数
     * @return 影响行数
     */
    int updateBatchNoneEntity(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function, int batchSize);

    /**
     * 查询全数据分批查询封装
     * @param queryWrapper 查询条件
     * @return 结果
     */
    List<T> listAll(LambdaQueryWrapper<T> queryWrapper);

    /**
     * 查询全数据分批查询封装
     * @param queryWrapper 查询条件
     * @return 结果
     */
    List<T> listAll(LambdaQueryWrapper<T> queryWrapper,int pageSize);
}
