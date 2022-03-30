package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamUtils {
    public static <TS, TT> TT transformObject1(TS source, Class<TS> sourceClass, Class<TT> targetClass) {
        if (source == null)
            return null;

        List<Field> targetFields = Arrays.asList(targetClass.getDeclaredFields());

        List<Field> sourceFields = Arrays.asList(sourceClass.getDeclaredFields());

        Map<Field, Field> matchedFields = targetFields.stream().filter(t -> sourceFields.stream().anyMatch(s -> s.getName().equals(t.getName())))
                .collect(Collectors.toMap(t -> t, t -> sourceFields.stream().filter(s -> s.getName().equals(t.getName())).findAny().get()));

        matchedFields.forEach((t, s) -> {
            t.setAccessible(true);
            s.setAccessible(true);
        });

        try {
            TT target = targetClass.getDeclaredConstructor().newInstance();

            matchedFields.forEach((tf, sf) -> {
                try {
                    Object o = sf.get(source);
                    tf.set(target, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <TS, TT> List<TT> transformList1(List<TS> sourceList, Class<TS> sourceClass, Class<TT> targetClass) {
        ArrayList<TT> result = new ArrayList<TT>();

        if (sourceList == null || sourceList.isEmpty())
            return result;

        List<Method> targetMethods = Arrays.asList(targetClass.getDeclaredMethods());
        List<Method> sourceMethods = Arrays.asList(sourceClass.getDeclaredMethods());

        Map<Method, Method> matchedMethods = targetMethods.stream().filter(t -> sourceMethods.stream()
                .anyMatch(s -> s.getName().startsWith("get") && t.getName().startsWith("set") && s.getName().substring(3).equals(t.getName().substring(3))))
                .collect(Collectors.toMap(t -> t, t -> sourceMethods.stream().filter(s -> s.getName().startsWith("get") && s.getName().substring(3).equals(t.getName().substring(3))).findAny().get()));

        matchedMethods.forEach((t, s) -> {
            t.setAccessible(true);
            s.setAccessible(true);
        });

        sourceList.forEach(s -> {
            try {
                TT target = targetClass.getDeclaredConstructor().newInstance();
                result.add(target);

                matchedMethods.forEach((tm, sm) -> {
                    try {
                        Object o = sm.invoke(s);
                        tm.invoke(target, o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
        return result;
    }

    public static <TS, TT> List<TT> transformList2(List<TS> sourceList, Class<TS> sourceClass, Class<TT> targetClass) {
        ArrayList<TT> result = new ArrayList<TT>();

        if (sourceList == null || sourceList.isEmpty())
            return result;

        List<Field> targetFields = Arrays.asList(targetClass.getDeclaredFields());

        List<Field> sourceFields = Arrays.asList(sourceClass.getDeclaredFields());

        Map<Field, Field> matchedFields = targetFields.stream().filter(t -> sourceFields.stream().anyMatch(s -> s.getName().equals(t.getName())))
                .collect(Collectors.toMap(t -> t, t -> sourceFields.stream().filter(s -> s.getName().equals(t.getName())).findAny().get()));

        matchedFields.forEach((t, s) -> {
            t.setAccessible(true);
            s.setAccessible(true);
        });
        sourceList.stream().forEach(s -> {
            try {
                TT target = targetClass.getDeclaredConstructor().newInstance();
                result.add(target);

                matchedFields.forEach((tf, sf) -> {
                    try {
                        Object o = sf.get(s);
                        tf.set(target, o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
        return result;
    }

    public static <TS, TT> void transformList3(List<TS> sourceList, List<TT> targetList, Class<TS> sourceClass, Class<TT> targetClass) {
        ArrayList<TT> result = new ArrayList<TT>();

        if (sourceList == null || sourceList.isEmpty() || targetList == null || targetList.isEmpty() || sourceList.size() != targetList.size())
            return;

        List<Field> targetFields = Arrays.asList(targetClass.getDeclaredFields());

        List<Field> sourceFields = Arrays.asList(sourceClass.getDeclaredFields());

        Map<Field, Field> matchedFields = targetFields.stream().filter(t -> sourceFields.stream().anyMatch(s -> s.getName().equals(t.getName())))
                .collect(Collectors.toMap(t -> t, t -> sourceFields.stream().filter(s -> s.getName().equals(t.getName())).findAny().get()));

        matchedFields.forEach((t, s) -> {
            t.setAccessible(true);
            s.setAccessible(true);
        });

        for (int i = 0; i < sourceList.size(); i++) {
            var target = targetList.get(i);
            var source = sourceList.get(i);

            matchedFields.forEach((tf, sf) -> {
                try {
                    Object o = sf.get(source);
                    tf.set(target, o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
