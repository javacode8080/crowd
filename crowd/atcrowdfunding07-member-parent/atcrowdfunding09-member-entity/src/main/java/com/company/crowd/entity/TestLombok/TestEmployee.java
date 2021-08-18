package com.company.crowd.entity.TestLombok;

/*Alt+7查看一个类中的所有属性和方法*/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TestEmployee {

    private String empName;
    private Integer empAge;
    private String empEmail;

    public void test(){
        TestEmployee testEmployee = new TestEmployee();
        TestEmployee 张三 = new TestEmployee("张三", 21, "2020@163.com");
        Integer empAge = getEmpAge();
        setEmpAge(12);
        张三.toString();

    }

}
