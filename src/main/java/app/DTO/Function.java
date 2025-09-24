package app.DTO;

public class Function {

	private int functionId;
	private String functionName;
    private String icon;
	private int status;

	// Constructors
	public Function() {
	}

	public Function(int functionId, String functionName, String icon, int status) {
		this.functionId = functionId;
		this.functionName = functionName;
		this.icon = icon;
		this.status = status;
	}

    public Function(int functionId, String functionName) {
        this.functionId = functionId;
        this.functionName = functionName;
    }

	// Getters and Setters
	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
