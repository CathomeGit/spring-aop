package com.psvmchannel.springaop.aop;

import com.psvmchannel.springaop.entity.Book;
import com.psvmchannel.springaop.util.CustomResponse;
import com.psvmchannel.springaop.util.CustomStatus;
import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Aspect
@Component
public class MyAspect {

    @Nullable
    private static String getParamString(ProceedingJoinPoint joinPoint) {
        String result = null;
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (parameterNames.length > 0 && parameterNames.length == args.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" by");
            for (int i = 0; i < parameterNames.length; i++) {
                stringBuilder.append(i == 0 ? " " : ", ")
                        .append(parameterNames[i])
                        .append(" = ")
                        .append(args[i]);
            }
            result = stringBuilder.toString();
        }
        return result;
    }

    private static String expandMessage(String initialMessage, @Nullable String extension) {
        return extension == null ? initialMessage : initialMessage.concat(extension);
    }

    @Around("PointCuts.allGetMethods()")
    public Object aroundGettingAdvice(ProceedingJoinPoint joinPoint) {
        String paramString = getParamString(joinPoint);

        log.info(expandMessage("Getting books", paramString));
        Object result;
        try {
            result = joinPoint.proceed();
            log.info("Books found");
        } catch (NoSuchElementException e) {
            log.error(expandMessage("Not found a book", paramString));
            return new CustomResponse<>(null, CustomStatus.NOT_FOUND);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            return new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
        return result;
    }

    @Around("PointCuts.allAddMethods()")
    public Object aroundAddingAdvice(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Book book = null;
        for (Object arg : args) {
            if (arg instanceof Book) {
                book = (Book) arg;
                log.info("Saving a book {}", book);
            }
        }
        Object result;
        try {
            result = joinPoint.proceed();
            log.info("A book {} saved", book);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result = new CustomResponse<>(null, CustomStatus.EXCEPTION);
        }
        return result;
    }
}