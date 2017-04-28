package com.nti.mlmep.domain;

import java.io.Serializable;

public class TrackMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sendTime;//发送消息时间
	private String repertoryName;//发货方
	private String customName;//到货方
	private String contractNumber;//合同号
	private String messageType;//消息类型
	
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
