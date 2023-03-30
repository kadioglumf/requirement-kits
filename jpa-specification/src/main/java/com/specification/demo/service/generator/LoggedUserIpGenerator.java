package com.specification.demo.service.generator;

import com.specification.demo.model.EIpType;
import com.specification.demo.utils.UserDeviceDetailsUtils;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

public class LoggedUserIpGenerator implements ValueGenerator<String> {
    @Override
    public String generateValue(Session session, Object o) {
        return UserDeviceDetailsUtils.getIpAddr(EIpType.CLIENT);
    }
}
