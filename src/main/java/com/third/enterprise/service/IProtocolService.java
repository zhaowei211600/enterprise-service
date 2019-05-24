package com.third.enterprise.service;

import com.third.enterprise.bean.ProductProtocol;

public interface IProtocolService {

    ProductProtocol selectByProductId(Integer productId);

    boolean saveProtocol(ProductProtocol productProtocol);
}
