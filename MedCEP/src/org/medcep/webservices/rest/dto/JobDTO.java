package org.medcep.webservices.rest.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JobDTO
{
    @XmlElement(name = "sched_name")
    private String schedulerName;

    @XmlElement(name = "job_name")
    private String jobName;

    @XmlElement(name = "job_group")
    private String jobGroup;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "job_class_name")
    private String jobClassName;

    @XmlElement(name = "is_durable")
    private Boolean isDurable;

    @XmlElement(name = "is_nonconcurrent")
    private Boolean isNonConcurrent;

    @XmlElement(name = "is_update_data")
    private Boolean isUpdateData;

    @XmlElement(name = "requests_recovery")
    private Boolean requestsRecovery;

    public String getSchedulerName()
    {
	return schedulerName;
    }

    public void setSchedulerName(String schedulerName)
    {
	this.schedulerName = schedulerName;
    }

    public String getJobName()
    {
	return jobName;
    }

    public void setJobName(String jobName)
    {
	this.jobName = jobName;
    }

    public String getJobGroup()
    {
	return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
	this.jobGroup = jobGroup;
    }

    public String getDescription()
    {
	return description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public String getJobClassName()
    {
	return jobClassName;
    }

    public void setJobClassName(String jobClassName)
    {
	this.jobClassName = jobClassName;
    }

    public Boolean getIsDurable()
    {
	return isDurable;
    }

    public void setIsDurable(Boolean isDurable)
    {
	this.isDurable = isDurable;
    }

    public Boolean getIsNonConcurrent()
    {
	return isNonConcurrent;
    }

    public void setIsNonConcurrent(Boolean isNonConcurrent)
    {
	this.isNonConcurrent = isNonConcurrent;
    }

    public Boolean getIsUpdateData()
    {
	return isUpdateData;
    }

    public void setIsUpdateData(Boolean isUpdateData)
    {
	this.isUpdateData = isUpdateData;
    }

    public Boolean getRequestsRecovery()
    {
	return requestsRecovery;
    }

    public void setRequestsRecovery(Boolean requestsRecovery)
    {
	this.requestsRecovery = requestsRecovery;
    }

}
