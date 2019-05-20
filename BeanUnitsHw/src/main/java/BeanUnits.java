import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BeanUnits {

    /**

     * Scans object "from" for all getters. If object "to"

     * contains correspondent setter, it will invoke it

     * to set property value for "to" which equals to the property

     * of "from".

     * <p/>

     * The type in setter should be compatible to the value returned

     * by getter (if not, no invocation performed).

     * Compatible means that parameter type in setter should

     * be the same or be superclass of the return type of the getter.

     * <p/>

     * The method takes care only about public methods.

     *

     * @param to Object which properties will be set.

     * @param from Object which properties will be used to get values.

     */

    private static ArrayList<Method> getSetters(Class obj_class) {
        Method[] all_methods = obj_class.getMethods();
        ArrayList<Method> setters = new ArrayList<Method>();

        for(Method cur: all_methods) {
            if(cur.getName().startsWith("set") && cur.getParameterTypes().length == 1) {
                setters.add(cur);
            }
        }
        return setters;

    }

    private static ArrayList<Method> getGetters(Class obj_class) {
        Method[] all_methods = obj_class.getMethods();
        ArrayList<Method> getters = new ArrayList<Method>();

        for(Method cur: all_methods) {
            if(cur.getName().startsWith("get") && cur.getParameterTypes().length == 0 &&
                    !((void.class.equals(cur.getReturnType())))) {
                getters.add(cur);
            }
        }
        return getters;

    }

    public static void assign(Object to, Object from) {

        ArrayList<Method> getters = getGetters(from.getClass());
        ArrayList<Method> setters = getSetters(to.getClass());

        for(Method get_method: getters) {
            for(Method set_method: setters) {
                Class returned_type = get_method.getReturnType();
                Class setter_type = set_method.getParameterTypes()[0];
                if (returned_type.equals(setter_type) || setter_type.equals(returned_type.getSuperclass())) {
                    try {
                        set_method.invoke(to, get_method.invoke(from));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}