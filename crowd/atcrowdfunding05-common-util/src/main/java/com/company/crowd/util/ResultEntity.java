package com.company.crowd.util;


/**
 * 同意整个项目的AJAX请求返回结果，未来也可以用于分布式架构各个模块之间调用时统一返回类型
 * @param <T>
 */
public class ResultEntity<T> {
    public static final String SUCCESS="SUCCESS";
    public static final String FAILED="FAILED";
    private String result;//用来封装当前的结果是成功还是失败
    private String message;//请求处理失败时，返回的错误消息
    private T data;//需要返回的数据

    /**
     * 处理成功且不需要返回数据的时候使用该方法
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithoutData(){//注意：第一个<Type>代表声明一个泛型，后面的两个<Type>都是使用这个泛型；带泛型的类叫做泛型类，带泛型的方法叫做泛型方法
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    /**
     * 处理成功需要返回数据使用的方法
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type data){//注意：第一个<Type>代表声明一个泛型，后面的两个<Type>都是使用这个泛型；带泛型的类叫做泛型类，带泛型的方法叫做泛型方法
        return new ResultEntity<Type>(SUCCESS,null,data);
    }

    /**
     * 请求失败使用的方法，data表示失败信息
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(Type data){//注意：第一个<Type>代表声明一个泛型，后面的两个<Type>都是使用这个泛型；带泛型的类叫做泛型类，带泛型的方法叫做泛型方法
        return new ResultEntity<Type>(FAILED,null,data);
    }


    public static <Type> ResultEntity<Type> failed(String message){//注意：第一个<Type>代表声明一个泛型，后面的两个<Type>都是使用这个泛型；带泛型的类叫做泛型类，带泛型的方法叫做泛型方法
        return new ResultEntity<Type>(FAILED,message,null);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "result='" + result + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }
}
