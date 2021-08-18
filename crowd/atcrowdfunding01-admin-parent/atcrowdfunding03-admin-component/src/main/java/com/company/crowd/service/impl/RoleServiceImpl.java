package com.company.crowd.service.impl;

import com.company.crowd.entity.Role;
import com.company.crowd.entity.RoleExample;
import com.company.crowd.mapper.RoleMapper;
import com.company.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        //eg:delete from t_role where id in (5,8,12);
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 2.执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        // 3.封装为PageInfo对象返回
        return new PageInfo<>(roleList);
    }
}
