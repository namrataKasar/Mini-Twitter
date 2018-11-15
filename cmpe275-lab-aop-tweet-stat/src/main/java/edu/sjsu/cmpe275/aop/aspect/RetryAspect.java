package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */
	
	@Around("execution(* edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
    public void tweetAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            joinPoint.proceed();
        } catch (IOException e1) {
            System.out.println("Retrying tweet due to network failure: 1st attempt");
            try {
                joinPoint.proceed();
            } catch (IOException e2) {
                System.out.println("Retrying tweet due to network failure: 2nd attempt");
                try {
                    joinPoint.proceed();
                } catch (IOException e3) {
                    System.out.println("Tweet failed permanently");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Tweet length more than 140 !");
        }
    }

    
    @Around("execution(* edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
    public void followAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            joinPoint.proceed();
        } catch (IOException e1) {
            System.out.println("Retrying follow due to network failure: 1st attempt");
            try {
                joinPoint.proceed();
            } catch (IOException e2) {
                System.out.println("Retrying follow due to network failure: 2nd attempt");
                try {
                    joinPoint.proceed();
                } catch (IOException e3) {
                    System.out.println("Follow failed permanently");
                }
            }
        }
    }
    
    @Around("execution(* edu.sjsu.cmpe275.aop.TweetServiceImpl.block(..))")
    public void blockAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            joinPoint.proceed();
        } catch (IOException e1) {
            System.out.println("Retrying follow due to network failure: 1st attempt");
            try {
                joinPoint.proceed();
            } catch (IOException e2) {
                System.out.println("Retrying follow due to network failure: 2nd attempt");
                try {
                    joinPoint.proceed();
                } catch (IOException e3) {
                    System.out.println("Follow failed permanently");
                }
            }
        }
    }

    @Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
	public void dummyAdvice(ProceedingJoinPoint joinPoint) {
		System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
			System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		}
	}
    
}
