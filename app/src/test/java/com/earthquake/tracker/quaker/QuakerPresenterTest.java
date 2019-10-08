package com.earthquake.tracker.quaker;

import com.earthquake.tracker.quaker.mvp.model.Earthquake;
import com.earthquake.tracker.quaker.mvp.model.Feature;
import com.earthquake.tracker.quaker.mvp.model.network.UsgsRestClient;
import com.earthquake.tracker.quaker.mvp.presenter.QuakerPresenter;
import com.earthquake.tracker.quaker.mvp.view.QuakerView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import retrofit2.Response;

/**
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class QuakerPresenterTest {

    private QuakerPresenter quakerPresenter;

    @Mock
    private QuakerView quakerView;

    @Mock
    private UsgsRestClient usgsRestClient;

    @Before
    public void setUp() {
        // required for the "@Mock" annotations
        MockitoAnnotations.initMocks(this);

        quakerPresenter = Mockito.spy(new QuakerPresenter(quakerView));
    }

//    @Test
    public void verify_Rest_Client_Is_NOT_Null() {

    }

    @Test
    public void verify_significant_earthquake_data() {
        Response response = Mockito.mock(Response.class);
        Earthquake earthquakeResponse = Mockito.mock(Earthquake.class);
        Mockito.doReturn(true).when(response).isSuccessful();
        Mockito.doReturn(earthquakeResponse).when(response).body();

        List<Feature> earthquakeList = Collections.singletonList(new Feature());
        quakerPresenter.fetchSignificantEarthquakeData();
        // Validation
        Mockito.verify(quakerView, Mockito.times(1)).quakesData(earthquakeList);
    }

//    @Test
    public void verify_one_and_above_earthquake_data() {

    }

//    @Test
    public void verify_all_earthquake_data() {

    }

}