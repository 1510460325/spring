package cn.wzy.DI;

import java.lang.reflect.Field;

/**
 * Create by Wzy
 * on 2018/8/3 14:24
 * 不短不长八字刚好
 */

public class BeanFactory {

    public static <Q> Q getBean(Class<Q> clazz) {
        Q result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("get the " + clazz.getName() + "failed!!");
            return null;
        } catch (IllegalAccessException e) {
            System.out.println("get the " + clazz.getName() + "failed!!");
            e.printStackTrace();
            return null;
        }
        //查找所有的字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {//查找字段中含有依赖注入的字段 存在就进行注入
            Inject inject = field.getAnnotation(Inject.class);
            if (inject != null) {
                Object object = getBean(field.getType());
                if (!field.isAccessible())
                    field.setAccessible(true);
                try {
                    field.set(result,object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    System.out.println("Inject the " + field.getName() + "failed!!");
                }
            }
        }
        return result;
    }
}
