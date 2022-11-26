package com.safetycar.wrappers;

import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.models.wrappers.UserDuplicateWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.safetycar.SafetyCarTestObjectsFactory.getUser;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserDuplicateWrapperTest {

    public static final String ANY_STRING = "Mockito.anyString()";
    private final User user = getUser();

    private UserDuplicateWrapper duplicateWrapper;

    @Test
    public void isDuplicate_ShouldReturnTrue_WhenFullNameAndAddressNotEqualAndTelephoneIsEqual() {
        //arrange
        UserDetails details = user.getUserDetails();
        duplicateWrapper = new UserDuplicateWrapper(details.getUserName(),
                details.getTelephone(),
                details.getAddress(),
                details.getFirstName(),
                details.getLastName());
        details.setLastName(ANY_STRING);
        details.setAddress(ANY_STRING);
        details.setFirstName(ANY_STRING);
        //act, assert
        Assertions.assertTrue(duplicateWrapper.duplicate(details));
    }

    @Test
    public void isDuplicate_ShouldReturnTrue_FullNameAndAddressEqualAndTelephoneNotEqual() {
        //arrange
        UserDetails details = user.getUserDetails();
        duplicateWrapper = new UserDuplicateWrapper(details.getUserName(),
                details.getTelephone(),
                details.getAddress(),
                details.getFirstName(),
                details.getLastName());
        details.setTelephone(ANY_STRING);
        //act, assert
        Assertions.assertTrue(duplicateWrapper.duplicate(details));
    }

    @Test
    public void isDuplicate_ShouldReturnFalse_WhenFullNameAndAddressNotEqualOrTelephoneIsNotEqual() {
        //arrange
        UserDetails details = user.getUserDetails();
        duplicateWrapper = new UserDuplicateWrapper(details.getUserName(),
                details.getTelephone(),
                details.getAddress(),
                details.getFirstName(),
                details.getLastName());
        details.setLastName(ANY_STRING);
        details.setTelephone(ANY_STRING);
        details.setAddress(ANY_STRING);
        details.setFirstName(ANY_STRING);
        //act, assert
        Assertions.assertFalse(duplicateWrapper.duplicate(details));
    }


    @Test
    public void isDuplicate_ShouldReturnFalse_WhenFullNameAndAddressNotEqual_AndTelephoneIsEqual_ButDetailsInactive() {
        //arrange
        UserDetails details = user.getUserDetails();
        details.setActive(false);
        duplicateWrapper = new UserDuplicateWrapper(details.getUserName(),
                details.getTelephone(),
                details.getAddress(),
                details.getFirstName(),
                details.getLastName());
        details.setLastName(ANY_STRING);
        details.setAddress(ANY_STRING);
        details.setFirstName(ANY_STRING);
        //act, assert
        Assertions.assertFalse(duplicateWrapper.duplicate(details));
    }

    @Test
    public void isDuplicate_ShouldReturnFalse_FullNameAndAddressEqual_AndTelephoneNotEqual_ButDetailsInactive() {
        //arrange
        UserDetails details = user.getUserDetails();
        details.setActive(false);
        duplicateWrapper = new UserDuplicateWrapper(details.getUserName(),
                details.getTelephone(),
                details.getAddress(),
                details.getFirstName(),
                details.getLastName());
        details.setTelephone(ANY_STRING);
        //act, assert
        Assertions.assertFalse(duplicateWrapper.duplicate(details));
    }
}
