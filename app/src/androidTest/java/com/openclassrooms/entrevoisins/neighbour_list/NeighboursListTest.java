
package com.openclassrooms.entrevoisins.neighbour_list;

import android.app.Activity;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.contrib.ViewPagerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.bumptech.glide.request.transition.Transition;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoritesNeighboursApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.ui.neighbour_list.UserActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mNeighbourApiService;
    private FavoritesNeighboursApiService mFavoritesNeighboursApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mNeighbourApiService = DI.getNewInstanceApiService();
        mFavoritesNeighboursApiService = DI.getFavoriteNewInstanceApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * Test vérifiant que lorsque que l'on clique sur un élément de la liste, l'écran de détail est bien lancé
     */
    @Test
    public void listNeighbourActivityTest() {
        // Ouverture de l'ActivityUser
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Vérification que l'activity est bien visible, donc lancée
        onView(withId(R.id.user_activity_rootview))
                .check(matches(isDisplayed()));
    }

    /**
     * Test vérifiant qu'au démarrage de ce nouvel écran, le textview indiquant le nom du voisin est bien rempli
     */
    @Test
    public void neighbourTextViewIsNotEmpty() {
        Neighbour neighbour = mNeighbourApiService.getNeighbours().get(0);
        // Ouverture de l'ActivityUser
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Vérification du contenu du textview
        onView(ViewMatchers.withId(R.id.textview_neighbour_name))
                .check(ViewAssertions.matches(withText(neighbour.getName())));
    }

    /**
     * Test vérifiant que l'onglet Favoris n'affiche que les voisins marqués comme favoris
     */
    @Test
    public void favTabOnlyShowFavNeighbours() {
        List<Neighbour> neighbourList = mNeighbourApiService.getNeighbours();

        // On s'assure qu'il n'y a aucun favoris d'affiché pour le moment
        onView(withId(R.id.container))
                .perform(ViewPagerActions.scrollRight());
        //TODO Implémenter le test de présence d'aucun item
        onView(withId(R.id.list_favorites_neighbours))
                .check(ViewAssertions.matches(not(withText(neighbourList.get(0).getName()))));

        onView(withId(R.id.container))
                .perform(ViewPagerActions.scrollLeft());

        // On ouvre la user activity
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // On clique sur le bouton ajouter aux favoris
        onView(withId(R.id.add_neighbour_to_fav))
                .perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());


        // On s'assure qu'il n'y a que le neighbour récemment ajouté aux favoris qui est affiché
        onView(withId(R.id.container))
                .perform(ViewPagerActions.scrollRight());
        onView(ViewMatchers.withId(R.id.list_favorites_neighbours))
                .perform(RecyclerViewActions.scrollToPosition(0))
                .check(matches(hasDescendant(withText(neighbourList.get(0).getName()))));
    }
}