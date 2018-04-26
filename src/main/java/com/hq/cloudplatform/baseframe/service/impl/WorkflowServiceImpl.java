/**
 *
 */
package com.hq.cloudplatform.baseframe.service.impl;

import com.hq.cloudplatform.baseframe.entity.WorkflowProcessInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import com.hq.cloudplatform.baseframe.service.IWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WANG YONG
 * CREATE BY 2017年7月17日
 */

@Service
@Slf4j
public class WorkflowServiceImpl implements IWorkflowService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 系统启动时在InstantiationTracingBeanPostProcessor中进行初始化
     *
     * @see com.hq.cloudplatform.baseframe.sys.InstantiationTracingBeanPostProcessor
     */
    public static Map<String, WorkflowProcessInfo> workflowProcessInfoMap;

    @Override
    public String startProcess(String userId, String processKey, String businessKey, String comment, Map<String, Object> variables) {
        //设置当前人，启动流程时会将该UserId记录为流程发起人
        Authentication.setAuthenticatedUserId(userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
        taskService.addComment(null, processInstance.getId(), comment);

        return processInstance.getId();
    }

    @Override
    public HistoricTaskInstance getHistoricTaskById(String taskId) {
        return historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .taskId(taskId)
                .singleResult();
    }

    @Override
    public List<Task> findTodoTaskListByPage(Page page, String userId) throws ServiceException {
        page.setTotal((int) getTodoTaskListCount(userId));

        return taskService.createTaskQuery()
                .includeProcessVariables()
                .taskAssignee(userId)
                .orderByTaskCreateTime().desc()
                .listPage(page.getStartRowNum(), page.getPageSize());
    }

    @Override
    public long getTodoTaskListCount(String userId) {
        return taskService.createTaskQuery()
                .taskAssignee(userId)
                .count();
    }

    @Override
    public List<HistoricTaskInstance> findDoneTaskListByPage(Page page, String userId) throws ServiceException {
        page.setTotal((int) getDoneTaskListCount(userId));

        return historyService
                //创建历史任务查询
                .createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                //指定办理人
                .taskAssignee(userId)
                //状态为已完成的
                .finished()
                .orderByHistoricTaskInstanceEndTime().desc()
                .listPage(page.getStartRowNum(), page.getPageSize());
    }

    @Override
    public long getDoneTaskListCount(String userId) {
        return historyService
                //创建历史任务查询
                .createHistoricTaskInstanceQuery()
                //指定办理人
                .taskAssignee(userId)
                //状态为已完成的
                .finished()
                .count();
    }

    @Override
    public boolean completeTask(String operatorId, String taskId, String comment, Map<String, Object> variables) {
        Authentication.setAuthenticatedUserId(operatorId);
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        if (null != task) {
            String processInstanceId = task.getProcessInstanceId();

            if (variables == null) {
                variables = new HashMap<>();
            }

            //使工作流程中配置的skipExpression生效
            variables.put("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

            taskService.addComment(taskId, processInstanceId, comment);
            taskService.complete(taskId, variables);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getBusinessKey(String taskId) throws ServiceException {
        String processInstanceId;
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();

        if (null == historicTaskInstance) {
            throw new ServiceException("该任务不存在！Task Id:" + taskId + " .");
        } else {
            processInstanceId = historicTaskInstance.getProcessInstanceId();
        }

        if (null == processInstanceId) {
            throw new ServiceException("该任务所属的流程实例不存在！Task Id:" + taskId + " .");
        }

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (null == processInstance) {
            throw new ServiceException("该任务所属的流程实例不存在！Task Id:" + taskId + " , Process Instance Id:" + processInstanceId + " .");
        }

        return processInstance.getBusinessKey();
    }

    @Override
    public List<Comment> getCommentListByTaskId(String taskId) throws ServiceException {
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId)
                .singleResult();

        if (null == task) {
            throw new ServiceException("该任务不存在！Task Id:" + taskId + " .");
        }

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();

        if (null == processInstance) {
            throw new ServiceException("该任务所属的流程实例不存在！Task Id:" + taskId + " .");
        }

        String processInstanceId = processInstance.getId();

        return taskService.getProcessInstanceComments(processInstanceId);
    }

    @Override
    public WorkflowProcessInfo getWorkflowProcessInfo(String type) {
        return workflowProcessInfoMap.get(type);
    }

}
