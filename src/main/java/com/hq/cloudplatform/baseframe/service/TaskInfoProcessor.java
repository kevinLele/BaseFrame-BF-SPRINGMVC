package com.hq.cloudplatform.baseframe.service;

import com.hq.cloudplatform.baseframe.exception.ServiceException;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;

/**
 * Administrator
 */
public interface TaskInfoProcessor {

    Object todoTaskInfo(Task task) throws ServiceException;

    Object doneTaskInfo(HistoricTaskInstance historicTaskInstance) throws ServiceException;

    Object taskDetailInfo(String taskId) throws ServiceException;
}
