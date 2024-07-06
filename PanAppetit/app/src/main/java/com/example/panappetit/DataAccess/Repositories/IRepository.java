package com.example.panappetit.DataAccess.Repositories;

import com.example.panappetit.DataAccess.Services;
import com.example.panappetit.Models.MessageResponse;
import com.google.protobuf.Any;

public interface IRepository {
    void onSuccessResponse(Object objectResponse, Services services);
    void onFailedResponse(MessageResponse response, Services services);
}
