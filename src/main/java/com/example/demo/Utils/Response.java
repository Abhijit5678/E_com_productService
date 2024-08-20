package com.example.demo.Utils;


public class Response<T> {

	private String resultCode = "0";

	private String status;

	private String message;

	private T data;

	private Integer currentPage;

	private Integer total;

	private long totalDataCounts;

	private String totalCount;

	public Response() {
	}

	public Response(String status, String message, T data)

	{

		this.status = status;

		this.message = message;

		this.data = data;

	}

	public String getStatus() {

		return status;

	}

	public void setStatus(String status) {

		this.status = status;

	}

	public String getMessage() {

		return message;

	}

	public void setMessage(String message) {

		this.message = message;

	}

	public T getData() {

		return data;

	}

	@SuppressWarnings("unchecked")

	public void setData(Object status2) {

		this.data = (T) status2;

	}

	public String getResultCode() {

		return resultCode;

	}

	public void setResultCode(String resultCode) {

		this.resultCode = resultCode;

	}

	public Integer getCurrentPage() {

		return currentPage;

	}

	public void setCurrentPage(Integer currentPage) {

		this.currentPage = currentPage;

	}

	public Integer getTotal() {

		return total;

	}

	public void setTotal(Integer total) {

		this.total = total;

	}

	public long getTotalDataCounts() {

		return totalDataCounts;

	}

	public void setTotalDataCounts(long totalDataCounts) {

		this.totalDataCounts = totalDataCounts;

	}

	public String getTotalCount() {

		return totalCount;

	}

	public void setTotalCount(String totalCount) {

		this.totalCount = totalCount;

	}

}