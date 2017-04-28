package com.nti.mlmep.domain;

import java.io.Serializable;
import java.util.Date;

public class TrackInfo implements Serializable {
	private static final long serialVersionUID = 9017023405236445081L;
	/**
	 * <ALMEvent> <EventId xsi:nil="true" /> <EndId xsi:nil="true" /> <State
	 * xsi:nil="true" /> <EventTypeId xsi:nil="true" /> <Description
	 * xsi:nil="true" /> <OccurOn xsi:nil="true" /> <HandleResult xsi:nil="true"
	 * /> <HandledBy xsi:nil="true" /> <HandledOn xsi:nil="true" /> <FilePath
	 * xsi:nil="true" /> <PositionId xsi:nil="true" />
	 */
	private String orderId;//订单id
	private String orderNumber;//订单号
	private String contractNumber;//合同号，根据合同号，关联运输管理表
	private String plateNumber;//车牌号
	private String transportCode;//运输单号
	private String transportUnit;//运输单位(1=自有 2=外协)
	private String orderStatus;//物流状态（查询最新的 运输状态）
	private String orderType;//订单标识
	private String customName;//收货方
	private String repertoryName;//发货方
	private String plannedArrivedDate;//预计到货时间
	private String actualArrivedDate;//实际到货时间
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
	public String getTransportUnit() {
		return transportUnit;
	}
	public void setTransportUnit(String transportUnit) {
		this.transportUnit = transportUnit;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getActualArrivedDate() {
		return actualArrivedDate;
	}
	public void setActualArrivedDate(String actualArrivedDate) {
		this.actualArrivedDate = actualArrivedDate;
	}
	

}
