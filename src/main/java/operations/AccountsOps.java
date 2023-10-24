package operations;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import model.Accounts;

public class AccountsOps {
    public enum columns {
        ID("id"),
        CUSTOMER_NO("customerNo"),
        PASSWORD("password"),
        NAME("name"),
        EMAIL("email"),
        CREATION_DATE("creationDate");

        private String columnName;

        columns(String columnName){
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    }

    public static boolean getAccounts(Session session) {
        List<Accounts> accountResult = session.createQuery("FROM Accounts", Accounts.class).list();

        if (accountResult != null) {
            for (int i = 0; i < accountResult.size(); i++) {
                if (i != accountResult.size() - 1)
                    System.out.println(accountResult.get(i).toString() + ", ");
                else
                    System.out.println(accountResult.get(i).toString());
            }
            return true;
        }
        return false;
    }

    public static boolean deleteAccountWithValue(Session session, String column, String value) {
        session.beginTransaction();
        Query<Accounts> query = session.createQuery("FROM Accounts WHERE "+column+" = :"+column+"", Accounts.class);
        query.setParameter(column, value);
        List<Accounts> accountResultRemove = query.list();
        if (accountResultRemove != null) {
            for (Accounts res : accountResultRemove)
                session.remove(res);
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public static boolean insertAccounts(Session session, Accounts newAccount) {
        session.beginTransaction();
        session.persist(newAccount);
        session.getTransaction().commit();
        return true;
    }
}
