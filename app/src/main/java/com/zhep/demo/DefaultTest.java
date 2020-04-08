package com.zhep.demo;

/**
 * @Author piaozhe
 * @Date 2020/4/2 15:40
 */
public class DefaultTest {
    //default修饰的在本包可以访问，其他包无法访问
    //public 其他所以类都能访问
    //private 本类可以访问
    //protected 本包访问 其他包访问不到
   protected String defaultStr = "xxxx";
}
