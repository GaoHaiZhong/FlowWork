import com.ghz.flow.base.mapper.ApplicationMapper;
import com.ghz.flow.base.pojo.Application;
import com.ghz.flow.base.pojo.ApplicationExample;
import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.service.IProcessDefinitionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.task.Task;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admi on 2017/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-transaction.xml"
        ,"classpath:spring/springmvc.xml","classpath:mybatis/sqlMapperConfig.xml"})
public class serviceImp {


    @Autowired
    FlowWorkServiceImpl flowWorkServiceImpl;
    @Autowired
    IProcessDefinitionService iProcessDefinitionService;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void test(){

      /*  flowWorkServiceImpl.getRepositoryService().deleteDeployment("40023",true);*/
        try {
            iProcessDefinitionService.getPngStream("payment:1:2506");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskId("122514").singleResult();
        task.setAssignee("王五");

    }

    /**
     * 使用RowBounds方式的查询的调用
     */

    @Test
    public  void test3(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> objects = sqlSession.selectList("域名.方法", new Application() {}, new RowBounds(1, 10));
        System.out.println(objects);
    }

    @Test
    public  void test4(){

        SqlSession sqlSession= sqlSessionFactory.openSession();
        ApplicationMapper countryMapper= sqlSession.getMapper(ApplicationMapper.class);

            //获取第1页，10条内容，默认查询总数count
            PageHelper.startPage(1,10);

            //紧跟着的第一个select方法会被分页
        ApplicationExample applicationExample=new ApplicationExample();
        ApplicationExample.Criteria criteria = applicationExample.createCriteria();
        criteria.andApplicantEqualTo(3);
        criteria.andStatusEqualTo("审批中");
        List<Application> list= countryMapper.selectByExample(applicationExample);
        PageInfo pageInfo=new PageInfo(list);
        long total = pageInfo.getTotal();
        System.out.println(list);

        }


}
