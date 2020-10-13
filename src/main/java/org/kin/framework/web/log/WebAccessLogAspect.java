package org.kin.framework.web.log;

import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.kin.framework.log.LoggerOprs;
import org.kin.framework.utils.JSON;
import org.kin.framework.utils.StringUtils;
import org.kin.framework.utils.Urls;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author huangjianqin
 * @date 2020/10/11
 */
@Aspect
@Component
@Order(1)
public class WebAccessLogAspect implements LoggerOprs {
    @Pointcut("execution(public * *.controller.*.*(..))")
    public void logWebAccess() {
    }

    @Before("logWebAccess()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @AfterReturning(value = "logWebAccess()", returning = "ret")
    public void doAfterReturning(Object ret) {
    }

    @Around("logWebAccess()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        //记录请求信息(通过Logstash传入Elasticsearch)
        Object result = joinPoint.proceed();

        if (Objects.nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            WebAccessLog accessLog = new WebAccessLog();
            //方法描述
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            long endTime = System.currentTimeMillis();
            String urlStr = request.getRequestURL().toString();

            accessLog.setBasePath(StringUtils.removeSuffix(urlStr, Urls.url(urlStr).getPath()));
            accessLog.setIp(request.getRemoteUser());
            accessLog.setMethod(request.getMethod());
            accessLog.setParams(parseParams(method, joinPoint.getArgs()));
            accessLog.setResult(result);
            accessLog.setCostTime((int) (endTime - startTime));
            accessLog.setStartTime(startTime);
            accessLog.setUri(request.getRequestURI());
            accessLog.setUrl(request.getRequestURL().toString());
            //logback日志打印内容可以引用的参数${XXX}
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("url", accessLog.getUrl());
            logMap.put("method", accessLog.getMethod());
            logMap.put("params", accessLog.getParams());
            logMap.put("costTime", accessLog.getCostTime());
            logMap.put("description", accessLog.getDescription());

            String accessLogStr = JSON.write(accessLog);
            log().info(Markers.appendEntries(logMap), accessLogStr);
        }

        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private List<Object> parseParams(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>(args.length);
        Parameter[] params = method.getParameters();

        int argsCounter = 0;
        for (int i = 0; i < params.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = params[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[argsCounter++]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = params[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                String key = params[i].getName();
                if (!StringUtils.isBlank(requestParam.value())) {
                    key = requestParam.value();
                }
                argList.add(Collections.singletonMap(key, args[argsCounter++]));
            }
        }
        return argList;
    }
}

