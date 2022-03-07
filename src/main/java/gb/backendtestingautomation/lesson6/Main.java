package gb.backendtestingautomation.lesson6;

import gb.backendtestingautomation.lesson6.db.dao.CategoriesMapper;
import gb.backendtestingautomation.lesson6.db.model.CategoriesExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    static String resource = "mybatis-config.xml";

    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory;
        String resource = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    private static CategoriesMapper getCategoriesMapper(String resource) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSessionFactory.openSession();
        SqlSession session = sqlSessionFactory.openSession();
        return session.getMapper(CategoriesMapper.class);
    }

    public static Integer countNumberOfAllCategories() throws IOException {
        CategoriesMapper categoriesMapper = getCategoriesMapper(resource);
        CategoriesExample example = new CategoriesExample();
        return Math.toIntExact(categoriesMapper.countByExample(example));
    }

}
