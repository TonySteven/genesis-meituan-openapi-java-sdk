package com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.genesis.org.cn.genesismeituanopenapijavasdk.database.service.IBaseService;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 批量修改封装实现类
 * @author LP
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IBaseService<T> {

    /**
     * 批量修改 以wrapper.set内容修改
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function){
        return updateBatch(entityList,function,DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量修改 以实体类内容修改
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatchNoneEntity(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function){
        return updateBatchNoneEntity(entityList,function,DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量更新封装返回影响行数
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @param batchSize 批量修改分批次 每批次条数
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function, int batchSize){
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE);
        List<BatchResult> resultList = this.customExecuteBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<LambdaUpdateWrapper<T>> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, null);
            param.put(Constants.WRAPPER, function.apply(entity));
            sqlSession.update(sqlStatement, param);
        });
        return sumUpdateCount(resultList);
    }


    /**
     * 批量修改 以实体类内容修改
     * @param entityList 要修改的数据
     * @param function 修改条件
     * @param batchSize 批量修改分批次 每批次条数
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatchNoneEntity(List<T> entityList, Function<T, LambdaUpdateWrapper<T>> function, int batchSize){
        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE);
        List<BatchResult> resultList = this.customExecuteBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            param.put(Constants.WRAPPER, function.apply(entity));
            sqlSession.update(sqlStatement, param);
        });
        return sumUpdateCount(resultList);
    }

    protected <E> List<BatchResult> customExecuteBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return customExecuteBatch(this.getSqlSessionFactory(), this.log, list, batchSize, consumer);
    }

    public static <E> List<BatchResult> customExecuteBatch(SqlSessionFactory sqlSessionFactory, Log log, Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        Assert.isFalse(batchSize < 1, "batchSize must not be less than one");
        List<BatchResult> resultList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)) {
            customExecuteBatch(sqlSessionFactory, log, (sqlSession) -> {
                int size = list.size();
                int idxLimit = Math.min(batchSize, size);
                int i = 1;

                for (Iterator<E> var7 = list.iterator(); var7.hasNext(); ++i) {
                    E element = var7.next();
                    consumer.accept(sqlSession, element);
                    if (i == idxLimit) {
                        resultList.addAll(sqlSession.flushStatements());
                        idxLimit = Math.min(idxLimit + batchSize, size);
                    }
                }

            });
        }
        return resultList;
    }

    public static boolean customExecuteBatch(SqlSessionFactory sqlSessionFactory, Log log, Consumer<SqlSession> consumer) {
        try {
            SqlSessionHolder sqlSessionHolder = (SqlSessionHolder)TransactionSynchronizationManager.getResource(sqlSessionFactory);
            boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
            SqlSession sqlSession;
            if (sqlSessionHolder != null) {
                sqlSession = sqlSessionHolder.getSqlSession();
                sqlSession.commit(!transaction);
            }

            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            if (!transaction) {
                log.warn("SqlSession [" + sqlSession + "] Transaction not enabled");
            }

            try {
                consumer.accept(sqlSession);
                sqlSession.commit(!transaction);
            } catch (Throwable var14) {
                sqlSession.rollback();
                Throwable unwrapped = ExceptionUtil.unwrapThrowable(var14);
                if (unwrapped instanceof PersistenceException) {
                    MyBatisExceptionTranslator myBatisExceptionTranslator = new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
                    Throwable throwable = myBatisExceptionTranslator.translateExceptionIfPossible((PersistenceException)unwrapped);
                    if (throwable != null) {
                        throw throwable;
                    }
                }

                throw ExceptionUtils.mpe(unwrapped);
            } finally {
                sqlSession.close();
            }

            return true;
        } catch (Throwable var16) {
            throw new RuntimeException(var16);
        }
    }

    private int sumUpdateCount(List<BatchResult> resultList){
        int count = 0;
        for(BatchResult result:resultList){
            count += Arrays.stream(result.getUpdateCounts()).sum();
        }
        return count;
    }

    /**
     * 查询全数据分批查询封装
     * @param queryWrapper 查询条件
     * @return 结果
     */
    @Override
    public List<T> listAll(LambdaQueryWrapper<T> queryWrapper){
        return this.listAll(queryWrapper,500);
    }

    /**
     * 查询全数据分批查询封装
     * @param queryWrapper 查询条件
     * @return 结果
     */
    @Override
    public List<T> listAll(LambdaQueryWrapper<T> queryWrapper,int pageSize){
        int pageNum = 1;
        List<T> list = new ArrayList<>();
        while (true){
            IPage<T> page = this.page(new Page<>(pageNum,pageSize,false),queryWrapper);
            list.addAll(page.getRecords());
            if(page.getRecords().isEmpty() || page.getRecords().size() < pageSize){
                break;
            }
            pageNum++;
        }

        return list;
    }

}
