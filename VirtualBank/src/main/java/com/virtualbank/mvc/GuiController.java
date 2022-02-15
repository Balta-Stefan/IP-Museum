package com.virtualbank.mvc;

import com.virtualbank.models.PersonDTO;
import com.virtualbank.models.TransactionDTO;
import com.virtualbank.models.entities.PersonEntity;
import com.virtualbank.models.entities.TransactionEntity;
import com.virtualbank.repositories.PersonRepository;
import com.virtualbank.repositories.TransactionsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gui")
public class GuiController
{
    private final PersonRepository personRepository;
    private final TransactionsRepository transactionsRepository;
    private final ModelMapper modelMapper;

    public GuiController(PersonRepository personRepository, TransactionsRepository transactionsRepository, ModelMapper modelMapper)
    {
        this.personRepository = personRepository;
        this.transactionsRepository = transactionsRepository;
        this.modelMapper = modelMapper;
    }

    private Optional<PersonDTO> handleLogin(String cardNumber, String pin, LocalDate expDate)
    {
        Optional<PersonEntity> personEntity = personRepository.findByCardNumber(cardNumber);

        if(personEntity.isPresent())
        {
            PersonEntity person = personEntity.get();

            if(person.getPin().equals(pin) && person.getCardExpirationDate().equals(expDate))
            {
                return Optional.of(modelMapper.map(person, PersonDTO.class));
            }
        }

        return Optional.empty();
    }

    private List<TransactionDTO> getPersonsTransactions(PersonDTO person)
    {
        List<TransactionDTO> transactions = new ArrayList<>();
        for(TransactionEntity te : transactionsRepository.findAllByPayer(person.getId()))
        {
            TransactionDTO.PaymentStatus status = (te.getPayer() == null) ? TransactionDTO.PaymentStatus.UNSUCCESSFUL : TransactionDTO.PaymentStatus.SUCCESSFUL;
            transactions.add(
                    new TransactionDTO(
                            status,
                            te.getId().toString(),
                            te.getReceiver().getId(),
                            te.getAmount(),
                            te.getTimestamp(),
                            null,
                            null,
                            null,
                            null));
        }

        return transactions;
    }

    private void changePersonActivity(PersonDTO person)
    {
        PersonEntity personEntity = personRepository.findById(person.getId()).get();
        personEntity.setEnabled(!personEntity.getEnabled());
        person.setEnabled(!person.getEnabled());

        personRepository.saveAndFlush(personEntity);
    }

    @RequestMapping("")
    public String getGui(@RequestParam(name = "action", required = false) String action, Model model, HttpServletRequest request, HttpServletResponse response)
    {
        String returnPage = "error";

        try
        {
            if (action == null)
                return "login";

            switch (action)
            {
                case "login":
                    String cardNumber = request.getParameter("number");
                    String pin = request.getParameter("pin");
                    LocalDate expDate = LocalDate.parse(request.getParameter("expDate"));

                    Optional<PersonDTO> person = handleLogin(cardNumber, pin, expDate);
                    if(person.isPresent())
                    {
                        PersonDTO personDTO = person.get();
                        request.getSession().setAttribute("user", personDTO);
                        returnPage = "PersonPage";
                        //model.addAttribute("transactions", getPersonsTransactions(personDTO));
                        request.getSession().setAttribute("transactions", getPersonsTransactions(personDTO));
                    }
                    else
                    {
                        model.addAttribute("login_status", "Neispravni kredencijali!");
                        returnPage = "login";
                    }
                    break;
                case "changeActivity":
                    PersonDTO personDTO = (PersonDTO)request.getSession().getAttribute("user");

                    if(personDTO == null)
                    {
                        returnPage = "login";
                    }
                    else
                    {
                        changePersonActivity(personDTO);
                        returnPage = "PersonPage";
                        model.addAttribute("activity_change_status", "Status naloga: " + (personDTO.getEnabled() ? "aktivan" : "neaktivan"));
                    }
                    break;
                case "logout":
                    request.getSession().invalidate();

                    returnPage = "login";
                    break;
                default:
                    returnPage = "login";
                    break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        return returnPage;
    }
}
