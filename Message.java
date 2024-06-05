package main;
import java.io.Serializable;

class Message implements Serializable {
    private String methodName;
    private Object[] parameters;
    private String cityName;

    public Message(String methodName, Object... parameters) {
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }
    
    public String getcityName() {
    	return cityName;
    }
    public void setCityName(String cityName) {
    	this.cityName = cityName;
    }
}
