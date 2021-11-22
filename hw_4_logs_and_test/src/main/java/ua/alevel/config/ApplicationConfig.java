package ua.alevel.config;

import org.reflections.Reflections;
import ua.alevel.db.UserDB;
import ua.alevel.db.impl.UserListDBImpl;
import ua.alevel.db.impl.UserSetDBImpl;
import ua.alevel.type.ApplicationType;
import ua.alevel.util.ResourcesUtil;

import java.util.Map;
import java.util.Set;

public class ApplicationConfig {

    public static <I> I getImpl(Class<I> iface) {
        Reflections scan = new Reflections("ua.alevel");
        Set<Class<? extends I>> imls = scan.getSubTypesOf(iface);
        for (Class<? extends I> iml : imls) {
            if (iface.isAssignableFrom(UserDB.class)) {
                Map<String, String> map = ResourcesUtil.getProperties(iface.getClassLoader());
                String db = map.get(ApplicationType.DB_TYPE.getType());
                if (db.equals(ApplicationType.DB_SET_VALUE.getType())) {
                    return (I) UserSetDBImpl.getInstance();
                } else {
                    return (I) UserListDBImpl.getInstance();
                }
            }
            if (!iml.isAnnotationPresent(Deprecated.class)) {
                try {
                    return iml.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
