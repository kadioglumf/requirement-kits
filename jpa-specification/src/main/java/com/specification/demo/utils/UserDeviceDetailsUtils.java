package com.specification.demo.utils;

import com.specification.demo.model.EIpType;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserDeviceDetailsUtils {

  private static final String UNKNOWN_IP_ADDRESS = "unknown";
  private static final String USER_AGENT = "user-agent";
  private static final String ORIGIN = "origin";
  private static final String[] IP_HEADER_CANDIDATES = {
    "X-Forwarded-For",
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_X_FORWARDED_FOR",
    "HTTP_X_FORWARDED",
    "HTTP_X_CLUSTER_CLIENT_IP",
    "HTTP_CLIENT_IP",
    "HTTP_FORWARDED_FOR",
    "HTTP_FORWARDED",
    "HTTP_VIA",
    "REMOTE_ADDR"
  };

  private static final Map<EIpType, Integer> ipTypes =
      Map.of(
          EIpType.CLIENT, 0,
          EIpType.ORIGIN, 1);

  public static String getIpAddr(EIpType ipType) {
    String ipAddress = "";
    if (RequestContextHolder.getRequestAttributes() == null) {
      return "Unknown User";
    }

    HttpServletRequest request = getHttpServletRequest();

    for (String header : IP_HEADER_CANDIDATES) {
      ipAddress = request.getHeader(header);
      if (ipAddress != null
          && ipAddress.length() != 0
          && !UNKNOWN_IP_ADDRESS.equalsIgnoreCase(ipAddress)) {
        break;
      }
    }
    if (ipAddress == null
        || ipAddress.length() == 0
        || UNKNOWN_IP_ADDRESS.equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
    }
    if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
      // added for localhost domain
      ipAddress = "127.0.0.1";
    }
    if (ipTypes.get(ipType) != null && ipAddress.contains(",")) {
      return ipAddress.split(",")[ipTypes.get(ipType)].replaceAll("\\s", "");
    }
    return ipAddress;
  }

  public static String getUserAgent() {
    HttpServletRequest request = getHttpServletRequest();
    return request.getHeader(USER_AGENT);
  }

  public static String getOrigin() {
    HttpServletRequest request = getHttpServletRequest();
    return request.getHeader(ORIGIN);
  }

  private static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }
}
