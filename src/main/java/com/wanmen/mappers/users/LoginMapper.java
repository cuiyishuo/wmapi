package com.wanmen.mappers.users;

import com.wanmen.model.users.LoginCase;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {
    // 批量查询同一场景的用例
    List<LoginCase> findLoginCases(String scenes);
}
