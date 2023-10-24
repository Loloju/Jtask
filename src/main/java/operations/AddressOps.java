package operations;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

import model.Address;

public class AddressOps {
    public enum columns {
        KEY("key"),
        COUNTRY("country"),
        CITY("city"),
        DISTRICT("district"),
        ADDRESS("addressrest");

        private String columnName;

        columns(String columnName){
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

    public static boolean getAddresses(Session session) {
        List<Address> addressResult = session.createQuery("FROM Address", Address.class).list();

        if (addressResult != null) {
            for (int i = 0; i < addressResult.size(); i++) {
                if (i != addressResult.size() - 1)
                    System.out.println(addressResult.get(i).toString() + ", ");
                else
                    System.out.println(addressResult.get(i).toString());
            }
            return true;
        }
        return false;
    }

    public static boolean deleteAddressWithValue(Session session, String column, String value) {
        session.beginTransaction();
        Query<Address> query = session.createQuery("FROM Address WHERE "+column+" = :"+column+"", Address.class);
        query.setParameter(column, value);
        List<Address> addressResultRemove = query.list();
        if (addressResultRemove != null) {
            for (Address res : addressResultRemove)
                session.remove(res);
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public static boolean insertAddress(Session session, Address newAddress) {
        session.beginTransaction();
        session.persist(newAddress);
        session.getTransaction().commit();
        return true;
    }
}
