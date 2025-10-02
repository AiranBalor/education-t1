package ru.t1.education;

import java.util.*;
import java.lang.reflect.*;


public class ListAddCounter {
    
    public static void test(List<?> list) {
        System.out.println("Метод test вызван с списком: " + list);
    }
    
    public static void main(String[] args) {
        // прокси-список
        List<Integer> countingList = createCountingList();
        
        countingList.add(1);
        countingList.add(2);
        countingList.add(3);
        countingList.add(4);
        countingList.add(5);
        countingList.add(6);
        
        test(countingList);
        
        System.out.println("Количество вызовов add: " + getAddCount(countingList));
    }
    
    private static List<Integer> createCountingList() {
        return (List<Integer>) Proxy.newProxyInstance(
            ListAddCounter.class.getClassLoader(),
            new Class[]{List.class},
            new CountingInvocationHandler(new ArrayList<>())
        );
    }
    
    private static int getAddCount(List<?> list) {
        if (list instanceof Proxy) {
            InvocationHandler handler = Proxy.getInvocationHandler(list);
            if (handler instanceof CountingInvocationHandler) {
                return ((CountingInvocationHandler) handler).getAddCount();
            }
        }
        return 0;
    }
    
    // обработчик вызовов для прокси
    private static class CountingInvocationHandler implements InvocationHandler {
        private final List<Integer> target;
        private int addCount = 0;
        
        public CountingInvocationHandler(List<Integer> target) {
            this.target = target;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Подсчитываем вызовы методов add. Equals можно заменить на что-то другое
            if (method.getName().equals("add")) {
                addCount++;
            }

            return method.invoke(target, args);
        }
        
        public int getAddCount() {
            return addCount;
        }
    }
}