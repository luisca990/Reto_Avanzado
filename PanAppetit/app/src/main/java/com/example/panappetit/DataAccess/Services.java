package com.example.panappetit.DataAccess;

public enum Services implements IServiceParameters {
    LOGIN("",Methods.POST),
    REGISTER_USER("", IServiceParameters.Methods.POST);

    private final String url;
    private final IServiceParameters.Methods method;

    Services(String url, IServiceParameters.Methods method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public IServiceParameters.Methods getMethods() {
        return method;
    }
}
