package com.athaydes;

import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class KindOfRealWorldTest extends TestBase {

    @Test
    public void modifyingPersonShouldAutomagicallySaveToDb() {
        Person henrik = new Person( "Henrik" );
        List<Serializable> ids = createDbWithPeople( henrik );

        withSession( session -> {
            session.load( henrik, ids.get( 0 ) );
            // now henrik is attached to a db session, so updates will be reflected in the DB
            henrik.setFavouriteColour( "blue" );
            return null;
        } );

        assertHenrikLikesBlueInTheDb();
    }

    private void assertHenrikLikesBlueInTheDb() {
        List<Person> people = withSession( session ->
                session.createQuery( "from Person p where p.favouriteColour = :colour" )
                        .setParameter( "colour", "blue" ).list() );

        assertThat( people.size(), is( 1 ) );
        assertThat( people.get( 0 ).getName(), is( "Henrik" ) );
    }


}
