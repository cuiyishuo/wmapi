package com.wanmen.mappers;

import com.wanmen.model.BaseCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseMapper {
    // 批量查询同一场景的用例
    List<BaseCase> findBaseCases(String scenes);
}
