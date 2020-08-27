package com.wanmen.mappers.reward;

import com.wanmen.model.reward.NewComerCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewComerMapper {
    // 批量查询同一场景的用例
    List<NewComerCase> findNewComerCases(String scenes);
}
