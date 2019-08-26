package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcUserRepository implements UserRepository {
    @Inject
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }
}
