package com.example.panappetit.DataAccess;

public interface IServiceParameters {
    enum Methods {
        GET,
        POST,
        PUT,
        DELETE
    }

    String getURL();

    Methods getMethods();
}
