package com.nti.mlmep.domain;

import java.io.Serializable;

public class TrackMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sendTime;//������Ϣʱ��
	private String repertoryName;//������
	private String customName;//������
	private String contractNumber;//��ͬ��
	private String messageType;//��Ϣ����
	
	public void setsendTime(String sendTime){
		this.sendTime = sendTime;
	}
	public String getsendTime(){
		return sendTime;
	}
	
	public void setrepertoryName(String repertoryName){
		this.repertoryName = repertoryName;
	}
	public String getrepertoryName(){
		return repertoryName;
	}
	public void setcustomName(String customName){
		this.customName = customName;
	}
	public String getcustomName(){
		return customName;
	}
	public void setcontractNumber(String contractNumber){
		this.contractNumber = contractNumber;
	}
	public String getcontractNumber(){
		return contractNumber;
	}
	public void setmessageType(String messageType){
		this.messageType = messageType;
	}
	public String getmessageType(){
		return messageType;
	}

}
