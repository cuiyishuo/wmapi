package com.wanmen.mappers.business;

import com.wanmen.model.business.NewComerBusCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewComerBusMapper {
    // 批量查询同一场景的用例
    List<NewComerBusCase> findNewComerBusCases(String scenes);
}
