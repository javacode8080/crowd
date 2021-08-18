package com.company.crowd.test;


import com.company.crowd.entity.Admin;
import com.company.crowd.entity.Role;
import com.company.crowd.mapper.AdminMapper;
import com.company.crowd.mapper.RoleMapper;
import com.company.crowd.service.api.AdminService;
import com.company.crowd.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@RunWith(SpringJUnit4ClassRunner.class)//在類上標記必要的註解，Spring整合JUnit，用來實現Spring測試
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})//該標籤是用來加載Spring配置文件
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;//这个就是事务装配出来的[注意这个地方要价在另外一个配置文件spring-persist-tx.xml]

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testRoleSave(){
        for(int i=0;i<235;i++){
            roleMapper.insert(new Role(null,"Role"+i));
        }
    }


    @Test
    public void test(){
        for(int i=0;i<238;i++){

            adminMapper.insert(new Admin(null,"loginAcct"+i,CrowdUtil.md5("userPawd"+i),"userName"+i,"userName"+i+"@163.com",null));
        }
    }


    @Test
    public void testlog(){
        // 获取日志记录对象,
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        /*不同級別：級別由高到低，打印內容由多到少*/
        logger.debug("Hello");
        logger.debug("Hello");
        logger.debug("Hello");
        logger.info("Info level");
        logger.info("Info level");
        logger.info("Info level");
        logger.warn("Warn level");
        logger.warn("Warn level");
        logger.warn("Warn level");
        logger.error("error level");
        logger.error("error level");
        logger.error("error level");


    }

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null, "zhangsan", "123123", "傑瑞", "jerry@qq.com", null);
        int insert = adminMapper.insert(admin);
        //如果在實際開發中，所有想查看數值的地方都是用System.out流方式進行打印，會給項目上線帶來問題，
        //systemout其實是一個IO操作，通常IO操作是很消耗性能的，如果項目中out很多，對性能的影響就會比較大。
        //及時上線前刪除很可能有遺漏而且非常麻煩，如果使用日誌系統通過日誌的級別可以批量控制信息的打印
        System.out.println("共："+insert+"行受影響");
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "lisi", "164525", "李四", "lisi@163.com", null);
        adminService.saveAdmin(admin);

    }
}
