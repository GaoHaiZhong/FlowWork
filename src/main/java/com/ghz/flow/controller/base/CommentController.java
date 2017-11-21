package com.ghz.flow.controller.base;

import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admi on 2017/9/5.
 */
@RequestMapping(value = "/comment")
@Controller
public class CommentController {

    @Autowired
    private FlowWorkServiceImpl flowWorkServiceImpl;

    /**
     *在Comment表中 字段type_ 值为
     * “comment” 表示手动添加的任务办理意见  “event”表示其他操作自动插入的
     *  Action_  字段中记录动作类型
     *     AddComment  表示手动添加意见
     *     AddAttachment    表示创建附件时，由引擎自动创建的
     *     DeleteUserLink，或者 AddUserLink   添加参与人，删除参与人由引擎自动插入
     *
     * @return
     */

    @RequestMapping(value = "/commentList")
    @ResponseBody
    public Map<String,Object> commentList(
                                         @RequestParam(value = "processInstanceId",required = false)String processInstanceId,
                                         @RequestParam(value = "taskId",required = false)String taskId
                                         ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, Object> result = new HashMap<String, Object>();

        Map<String, Object> commentAndEventsMap = new HashMap<String, Object>();

    /*
     * 根据不同情况使用不同方式查询
     * 听啊及附件
     */
        if (StringUtils.isNotBlank(processInstanceId)) {
            List<Comment> processInstanceComments = flowWorkServiceImpl.getTaskService().getProcessInstanceComments(processInstanceId);
            for (Comment comment : processInstanceComments) {
                String commentId = (String) PropertyUtils.getProperty(comment, "id");
                commentAndEventsMap.put(commentId, comment);
            }

            // 提取任务任务名称
            List<HistoricTaskInstance> list = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
            Map<String, String> taskNames = new HashMap<String, String>();
            for (HistoricTaskInstance historicTaskInstance : list) {
                taskNames.put(historicTaskInstance.getId(), historicTaskInstance.getName());
            }
            result.put("taskNames", taskNames);

        }

    /*
     * 查询所有类型的事件
     */
        if (StringUtils.isNotBlank(taskId)) { // 根据任务ID查询
            List<Event> taskEvents = flowWorkServiceImpl.getTaskService().getTaskEvents(taskId);
            for (Event event : taskEvents) {
                String eventId = (String) PropertyUtils.getProperty(event, "id");
                commentAndEventsMap.put(eventId, event);
            }
        }

        result.put("events", commentAndEventsMap.values());

        return result;
    }


    @RequestMapping(value = "/save")
    public Boolean save(@RequestParam("taskId") String taskId, @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                       @RequestParam("message") String message, HttpSession session) {
        User user =(User) session.getAttribute("user");
        flowWorkServiceImpl.getIdentityService().setAuthenticatedUserId(user.getLoginname());
        flowWorkServiceImpl.getTaskService().addComment(taskId, processInstanceId, message);
        return true;
    }
}

