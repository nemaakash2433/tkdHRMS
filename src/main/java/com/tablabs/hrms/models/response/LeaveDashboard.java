package com.tablabs.hrms.models.response;


public class LeaveDashboard {
	private Long onsite;

	private Long remote;

	private Long totalEmpCount;

	private Long leavesCount;

	public Long getOnsite() {
		return onsite;
	}

	public void setOnsite(Long onsite) {
		this.onsite = onsite;
	}

	public Long getRemote() {
		return remote;
	}

	public void setRemote(Long remote) {
		this.remote = remote;
	}

	public Long getTotalEmpCount() {
		return totalEmpCount;
	}

	public void setTotalEmpCount(Long totalEmpCount) {
		this.totalEmpCount = totalEmpCount;
	}

	public Long getLeavesCount() {
		return leavesCount;
	}

	public void setLeavesCount(Long leavesCount) {
		this.leavesCount = leavesCount;
	}

}

