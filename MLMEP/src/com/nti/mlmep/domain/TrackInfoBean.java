package com.nti.mlmep.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nti.mlmep.util.StringUtil;

public class TrackInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <ALMEvent> <EventId xsi:nil="true" /> <EndId xsi:nil="true" /> <State
	 * xsi:nil="true" /> <EventTypeId xsi:nil="true" /> <Description
	 * xsi:nil="true" /> <OccurOn xsi:nil="true" /> <HandleResult xsi:nil="true"
	 * /> <HandledBy xsi:nil="true" /> <HandledOn xsi:nil="true" /> <FilePath
	 * xsi:nil="true" /> <PositionId xsi:nil="true" />
	 */
	private String orderId;// 订单id
	private String orderNumber;// 订单号
	private String contractNumber;// 合同号，根据合同号，关联运输管理表
	private String plateNumber;// 车牌号
	private String transportCode;// 运输单号
	private String transportUnit;//运输单位(1=自有 2=外协)
	private String orderStatus;// 物流状态（查询最新的 运输状态）
	private String orderStatusText;// 物流状态（查询最新的 运输状态）
	
	private String ncCreateDate;//制单时间
	
	// private String orderType;//订单标识
	private String customName;// 收货方
	private String repertoryName;// 发货方
	private String plannedArrivedDate;// 预计到货时间
	// private String actualArrivedDate;//实际到货时间
	private String driverName;// 实际到货时间
	private String tel;// 实际到货时间
	public List<OrderDetails> orderDetails;
	public List<OrderStatuss> orderStatuss;
	
	private String complaint_flag; //投诉标志位
	private String eva_flag;//评价标志位
    	
	private List<MobileComplaint> mobileComplaintList; //投诉信息
	private List<MobileEva> mobileEva; //评价信息
	
	public TrackPosition trackPosition;//订单在途的情况，记录当时的到货地点
	
	
	public static class MobileComplaint implements Serializable{

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
		
		//投诉人信息
		private String complaintUserId;
		private String complaintUserName;

		private String feedback;// 服务方反馈
		private Date feedbackDate;// 反馈日期
		private String feedbackUserId;// 反馈人
		private String feedbackUserName;// 反馈人
		private String isAccept;//是否受理
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
		public String getContractNumber() {
			return contractNumber;
		}

		public void setContractNumber(String contractNumber) {
			this.contractNumber = contractNumber;
		}
		
		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getNoIntimeAssess() {
			return noIntimeAssess;
		}

		public void setNoIntimeAssess(String noIntimeAssess) {
			this.noIntimeAssess = noIntimeAssess;
		}
		public String getNoAnswerPhone() {
			return noAnswerPhone;
		}

		public void setNoAnswerPhone(String noAnswerPhone) {
			this.noAnswerPhone = noAnswerPhone;
		}
		public String getErrorPhoneNumber() {
			return errorPhoneNumber;
		}

		public void setErrorPhoneNumber(String errorPhoneNumber) {
			this.errorPhoneNumber = errorPhoneNumber;
		}
		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Date getComplaintDate() {
			return complaintDate;
		}

		public void setComplaintDate(Date complaintDate) {
			this.complaintDate = complaintDate;
		}
		public String getComplaintUserId() {
			return complaintUserId;
		}

		public void setComplaintUserId(String complaintUserId) {
			this.complaintUserId = complaintUserId;
		}
		public String getComplaintUserName() {
			return complaintUserName;
		}

		public void setComplaintUserName(String complaintUserName) {
			this.complaintUserName = complaintUserName;
		}
		public String getFeedback() {
			return feedback;
		}

		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
		public Date getFeedbackDate() {
			return feedbackDate;
		}

		public void setFeedbackDate(Date feedbackDate) {
			this.feedbackDate = feedbackDate;
		}
		public String getFeedbackUserId() {
			return feedbackUserId;
		}

		public void setFeedbackUserId(String feedbackUserId) {
			this.feedbackUserId = feedbackUserId;
		}
		public String getFeedbackUserName() {
			return feedbackUserName;
		}

		public void setFeedbackUserName(String feedbackUserName) {
			this.feedbackUserName = feedbackUserName;
		}
		public String getIsAccept() {
			return isAccept;
		}

		public void setIsAccept(String isAccept) {
			this.isAccept = isAccept;
		}
		
		
		
		
	}
	
	public static class MobileEva implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String evaId;
		private String contractNumber;
		private String orderId;
		private String isHighOpinion;
		private String totalEvaAssess;
		private String totalEvaScore;
		private String intimeAssess;//
		private String intimeScore;
		private String intactAssess;
		private String intactScore;
		private String serveAttitudeAssess;
		private String serveAttitudeScore;
		private String assessPersonId;
		private String assessPersonName;
		private Date assessDatetime;
		private String remark;
		
		public void setevaId(String evaId) {
			this.evaId = evaId;
		}
		
		public String getevaId() {
			return evaId;
		}

		public void setcontractNumber(String contractNumber) {
			this.contractNumber = contractNumber;
		}
		public String getcontractNumber() {
			return contractNumber;
		}

		public void setorderId(String orderId) {
			this.orderId = orderId;
		}
		public String getorderId() {
			return orderId;
		}

		public void setisHighOpinion(String isHighOpinion) {
			this.isHighOpinion = isHighOpinion;
		}
		public String getisHighOpinion() {
			return isHighOpinion;
		}

		public void settotalEvaAssess(String totalEvaAssess) {
			this.totalEvaAssess = totalEvaAssess;
		}
		public String gettotalEvaAssess() {
			return totalEvaAssess;
		}

		public void settotalEvaScore(String totalEvaScore) {
			this.totalEvaScore = totalEvaScore;
		}
		public String gettotalEvaScore() {
			return totalEvaScore;
		}

		public void setintimeAssess(String intimeAssess) {
			this.intimeAssess = intimeAssess;
		}
		public String getintimeAssess() {
			return intimeAssess;
		}

		public void setintimeScore(String intimeScore) {
			this.intimeScore = intimeScore;
		}
		public String getintimeScore() {
			return intimeScore;
		}

		public void setintactAssess(String intactAssess) {
			this.intactAssess = intactAssess;
		}
		public String getintactAssess() {
			return intactAssess;
		}

		public void setintactScore(String intactScore) {
			this.intactScore = intactScore;
		}
		public String getintactScore() {
			return intactScore;
		}

		public void setserveAttitudeAssess(String serveAttitudeAssess) {
			this.serveAttitudeAssess = serveAttitudeAssess;
		}
		public String getserveAttitudeAssess() {
			return serveAttitudeAssess;
		}

		public void setserveAttitudeScore(String serveAttitudeScore) {
			this.serveAttitudeScore = serveAttitudeScore;
		}
		public String getserveAttitudeScore() {
			return serveAttitudeScore;
		}

		public void setassessPersonId(String assessPersonId) {
			this.assessPersonId = assessPersonId;
		}
		public String getassessPersonId() {
			return assessPersonId;
		}

		public void setassessDatetime(Date assessDatetime) {
			this.assessDatetime = assessDatetime;
		}
		public Date getassessDatetime() {
			return assessDatetime;
		}

		public void setassessPersonName(String assessPersonName) {
			this.assessPersonName = assessPersonName;
		}
		public String getassessPersonName() {
			return assessPersonName;
		}

		public void setremark(String remark) {
			this.remark = remark;
		}
		public String getremark() {
			return remark;
		}
	
	}
	
	

	public static class OrderDetails implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String id;
		public String orderId;
		public String versionName;
		public String versionCode;
		public String productName;
		public String pMeteringUnit;
		public String pNumber;
		public String lMeteringUnit;
		public String lNumber;
		public String taxMoney;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getVersionName() {
			return versionName;
		}

		public void setVersionName(String versionName) {
			this.versionName = versionName;
		}

		public String getVersionCode() {
			return versionCode;
		}

		public void setVersionCode(String versionCode) {
			this.versionCode = versionCode;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getpMeteringUnit() {
			return pMeteringUnit;
		}

		public void setpMeteringUnit(String pMeteringUnit) {
			this.pMeteringUnit = pMeteringUnit;
		}

		public String getpNumber() {
			return pNumber;
		}

		public void setpNumber(String pNumber) {
			this.pNumber = pNumber;
		}

		public String getlMeteringUnit() {
			return lMeteringUnit;
		}

		public void setlMeteringUnit(String lMeteringUnit) {
			this.lMeteringUnit = lMeteringUnit;
		}

		public String getlNumber() {
			return lNumber;
		}

		public void setlNumber(String lNumber) {
			this.lNumber = lNumber;
		}

		public String getTaxMoney() {
			return taxMoney;
		}

		public void setTaxMoney(String taxMoney) {
			this.taxMoney = taxMoney;
		}

	}

	public static class OrderStatuss implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String orderStatusId;
		public String recordDate;
		public String orderStatus;
		public String orderStatusText;

		public String getOrderStatusText() {
			return orderStatusText;
		}

		public void setOrderStatusText(String orderStatusText) {
			this.orderStatusText = orderStatusText;
		}

		public String getOrderStatusId() {
			return orderStatusId;
		}

		public void setOrderStatusId(String orderStatusId) {
			this.orderStatusId = orderStatusId;
		}

		public String getRecordDate() {
			return recordDate;
		}

		public void setRecordDate(String recordDate) {
			this.recordDate = recordDate;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

	}

	
	public String getcomplaint_flag() {
		return complaint_flag;
	}

	public void setcomplaint_flag(String complaint_flag) {
		this.complaint_flag = complaint_flag;
	}
	public String geteva_flag() {
		return eva_flag;
	}

	public void seteva_flag(String eva_flag) {
		this.eva_flag = eva_flag;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getTransportCode() {
		return transportCode;
	}

	public void setTransportCode(String transportCode) {
		this.transportCode = transportCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getRepertoryName() {
		return repertoryName;
	}

	public void setRepertoryName(String repertoryName) {
		this.repertoryName = repertoryName;
	}

	public String getPlannedArrivedDate() {
		return plannedArrivedDate;
	}

	public void setPlannedArrivedDate(String plannedArrivedDate) {
		this.plannedArrivedDate = plannedArrivedDate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOrderStatusText() {
		return orderStatusText;
	}

	public void setOrderStatusText(String orderStatusText) {
		this.orderStatusText = orderStatusText;
	}
	
	public List<MobileComplaint> getMobileComplaint() {
		return mobileComplaintList;
	}

	public void setMobileComplaint(List<MobileComplaint> mobileComplaintList) {
		this.mobileComplaintList = mobileComplaintList;
	}

	public List<MobileEva> getMobileEva() {
		return mobileEva;
	}

	public void setMobileEva(List<MobileEva> mobileEva) {
		this.mobileEva = mobileEva;
	}

	public List<OrderStatuss> getOrderStatuss() {
		return orderStatuss;
	}

	public void setOrderStatuss(List<OrderStatuss> orderStatuss) {
		this.orderStatuss = orderStatuss;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public TrackPosition getTrackPosition() {
		return trackPosition;
	}

	public void setTrackPosition(TrackPosition trackPosition) {
		this.trackPosition = trackPosition;
	}
	
	public void setNcCreateDate(String ncCreateDate) {
		this.ncCreateDate = ncCreateDate;
	}
	
	public String getNcCreateDate(){
		return ncCreateDate;
	}
	
	public void setTransportUnit(String transportUnit){
		this.transportUnit = transportUnit;
	}
	public String getTransportUnit(){
		return transportUnit;
	}

	@Override
	public boolean equals(Object o) {
		TrackInfoBean tb = (TrackInfoBean) o;
		if (tb != null && !StringUtil.isEmpty(tb.getOrderId()) && this != null
				&& StringUtil.isEmpty(this.getOrderId())
				&& tb.getOrderId().equals(this.getOrderId())) {
			return true;
		}else{
			return false;
		}
	}

}
