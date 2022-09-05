package museum.service.controllerTests;

import museum.service.controllers.mvc.AdminDashboardController;
import museum.service.repositories.MuseumTypeRepository;
import museum.service.security.CustomLogoutSuccessHandler;
import museum.service.security.SecurityConfig;
import museum.service.services.*;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminDashboardController.class)
@Import(SecurityConfig.class)
public class CountriesEndpointsTests
{
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AdminLoginService adminLoginService;
    @MockBean
    private AdminInfoService adminInfoService;
    @MockBean
    private CountriesService countriesService;
    @MockBean
    private MuseumService museumService;
    @MockBean
    private MuseumTypeRepository museumTypeRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    @MockBean
    private CustomUserDetailsService userDetailsService;


    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testGetRegionsWithError() throws Exception
    {
        given(countriesService.getRegions(anyString()))
                .willThrow(WebClientResponseException.BadRequest.class);

        mvc.perform(get("/admin/countries/FR/regions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable());
    }
}
