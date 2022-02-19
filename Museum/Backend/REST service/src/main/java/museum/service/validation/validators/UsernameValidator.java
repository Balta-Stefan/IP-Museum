package museum.service.validation.validators;

import museum.service.validation.annotations.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String>
{
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext)
    {
        if(s.trim().length() < 12)
        {
            return false;
        }

        Pattern specialCharactersPattern = Pattern.compile("(.*[#@\\/]{1,}.*)");

        Matcher matcher = specialCharactersPattern.matcher(s);

        if(matcher.matches())
        {
            return false;
        }

        return true;
    }
}
