package com.mtncloudservices.ws.products.service;

import com.mtncloudservices.ws.products.rest.CreateProductRestModel;

public interface ProductService {

    String createProduct(CreateProductRestModel productRestModel);
}
