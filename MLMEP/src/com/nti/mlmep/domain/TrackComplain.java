package com.nti.mlmep.domain;

import java.io.Serializable;
import java.util.Date;

public class TrackComplain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;// 主键
	private String contractNumber;// 合同号
	private String orderId;// 订单ID

	private String noIntimeAssess;// 未及时到货
	private String noAnswerPhone;// 未接电话
	private String errorPhoneNumber;// 驾驶员联系方式
	private String remark;
	private Date complaintDate;// 投诉日期

	private String feedback;// 服务方反馈
	private Date feedbackDate;// 反馈日期
	private String feedbackUserId;// 反馈人
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
