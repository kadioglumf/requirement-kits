package com.specification.demo.service.generator;

import com.specification.demo.utils.UserDeviceDetailsUtils;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

public class OriginGenerator implements ValueGenerator<String> {
  @Override
  public String generateValue(Session session, Object o) {
    return UserDeviceDetailsUtils.getOrigin();
  }
}
