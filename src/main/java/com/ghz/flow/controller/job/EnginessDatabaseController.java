package com.ghz.flow.controller.job;

import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.base.util.Page;
import com.ghz.flow.base.util.PageUtil;
import org.activiti.engine.management.TableMetaData;
import org.activiti.engine.management.TablePage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by admi on 2017/9/17.
 */
@Controller
@RequestMapping("/enginessDatabase")
public class EnginessDatabaseController {

    @Resource
    private FlowWorkServiceImpl flowWorkServiceImpl;
    @RequestMapping("/toDatbaseUI")
    public ModelAndView  toDatbaseUI(@RequestParam(value = "tableName",required = false)String tableName,
                                     HttpServletRequest request){
        ModelAndView  modelAndView=new ModelAndView("/processDefinitionView/database");
        //读取表(key为表名，value为表所对应的数据长度)
        Map<String, Long> tableCount = flowWorkServiceImpl.getManagementService().getTableCount();
        modelAndView.addObject("tableCount",tableCount);

        //读取表记录
        if(StringUtils.isNoneBlank(tableName)){
            //TableMetaData  得到表的结构（字段的名字colunmNames和类型colunmTypes）
            TableMetaData tableMetaData =
                    flowWorkServiceImpl.getManagementService().getTableMetaData(tableName);
            modelAndView.addObject("tableMetaData", tableMetaData);
            Page<Map<String,Object>> page=new Page<Map<String, Object>>(PageUtil.PAGE_SIZE);
            int[] init = PageUtil.init(page, request);
            //得到表的数据
            TablePage tablePage = flowWorkServiceImpl.getManagementService().createTablePageQuery()
                    .tableName(tableName)
                    .listPage(init[0], init[1]);

            page.setResult(tablePage.getRows());
            page.setTotalCount(tableCount.get(tableName));
            modelAndView.addObject("page", page);
        }
        return  modelAndView;

    }
}
