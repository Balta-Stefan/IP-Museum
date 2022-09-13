# Description

A web application made with Spring boot, Angular and JSP (Java Server Pages) for virtual museum tours.

There are three applications: 
- **Tours application** - for viewing museums, tours and news. Weather is displayed for each museum. News and weather data are fetched from the back-end, which fetches the data from external services. After purchasing a tour ticket, an email is sent to the customer which contains a PDF file with the ticket. Museum location is displayed in a Google maps iframe. It exposes a RESTful API made in Spring boot. Front-end is made in Angular. 
- **Bank application** - for performing payments. It exposes a RESTful API. It is used by the aforementioned tours application. There is also a JSP M2 application for clients which allows them to view their history of transactions and to enable/disable their account. 
- **Museum administrator application** - a JSP application used only by administrators. It allows the administrators to: 
    - add new museums and tours
    - enable/disable user accounts
    - confirm/deny user registrations
    - reset user password
    - view statistics regarding the *tours application* (with charts)
