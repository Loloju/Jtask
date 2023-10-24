package operations;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import model.Phone;

public class PhoneOps {
    public enum columns {
        KEY("key"),
        CUSTOMER_ID("customerId"),
        NAME("name"),
        PHONE("phone");

        private String columnName;

        columns(String columnName){
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

    public static boolean getPhones(Session session) {
        List<Phone> phoneResult = session.createQuery("FROM Phone", Phone.class).list();

        if (phoneResult != null) {
            for (int i = 0; i < phoneResult.size(); i++) {
                if (i != phoneResult.size() - 1)
                    System.out.println(phoneResult.get(i).toString() + ", ");
                else
                    System.out.println(phoneResult.get(i).toString());
            }
            return true;
        }
        return false;
    }

    public static boolean deletePhoneWithValue(Session session, String column, String value) {
        session.beginTransaction();
        Query<Phone> query = session.createQuery("FROM Phone WHERE "+column+" = :"+column+"", Phone.class);
        query.setParameter(column, value);
        List<Phone> phoneResultRemove = query.list();
        if (phoneResultRemove != null) {
            for (Phone res : phoneResultRemove)
                session.remove(res);
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public static boolean insertPhone(Session session, Phone newPhone) {
        session.beginTransaction();
        session.persist(newPhone);
        session.getTransaction().commit();
        return true;
    }
}
