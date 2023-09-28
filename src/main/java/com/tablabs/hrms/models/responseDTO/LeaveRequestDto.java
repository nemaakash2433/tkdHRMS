package com.tablabs.hrms.models.responseDTO;

import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDto {

	private List<LeaveRequestsDto> leaveRequest = new ArrayList<>();

	public LeaveRequestDto() {
		super();
	}

	public LeaveRequestDto(List<LeaveRequestsDto> leaveRequest) {
		super();
		this.leaveRequest = leaveRequest;
	}

	public List<LeaveRequestsDto> getLeaveRequest() {
		return leaveRequest;
	}

	public void setLeaveRequest(List<LeaveRequestsDto> leaveRequest) {
		this.leaveRequest = leaveRequest;
	}

}
