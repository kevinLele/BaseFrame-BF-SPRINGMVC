/**
 *
 */
package com.hq.cloudplatform.baseframe.service;

import com.hq.cloudplatform.baseframe.entity.WorkflowProcessInfo;
import com.hq.cloudplatform.baseframe.exception.ServiceException;
import com.hq.cloudplatform.baseframe.restful.view.Page;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * 工作流相关服务接口
 *
 * @author Kevin
 */
public interface IWorkflowService {

    /**
     * 启动工作流
     *
     * @param userId      流程发起人的ID
     * @param processKey  流程的唯一标识
     * @param businessKey 业务信息的ID
     * @param comment     申请原因
     * @param variables   工作流程中需要使用到的变量信息
     * @return
     */
    String startProcess(String userId, String processKey, String businessKey,
                        String comment, Map<String, Object> variables);

    /**
     * 根据任务ID从历史任务记录表中获取任务信息
     *
     * @param taskId
     * @return
     */
    HistoricTaskInstance getHistoricTaskById(String taskId);

    /**
     * 查询待办任务
     *
     * @param page
     * @param userId
     * @return
     * @throws ServiceException
     */
    List<Task> findTodoTaskListByPage(Page page, String userId) throws ServiceException;

    /**
     * 查询待办任务的总数
     *
     * @param userId
     * @return
     */
    long getTodoTaskListCount(String userId);

    /**
     * 查询已办任务
     *
     * @param page
     * @param userId
     * @return
     * @throws ServiceException
     */
    List<HistoricTaskInstance> findDoneTaskListByPage(Page page, String userId) throws ServiceException;

    /**
     * 查询已办任务的总数
     *
     * @param userId
     * @return
     */
    long getDoneTaskListCount(String userId);

    /**
     * 完成工作流中的任务
     *
     * @param opearatorId 操作员的ID
     * @param taskId      任务ID
     * @param comment     批注信息（即审批意见）
     * @param variables   工作流程中需要使用到的变量信息
     */
    boolean completeTask(String opearatorId, String taskId, String comment, Map<String, Object> variables);

    /**
     * 获取业务ID
     *
     * @param taskId
     * @return
     */
    String getBusinessKey(String taskId) throws ServiceException;

    /**
     * 通过任务ID获取该任务所在的工作流程的所有批注信息
     *
     * @param taskId
     * @return
     */
    List<Comment> getCommentListByTaskId(String taskId) throws ServiceException;

    WorkflowProcessInfo getWorkflowProcessInfo(String type);
}