package com.ghz.flow.controller.job;

import com.ghz.flow.base.mapper.RolerMapper;
import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.RolerExample;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.pojo.UserExample;
import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.base.util.Page;
import com.ghz.flow.base.util.PageUtil;
import com.ghz.flow.service.IProcessDefinitionService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 流程定义管理
 */
@Controller
@RequestMapping(value ="/processDef" )
public class ProcessDefiitionManageController {


    @Resource
    private FlowWorkServiceImpl flowWorkServiceImpl;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RolerMapper rolerMapper;
    @Resource
    private IProcessDefinitionService iProcessDefinitionService;

    @RequestMapping(value = "/toProdefManageUI")
    public   ModelAndView  toProdefManageUI(HttpServletRequest request){
        ModelAndView modelAndView =new ModelAndView("/processDefinitionView/process-list");

        Page<ProcessDefinition>  page=new Page<ProcessDefinition>(PageUtil.PAGE_SIZE);
        int[] init = PageUtil.init(page, request);
        ProcessDefinitionQuery processDefinitionQuery = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitions = processDefinitionQuery
                .orderByProcessDefinitionVersion().asc().listPage(init[0], init[1]);
        page.setResult(processDefinitions);
        page.setTotalCount(processDefinitionQuery.count());
        /*User user = (User)Request.getRequest().getSession().getAttribute("user");
        List<User> users = userMapper.selectAllUser(user);

        List<Roler> rolers = rolerMapper.selectAllRoler();*/
        modelAndView.addObject("page",page);
       /* modelAndView.addObject("users",users);
        modelAndView.addObject("groups",rolers);*/
        // 读取每个流程定义的候选属性
        Map<String, Map<String, List<? extends Object>>> linksMap = setCandidateUserAndGroups(processDefinitions);
        modelAndView.addObject("linksMap", linksMap);
        return  modelAndView;
    }

    /**
     * 读取已设置的候选启动人、组
     * @param processDefinitionId
     * @return
     */
    @RequestMapping(value = "startable/read/{processDefinitionId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<String>> readStartableData(@PathVariable("processDefinitionId") String processDefinitionId) {
        Map<String, List<String>> datas = new HashMap<String, List<String>>();
        ArrayList<String> users = new ArrayList<String>();
        ArrayList<String> groups = new ArrayList<String>();

        List<IdentityLink> links = flowWorkServiceImpl.getRepositoryService().getIdentityLinksForProcessDefinition(processDefinitionId);
        for (IdentityLink link : links) {
            if (StringUtils.isNotBlank(link.getUserId())) {
                users.add(link.getUserId());
            }
            if (StringUtils.isNotBlank(link.getGroupId())) {
                groups.add(link.getGroupId());
            }
        }
        datas.put("users", users);
        datas.put("groups", groups);
        return datas;
    }


    /**  ()
     * 读取流程定义的相关候选启动人、组，根据link信息转换并封装为User、Group对象
     * @param processDefinitionList
     * @return
     */
    private Map<String, Map<String, List<? extends Object>>> setCandidateUserAndGroups(List<ProcessDefinition> processDefinitionList) {
        Map<String, Map<String, List<? extends Object>>> linksMap = new HashMap<String, Map<String, List<? extends Object>>>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            //取流程定义的相关候选启动人、组，根据link信息转换并封装为User、Group对象
            List<IdentityLink> identityLinks =flowWorkServiceImpl.getRepositoryService()
                    .getIdentityLinksForProcessDefinition(processDefinition.getId());

            Map<String, List<? extends Object>> single = new Hashtable<String, List<? extends Object>>();
            List<User> linkUsers = new ArrayList<User>();
            List<Roler> linkGroups = new ArrayList<Roler>();

            for (IdentityLink link : identityLinks) {
                if (StringUtils.isNotBlank(link.getUserId())) {
                    UserExample userExample=new UserExample();
                    UserExample.Criteria criteria = userExample.createCriteria();
                    criteria.andLoginnameEqualTo(link.getUserId());
                    List<User> users = userMapper.selectByExample(userExample);
                    linkUsers.addAll(users);
                } else if (StringUtils.isNotBlank(link.getGroupId())) {
                    RolerExample rolerExample=new RolerExample();
                    RolerExample.Criteria criteria = rolerExample.createCriteria();
                    criteria.andRolernameEqualTo(link.getGroupId());
                    List<Roler> rolers = rolerMapper.selectByExample(rolerExample);
                    linkGroups.addAll(rolers);

                }
            }

            single.put("user", linkUsers);
            single.put("group", linkGroups);

            linksMap.put(processDefinition.getId(), single);

        }
        return linksMap;
    }

    @RequestMapping(value = "/deleteDeployment")
    public  String  deleteDeployment(@RequestParam(value = "deploymentId",required = true)String deploymentId){

        flowWorkServiceImpl.getRepositoryService().deleteDeployment(deploymentId,true);

        return "redirect:/processDef/toProdefManageUI.action";
    }

    @RequestMapping("/stopProdef/{state}")
    public String   stopProdef(@PathVariable("state")String state,
                               @RequestParam(value = "processDefinitionId") String processDefinitionId,
                               @RequestParam(value = "cascade", required = false) boolean cascadeProcessInstances,
                               @RequestParam(value = "effectiveDate", required = false) String strEffectiveDate){
        Date  effectiveDate=null;
        if(StringUtils.isNoneBlank(strEffectiveDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                effectiveDate = simpleDateFormat.parse(strEffectiveDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //流程激活
        if(StringUtils.equals(state,"active")){
          flowWorkServiceImpl.getRepositoryService().activateProcessDefinitionById(processDefinitionId,cascadeProcessInstances,effectiveDate);
        }
        //流程挂起
        if(StringUtils.equals("suspend",state)){
            flowWorkServiceImpl.getRepositoryService().suspendProcessDefinitionById(processDefinitionId,cascadeProcessInstances,effectiveDate);
        }


        return "redirect:/processDef/toProdefManageUI.action";
    }

    /**
     * 设置流程定义对象的候选人、候选组
     * @return
     */
    @RequestMapping(value = "startable/set/{processDefinitionId}", method = RequestMethod.POST)
    @ResponseBody
    public String addStartables(@PathVariable("processDefinitionId") String processDefinitionId,
                                @RequestParam(value = "users[]", required = false) String[] users, @RequestParam(value = "groups[]", required = false) String[] groups) {
        iProcessDefinitionService.setStartables(processDefinitionId, users, groups);
        return "true";
    }

}

