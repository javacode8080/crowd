package com.company.crowd.service.api;

import com.company.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    List<Auth> getAll();

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
