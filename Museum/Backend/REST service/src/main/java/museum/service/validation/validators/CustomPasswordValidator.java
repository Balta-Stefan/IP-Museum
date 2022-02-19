package museum.service.validation.validators;

import museum.service.exceptions.BadRequestException;
import museum.service.validation.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomPasswordValidator implements ConstraintValidator<ValidPassword, String>
{
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext)
    {
		if(s == null)
		{
			return false;
		}
        if(s.trim().length() < 15)
        {
            return false;
        }

        Pattern smallLetters = Pattern.compile("(.*[a-z]{1,}.*)");
        Pattern capitalLetters = Pattern.compile("(.*[A-Z]{1,}.*)");
        Pattern numbers = Pattern.compile("(.*[0-9]{1,}.*)");

        Matcher matcher = smallLetters.matcher(s);

        if(matcher.matches() == false)
        {
            return false;
        }

        matcher = capitalLetters.matcher(s);
        if(matcher.matches() == false)
        {
            return false;
        }

        matcher = numbers.matcher(s);
        if(matcher.matches() == false)
        {
            return false;
        }

        return true;
    }
}
