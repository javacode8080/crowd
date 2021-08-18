package com.company.crowd.service.impl;

import com.company.crowd.constants.CrowdConstant;
import com.company.crowd.entity.Admin;
import com.company.crowd.entity.AdminExample;
import com.company.crowd.exception.LoginAcctAlreadyInUseException;
import com.company.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.company.crowd.exception.LoginFailedException;
import com.company.crowd.mapper.AdminMapper;
import com.company.crowd.service.api.AdminService;
import com.company.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
/*    @Bean//这里是不允许使用直接创建Bean的，必须要使用自动装配
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }*/

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 旧数据如下：
        // adminId roleId
        // 1 1（要删除）
        // 1 2（要删除）
        // 1 3
        // 1 4
        // 1 5
        // 新数据如下：
        // adminId roleId
        // 1 3（本来就有）
        // 1 4（本来就有）
        // 1 5（本来就有）
        // 1 6（新）
        // 1 7（新）
        // 为了简化操作： 先根据 adminId 删除旧的数据， 再根据 roleIdList 保存全部新的数据
        // 1.根据 adminId 删除旧的关联关系数据
        adminMapper.deleteOLdRelationship(adminId);
        // 2.根据 roleIdList 和 adminId 保存新的关联关系
        if(roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public void update(Admin admin) {
        // “Selective”表示有选择的更新，对于null值的字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();

            logger.info("异常全类名="+e.getClass().getName());

            if(e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);/*pageInfo是非侵入时语言，如果你想要分页就加入这句话，不分页就去掉，不会对其他代码产生任何影响*/
        // 2.查询 Admin 数据
        List<Admin> adminList = adminMapper.selectAdminListByKeyword(keyword);
        // ※辅助代码： 打印 adminList 的全类名
        Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
        logger.debug("adminList 的全类名是： " + adminList.getClass().getName());
        // 3.为了方便页面使用将 adminList 封装为 PageInfo
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return pageInfo;

    }



    @Override
    public void saveAdmin(Admin admin) {
        // 生成当前系统时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);
        // 针对登录密码进行加密
        String source = admin.getUserPswd();
        String encoded =passwordEncoder.encode(source);//带盐值的MD5加密
       // String encoded = CrowdUtil.md5(source);//MD5加密
        admin.setUserPswd(encoded);
        // 执行保存， 如果账户被占用会抛出异常
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
        // 检测当前捕获的异常对象， 如果是 DuplicateKeyException 类型说明是账号重复导致的
            if(e instanceof DuplicateKeyException) {
                // 抛出自定义的 LoginAcctAlreadyInUseException 异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            // 为了不掩盖问题， 如果当前捕获到的不是 DuplicateKeyException 类型的异常， 则把当前捕获到的异常对象继续向上抛出
            throw e;
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());

    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登录账号查询Admin对象
        // ①创建AdminExample对象
        AdminExample adminExample = new AdminExample();

        // ②创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // ③在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        // ④调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);

        // 2.判断Admin对象是否为null
        if(list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if(list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);

        // 3.如果Admin对象为null则抛出异常
        if(admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4.如果Admin对象不为null则将数据库密码从Admin对象中取出
        String userPswdDB = admin.getUserPswd();

        // 5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);

        // 6.对密码进行比较:比较两个变量的equals工具方法，用来放置空指针异常!Objects.equals(userPswdDB, userPswdForm)
        if(!Objects.equals(userPswdDB, userPswdForm)) {
            // 7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 8.如果一致则返回Admin对象
        return admin;
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> list = adminMapper.selectByExample(example);
        Admin admin = list.get(0);
        return admin;
    }
}
