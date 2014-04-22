package com.athaydes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
public abstract class TestBase {

    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws Exception {
        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    public <T> T withSession( Function<Session, T> callable ) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            T result = callable.apply( session );
            session.getTransaction().commit();
            return result;
        } catch ( Exception e ) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Serializable> createDbWithPeople( Person... people ) {
        return withSession( session -> Arrays.asList( people )
                .stream().map( session::save ).collect( Collectors.toList() ) );
    }

}
