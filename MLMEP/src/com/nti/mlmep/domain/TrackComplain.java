package com.nti.mlmep.domain;

import java.io.Serializable;
import java.util.Date;

public class TrackComplain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;// ����
	private String contractNumber;// ��ͬ��
	private String orderId;// ����ID

	private String noIntimeAssess;// δ��ʱ����
	private String noAnswerPhone;// δ�ӵ绰
	private String errorPhoneNumber;// ��ʻԱ��ϵ��ʽ
	private String remark;
	private Date complaintDate;// Ͷ������

	private String feedback;// ���񷽷���
	private Date feedbackDate;// ��������
	private String feedbackUserId;// ������
	private String isAccept;
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setcontractNumber(String contractNumber){
		this.contractNumber = contractNumber;
	}
	
	public String getcontractNumber(){
		return this.contractNumber;
	}
	
	public void setorderId(String orderId){
		this.orderId = orderId;
	}
	
	public String getorderId(){
		return this.orderId;
	}
	
	public void setnoIntimeAssess(String noIntimeAssess){
		this.noIntimeAssess = noIntimeAssess;
	}
	
	public String getnoIntimeAssess(){
		return this.noIntimeAssess;
	}
	
	public void setnoAnswerPhone(String noAnswerPhone){
		this.noAnswerPhone = noAnswerPhone;
	}
	
	public String getnoAnswerPhone(){
		return this.noAnswerPhone;
	}
	
	public void seterrorPhoneNumber(String errorPhoneNumber){
		this.errorPhoneNumber = errorPhoneNumber;
	}
	
	public String geterrorPhoneNumber(){
		return this.errorPhoneNumber;
	}
	
	public void setremark(String remark){
		this.remark = remark;
	}
	
	public String getremark(){
		return this.remark;
	}
	
	public void setcomplaintDate(Date complaintDate){
		this.complaintDate = complaintDate;
	}
	
	public Date getcomplaintDate(){
		return this.complaintDate;
	}
	
	public void setfeedbackDate(Date feedbackDate){
		this.feedbackDate = feedbackDate;
	}
	
	public Date getfeedbackDate(){
		return this.feedbackDate;
	}
	
	public void setfeedback(String feedback){
		this.feedback = feedback;
	}
	
	public String getfeedback(){
		return this.feedback;
	}
	
	public void setfeedbackUserId(String feedbackUserId){
		this.feedbackUserId = feedbackUserId;
	}
	
	public String getfeedbackUserId(){
		return this.feedbackUserId;
	}
	
	public void setisAccept(String isAccept){
		this.isAccept = isAccept;
	}
	
	public String getisAccept(){
		return this.id;
	}

}
