package maybank.project.dto;

import maybank.project.exception.error.GenericErrorCode;

public class ResponseMessage {
    public static final String OK = "OK";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    private GenericErrorCode code;
    private String status;
    private String description;
    private Object data;
    private Object[] dataArr;

    public Object[] getDataArr() {
		return dataArr;
	}

	public void setDataArr(Object[] dataArr) {
		this.dataArr = dataArr;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public GenericErrorCode getCode() {
		return code;
	}

	public void setCode(GenericErrorCode code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResponseMessage [code=" + code + ", status=" + status + ", description=" + description + ", data="
				+ data + "]";
	}
}
