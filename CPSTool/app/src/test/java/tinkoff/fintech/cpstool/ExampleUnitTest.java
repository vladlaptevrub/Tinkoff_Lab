package tinkoff.fintech.cpstool;

import org.junit.Test;

import tinkoff.fintech.cpstool.view.fragments.InformationFragment;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void building_message_is_correct_not_null_test() throws Exception{
        InformationFragment thirdFragment = new InformationFragment();

        String title = "test_title";
        String inn = "test_inn";
        String address = "test_address";

        String aim = "test_title\n"+
                "ИНН: test_inn\n"+
                "Адрес: test_address\n\n"+
                "Найдено с помощью 'CPS Tool' (Play Market link)";

        assertEquals(thirdFragment.buildSharingMessage(title, inn, address), aim);
    }

    @Test
    public void building_message_is_correct_null_test() throws Exception{
        InformationFragment thirdFragment = new InformationFragment();

        String title = "test_title";
        String inn = "test_inn";
        String address = "test_address";

        assertEquals(thirdFragment.buildSharingMessage(null, inn, address), null);
        assertEquals(thirdFragment.buildSharingMessage(title, null, address), null);
        assertEquals(thirdFragment.buildSharingMessage(title, inn, null), null);
        assertEquals(thirdFragment.buildSharingMessage(null, null, null), null);
    }
}