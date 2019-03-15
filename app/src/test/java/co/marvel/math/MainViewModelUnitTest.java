package co.marvel.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import co.marvel.math.data.source.CharactersDataSource;
import co.marvel.math.data.source.ComicsDataSource;
import co.marvel.math.data.source.CreatorsDataSource;
import co.marvel.math.data.source.EventsDataSource;
import co.marvel.math.data.source.SeriesDataSource;
import co.marvel.math.data.source.StoriesDataSource;
import co.marvel.math.utils.Expression;
import co.marvel.math.view.home.MainViewModel;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelUnitTest {
    @Mock
    CharactersDataSource repository;
    @Mock
    ComicsDataSource comicRepository;
    @Mock
    CreatorsDataSource creatorRepository;
    @Mock
    EventsDataSource eventRepository;
    @Mock
    SeriesDataSource seriesRepository;
    @Mock
    StoriesDataSource storiesRepository;
    private MainViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new MainViewModel(
                repository,
                comicRepository,
                creatorRepository,
                eventRepository,
                seriesRepository,
                storiesRepository
        );
    }

    @Test
    public void shouldEvaluateThat3isMultipleOf3Multiple() {
        // given
        Integer value = 3;
        Integer by = 3;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldEvaluateThat2isNotMultipleOf3Multiple() {
        // given
        Integer value = 2;
        Integer by = 3;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void shouldEvaluateThat5isMultipleOf5Multiple() {
        // given
        Integer value = 5;
        Integer by = 5;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldEvaluateThat4isNotMultipleOf5Multiple() {
        // given
        Integer value = 4;
        Integer by = 5;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void shouldEvaluateThat7isMultipleOf7Multiple() {
        // given
        Integer value = 7;
        Integer by = 7;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldEvaluateThat6isNotMultipleOf7Multiple() {
        // given
        Integer value = 6;
        Integer by = 7;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void shouldEvaluateThat11isMultipleOf11Multiple() {
        // given
        Integer value = 11;
        Integer by = 11;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldEvaluateThat10isNotMultipleOf11Multiple() {
        // given
        Integer value = 10;
        Integer by = 11;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void shouldEvaluateThat13isMultipleOf13Multiple() {
        // given
        Integer value = 13;
        Integer by = 13;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldEvaluateThat12isNotMultipleOf13Multiple() {
        // given
        Integer value = 12;
        Integer by = 13;

        // when
        Boolean result = viewModel.multipleOf(value, by);

        // then
        Assert.assertFalse(result);
    }

    @Test
    public void shouldResolveOperation2Plus2() {
        // given
        String expression = "2+2";
        Expression exp = new Expression(expression);

        // when
        Boolean isInvalid = exp.isInvalid(expression);
        int result = exp.calculate();

        // then
        Assert.assertFalse(isInvalid);
        Assert.assertEquals(4, result);
    }

    @Test
    public void shouldResolveOperation2Minus2() {
        // given
        String expression = "2-2";
        Expression exp = new Expression(expression);

        // when
        Boolean isInvalid = exp.isInvalid(expression);
        int result = exp.calculate();

        // then
        Assert.assertFalse(isInvalid);
        Assert.assertEquals(0, result);
    }

    @Test
    public void shouldResolveOperation2DividedBy5PlusMinus3() {
        // given
        String expression = "5 / (-3)";
        Expression exp = new Expression(expression);

        // when
        Boolean isInvalid = exp.isInvalid(expression);
        int result = exp.calculate();

        // then
        Assert.assertFalse(isInvalid);
        Assert.assertEquals(-1, result);
    }

    @Test
    public void shouldResolveOperation5MultipliedByMinus8() {
        // given
        String expression = "5 * (-8)";
        Expression exp = new Expression(expression);

        // when
        Boolean isInvalid = exp.isInvalid(expression);
        int result = exp.calculate();

        // then
        Assert.assertFalse(isInvalid);
        Assert.assertEquals(-40, result);
    }
}
