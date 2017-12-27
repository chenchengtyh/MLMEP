package com.nti.mlmep.domain;

import java.io.Serializable;
import java.util.Date;

public class SearchMobileEva implements Serializable{
	
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
		private String otherAssess = "";
		private Date assessDatetime;
		private String remark;
		
		public void setevaId(String evaId) {
			this.evaId = evaId;
		}
		
		public String getevaId() {
			return evaId;
		}
		
		public void setotherAssess(String otherAssess) {
			this.otherAssess = otherAssess;
		}
		
		public String getotherAssess() {
			return otherAssess;
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
