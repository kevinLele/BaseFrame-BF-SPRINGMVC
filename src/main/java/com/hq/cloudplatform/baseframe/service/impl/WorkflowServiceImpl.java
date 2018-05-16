/**
 *
 */
package com.hq.cloudplatform.baseframe.service.impl;

import com.hq.cloudplatform.baseframe.entity.WorkflowProcessInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import com.hq.cloudplatform.baseframe.service.IWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
    public String getProcessDefinitionId(String processInstanceId) throws ServiceException {
        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getProcessDefinitionId();
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

    @Override
    public void processTracking(String processDefinitionId, String executionId,
                                OutputStream out) throws ServiceException {
        // 当前活动节点、活动线
        List<String> activeActivityIds = new ArrayList<>();
        List<String> highLightedFlows;

        // 如果流程已经结束，则得到结束节点
        if (this.isFinished(executionId)) {
            activeActivityIds.add(historyService
                    .createHistoricActivityInstanceQuery()
                    .executionId(executionId).activityType("endEvent")
                    .singleResult().getActivityId());
        }
        // 如果流程没有结束，则取当前活动节点
        else {
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        }

        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
        List<HistoricActivityInstance> historicActivityInstances = historyService
                .createHistoricActivityInstanceQuery().executionId(executionId)
                .orderByHistoricActivityInstanceStartTime().asc().list();
        // 计算活动线
        highLightedFlows = this
                .getHighLightedFlows(
                        (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                                .getDeployedProcessDefinition(processDefinitionId),
                        historicActivityInstances);

        //绘制图形
        if (null != activeActivityIds) {
            InputStream imageStream = null;
            try {
                // 根据流程定义ID获得BpmnModel
                BpmnModel bpmnModel = repositoryService
                        .getBpmnModel(processDefinitionId);

                // 输出资源内容到相应对象
                imageStream = new DefaultProcessDiagramGenerator()
                        .generateDiagram(bpmnModel,
                                "png",
                                activeActivityIds,
                                highLightedFlows,
                                processEngineConfiguration.getActivityFontName(),
                                processEngineConfiguration.getLabelFontName(),
                                processEngineConfiguration.getAnnotationFontName(),
                                processEngineConfiguration.getClassLoader(),
                                1.0);
                IOUtils.copy(imageStream, out);
            } catch (IOException e) {
                throw new ServiceException(e);
            } finally {
                IOUtils.closeQuietly(imageStream);
            }
        }
    }

    /**
     * 判断流程是否已经结束
     *
     * @param processInstanceId 流程实例ID
     * @return
     */
    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery()
                .finished()
                .processInstanceId(processInstanceId)
                .count() > 0;
    }

    /**
     * 获得高亮线
     *
     * @param processDefinitionEntity   流程定义实体
     * @param historicActivityInstances 历史活动实体
     * @return 线ID集合
     */
    public List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();

        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size(); i++) {
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            // 得 到节点定义的详细信息
                            .getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();

            if ((i + 1) >= historicActivityInstances.size()) {
                break;
            }

            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            // 将后面第一个节点放在时间相同节点的集合里
                            .getActivityId());
            sameStartTimeNodes.add(sameActivityImpl1);

            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);

                // 如果第一个节点和第二个节点开始时间相同保存
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }

            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();

            // 对所有的线进行遍历
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();

                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }

        return highFlows;
    }
}
