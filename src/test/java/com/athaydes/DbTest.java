package com.athaydes;


import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class DbTest extends TestBase {

    @Test
    public void shouldPersistAndGetPeople() {
        createDbWithPeople( new Person( "Joe" ) );

        List<Person> people = withSession( session ->
                session.createQuery( "from Person" ).list() );

        assertThat( people.size(), is( 1 ) );
    }

}
