package operations;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import model.Customer;

public class CustomerOps {
    public enum columns {
        ID("id"),
        TCKN("tckn"),
        NAME("name"),
        AGE("age");

        private String columnName;

        columns(String columnName){
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

    public static boolean getCustomers(Session session) {
        List<Customer> custResult = session.createQuery("FROM Customer", Customer.class).list();

        if (custResult != null) {
            for (int i = 0; i < custResult.size(); i++) {
                if (i != custResult.size() - 1)
                    System.out.println(custResult.get(i).toString() + ", ");
                else
                    System.out.println(custResult.get(i).toString());
            }
            return true;
        }
        return false;
    }

    public static boolean deleteCustomerWithValue(Session session, String column, String value) {
        session.beginTransaction();
        Query<Customer> query = session.createQuery("FROM Customer WHERE "+column+" = :"+column+"", Customer.class);
        query.setParameter(column, value);
        List<Customer> custResultRemove = query.list();
        if (custResultRemove != null) {
            for (Customer res : custResultRemove)
                session.remove(res);
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public static boolean insertCustomer(Session session, Customer newCustomer) {
        session.beginTransaction();
        session.persist(newCustomer);
        session.getTransaction().commit();
        return true;
    }
}
