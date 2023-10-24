package operations;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Command;

public class CommandExecutor {
    public static boolean executeCommand(String command, Map<String, Object> data) throws Exception {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        config.addAnnotatedClass(Command.class);

        SessionFactory sf = config.buildSessionFactory();
        Session session = sf.openSession();

        session.beginTransaction();

        Query<Command> qry = session.createQuery("FROM Command WHERE command = :command", Command.class);
        qry.setParameter("command", command);

        Command cmd = qry.uniqueResult();

        if (cmd != null) {
            LinkedHashMap<String, Object> paramMap = new LinkedHashMap<>();
            paramMap.putAll(data);

            Class<?> clazz = Class.forName(cmd.getClassName());
            Method method = clazz.getDeclaredMethod(cmd.getMethod(), LinkedHashMap.class);
            method.setAccessible(true);
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Boolean result = (Boolean) method.invoke(instance, paramMap);

            session.getTransaction().commit();
            session.close();
            return result;
        }

        session.getTransaction().commit();
        session.close();

        System.out.println("No such command have been found on the database!");
        return false;
    }
}