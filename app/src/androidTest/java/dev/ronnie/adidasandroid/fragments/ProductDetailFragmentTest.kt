package dev.ronnie.adidasandroid.fragments

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dev.ronnie.adidasandroid.MainActivity
import dev.ronnie.adidasandroid.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ProductDetailFragmentTest {

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun navigateToTheFragment() {
        //Wait for the products to load, should fail in case product list doesn't load.
        //alternatively pass the product as bundle without relying on network call
        Thread.sleep(5000)

        val recyclerView = onView(
            allOf(
                withId(R.id.list)
            )
        )
        recyclerView.perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
    }

    @Test
    fun appbar_is_showing(): Unit = runBlocking {
        onView(withId(R.id.appbar))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun recyclerview_is_showing() {
        onView(withId(R.id.rating_list))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun image_is_showing() {
        onView(withId(R.id.image))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    //Should fail if there is no internet connection or if the Api wasn't reached.
    @Test
    fun test_add_review(): Unit = runBlocking {
        val addReviewButton = onView(
            allOf(
                withId(R.id.add_review)
            )
        )

        //click add review button
        addReviewButton.perform(click())

        //wait for the dialog to show
        delay(1000)
        val ratingBar = onView(
            allOf(
                withId(R.id.ratingBar)
            )
        )
        //set rating
        ratingBar.perform(SetRating(5f))
        val textView = onView(
            allOf(
                withId(R.id.text)
            )
        )

        //set text review
        textView.perform(ViewActions.replaceText("I like this shoe"))

        val submitButton = onView(
            allOf(
                withId(R.id.confirm)
            )
        )

        //submit review
        submitButton.perform(click())

        //wait for the network call and list update
        delay(1000)

        //confirm that the review was added
        onView(withId(R.id.rating_list)).check(
            ViewAssertions.matches(
                atPosition(
                    0,
                    hasDescendant(withText("I like this shoe"))
                )
            )
        )

    }
}