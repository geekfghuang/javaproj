package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Created by huangfugui on 2017/4/5.
 */
public class SqlSessionFactoryUtil {

    private static SqlSessionFactory sqlSessionFactory = null;
    private SqlSessionFactoryUtil(){}

    private static SqlSessionFactory initSqlSessionFactory(){
        String resources = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resources);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(sqlSessionFactory==null){
            synchronized (SqlSessionFactoryUtil.class){
                if(sqlSessionFactory==null){
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                }
            }
        }
        return sqlSessionFactory;
    }
    public static SqlSession openSqlSession(){
        if(sqlSessionFactory==null){
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}