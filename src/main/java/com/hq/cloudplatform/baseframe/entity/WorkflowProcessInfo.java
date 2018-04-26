package com.hq.cloudplatform.baseframe.entity;

import com.hq.cloudplatform.baseframe.service.ProcessStartInitiator;

/**
 * Administrator
 */
public class WorkflowProcessInfo {

    private String processInstanceKey;

    private ProcessStartInitiator processStartInitiator;

    public WorkflowProcessInfo(String processInstanceKey, ProcessStartInitiator processStartInitiator) {
        this.processInstanceKey = processInstanceKey;
        this.processStartInitiator = processStartInitiator;
    }

    public String getProcessInstanceKey() {
        return processInstanceKey;
    }

    public void setProcessInstanceKey(String processInstanceKey) {
        this.processInstanceKey = processInstanceKey;
    }

    public ProcessStartInitiator getProcessStartInitiator() {
        return processStartInitiator;
    }

    public void setProcessStartInitiator(ProcessStartInitiator processStartInitiator) {
        this.processStartInitiator = processStartInitiator;
    }
}
